package game;

import javafx.application.Application;
import static javafx.application.Application.launch;

import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.text.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Control;
import javafx.stage.Stage;

import java.awt.*;
import java.util.ArrayList;
import java.util.Scanner;

public class GameMaster extends Application {

    private ArrayList<Field> getColumn(Board myBoard, int column){
        ArrayList <Field> toReturn = new ArrayList<>();
        for (int i = 0; i < myBoard.getBoard().size(); i++){
            toReturn.add(myBoard.getBoard().get(i).get(column));
        }
        return toReturn;
    }
    private boolean isThereWinner(Board board, ArrayList<Field> fields){

        Color previous = Color.NONE;

        for (int i = 0; i < fields.size(); i++){

            if(i == 0){
                previous = fields.get(i).getColor();
                continue;
            }
            else{
                if(previous != fields.get(i).getColor()){
                    return false;
                }
            }

        }
        return true;
    }
    private GridPane drawBoard(Board myBoard ,GridPane boardUI) {
        for (int i = 0; i < myBoard.getBoard().size(); i++){
            for (int j = 0; j < myBoard.getBoard().get(i).size(); j++){
                StackPane square = new StackPane();
                square.setMaxSize(66, 66);
                square.setStyle("-fx-background-color: "
                        + myBoard.getBoard().get(i).get(j)
                        +";" );

                boardUI.add(square, i, j);
            }
        }
        return boardUI;
    }
    private void changeColor(int event, OccupiedPosition ofield, Board myBoard) {
        try {
            int x = ofield.getPosition()[0];
            int y = ofield.getPosition()[1];
            Node clickedNode = ofield.getClickedNode();
            if (event % 2 == 0) {
                myBoard.setField(x, y, Color.PLAYER1);
                clickedNode.setStyle("-fx-background-color: "
                        + Color.PLAYER1.getColor()
                        + ";");
                System.out.println(Color.PLAYER1.getColor());

                if (isThereWinner(myBoard, getColumn(myBoard, y))) {
                    System.out.println(Color.PLAYER1 + " won");
                    //TODO ne csak logolja a játék a győzelmet
                }
            } else {
                myBoard.setField(x, y, Color.PLAYER2);
                clickedNode.setStyle("-fx-background-color: "
                        + Color.PLAYER2.getColor()
                        + ";");
                System.out.println(Color.PLAYER2.getColor());
                if (isThereWinner(myBoard, myBoard.getBoard().get(x))) {
                    System.out.println(Color.PLAYER2 + " won");
                    //TODO ne csak logolja a játék a győzelmet
                }
            }
        }
        catch (Exception e){

        }
        ofield.setEventCounter(ofield.getEventCounter()+1);
    }
    private void play(Stage game){

        System.out.println("Starting a new game...");
        Board myBoard = new Board();
        OccupiedPosition ofield = new OccupiedPosition();

        BorderPane root = new BorderPane();
        GridPane boardUI = new GridPane();
        Scene scene = new Scene(root);
        Label title = new Label("Color War");
        title.setFont(new Font("Covik Sans", 30));
        root.setTop(title);
        root.setCenter(boardUI);
        root.setPrefSize(720, 1280);
        Button saveState = new Button("Save");
        Button mainMenu = new Button("Back to Main Menu");
        Button exitGame = new Button("Exit");

        VBox sidemenu = new VBox();
        sidemenu.getChildren().addAll(saveState, mainMenu, exitGame);

        root.setLeft(sidemenu);

        saveState.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                //TODO
            }
        });
        mainMenu.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                start(game);
            }
        });
        exitGame.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                exit();
            }
        });

        boardUI.setMaxSize(660, 660);
        boardUI.setGridLinesVisible(true);
        boardUI = drawBoard(myBoard, boardUI);




        scene.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Node clickedNode = mouseEvent.getPickResult().getIntersectedNode();
                Integer colIndex = GridPane.getColumnIndex(clickedNode);
                Integer rowIndex = GridPane.getRowIndex(clickedNode);
                try {
                    if (myBoard.getBoard().get(colIndex).get(rowIndex).getColor() == Color.NONE) {
                        System.out.println(colIndex + " " + rowIndex);
                        ofield.setPosition(colIndex, rowIndex);
                        ofield.setClickedNode(clickedNode);
                        changeColor(ofield.getEventCounter(), ofield, myBoard);
                    }
                }catch (NullPointerException e){
                    // A játékos a griden kívülre kattint
                }
            }
        });

        for (int i = 0; i < myBoard.getBoard().size(); i++) {
            boardUI.getColumnConstraints().add(new ColumnConstraints(10, Control.USE_COMPUTED_SIZE, Double.POSITIVE_INFINITY, Priority.ALWAYS, HPos.CENTER, true));
            boardUI.getRowConstraints().add(new RowConstraints(10, Control.USE_COMPUTED_SIZE, Double.POSITIVE_INFINITY, Priority.ALWAYS, VPos.CENTER, true));
        }
        game.setScene(scene);
        game.setFullScreen(true);
        game.show();
    }
    private void exit(){
        Stage dialog = new Stage();
        BorderPane root = new BorderPane();

        Label question = new Label("Are you sure?");
        Button ok = new Button("Yes");
        Button cancel = new Button("Cancel");

       // root.getChildren().addAll(question);
        root.setTop(question);
        HBox buttons = new HBox();
        buttons.getChildren().addAll(ok, cancel);
        root.setLeft(buttons);

        Scene scene = new Scene(root);
        dialog.setScene(scene);
        dialog.show();

        ok.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                System.exit(0);
            }
        });
        cancel.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                dialog.close();
            }
        });
        dialog.setScene(scene);
    }

    @Override
    public void start(Stage primaryStage) {

        BorderPane menuRoot = new BorderPane();
        VBox menu = new VBox();
        Scene scene = new Scene(menuRoot);

        Label menuTitle = new Label("Color War");
        Button startGame = new Button("New Game");
        Button loadGame = new Button("Load Game");
        Button exitGame = new Button("Exit");

        menu.getChildren().addAll(startGame,loadGame,exitGame);
        menuRoot.setCenter(menu);
        menu.setFillWidth(true);
        menuRoot.setLeft(menuTitle);
        menuTitle.setTextAlignment(TextAlignment.CENTER);
        menuTitle.setFont(new Font("Arial", 30.0));

        menuRoot.setPrefSize(720, 1280);
        startGame.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                play(primaryStage);
            }
        });
        loadGame.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                //Todo
            }
        });
        exitGame.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                exit();
            }
        });

        primaryStage.setScene(scene);
        primaryStage.setFullScreen(true);
        primaryStage.show();


    }

    /**
     * The main() method is ignored in correctly deployed JavaFX application.
     * main() serves only as fallback in case the application can not be
     * launched through deployment artifacts, e.g., in IDEs with limited FX
     * support. NetBeans ignores main().
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
