package game;

import javafx.application.Application;
import static javafx.application.Application.launch;

import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Control;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Scanner;

public class GameMaster extends Application {

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

                if (isThereWinner(myBoard, getColumn(myBoard, x))) {
                    System.out.println(Color.PLAYER1 + " won");
                    //TODO ne csak logolja a játék a győzelmet
                }
            } else {
                myBoard.setField(x, y, Color.PLAYER2);
                clickedNode.setStyle("-fx-background-color: "
                        + Color.PLAYER2.getColor()
                        + ";");
                System.out.println(Color.PLAYER1.getColor());
                if (isThereWinner(myBoard, myBoard.getBoard().get(y))) {
                    System.out.println(Color.PLAYER2 + " won");
                    //TODO ne csak logolja a játék a győzelmet
                }
            }
        }
        catch (Exception e){

        }
     ofield.setEventCounter(ofield.getEventCounter()+1);
    }
    @Override
    public void start(Stage primaryStage) {
        System.out.println("Starting a new game...");
        Board myBoard = new Board();
        OccupiedPosition ofield = new OccupiedPosition();

        BorderPane root = new BorderPane();
        GridPane boardUI = new GridPane();
        Scene scene = new Scene(root);

        root.setCenter(boardUI);
        root.setPrefSize(720, 1280);

        boardUI.setMaxSize(660, 660);
        boardUI.setGridLinesVisible(true);
        boardUI = drawBoard(myBoard, boardUI);




        scene.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Node clickedNode = mouseEvent.getPickResult().getIntersectedNode();
                Integer colIndex = GridPane.getColumnIndex(clickedNode);
                Integer rowIndex = GridPane.getRowIndex(clickedNode);
                if(myBoard.getBoard().get(colIndex).get(rowIndex).getColor() == Color.NONE){
                    System.out.println(colIndex+" "+rowIndex);
                    ofield.setPosition(colIndex, rowIndex);
                    ofield.setClickedNode(clickedNode);
                    changeColor(ofield.getEventCounter(), ofield, myBoard);
                }

            }
        });

        for (int i = 0; i < myBoard.getBoard().size(); i++) {
            boardUI.getColumnConstraints().add(new ColumnConstraints(10, Control.USE_COMPUTED_SIZE, Double.POSITIVE_INFINITY, Priority.ALWAYS, HPos.CENTER, true));
            boardUI.getRowConstraints().add(new RowConstraints(10, Control.USE_COMPUTED_SIZE, Double.POSITIVE_INFINITY, Priority.ALWAYS, VPos.CENTER, true));
        }
        primaryStage.setScene(scene);
        primaryStage.setFullScreen(true);
        primaryStage.show();


    }

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
