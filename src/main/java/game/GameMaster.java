package game;


import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;

/**
 * A projekt fő osztálya, a az egész játékmenetért felel.
 */
public class GameMaster extends Application {

    private static Logger logger = LoggerFactory.getLogger(GameMaster.class);
    /**
     * Egy tábla {@code column}. oszlopát adja vissza.
     * Azért van rá szükség, mert a {@code Board} táblát leképező adatszerketete
     * egy {@code ArrayList<ArrayList<Field>>}, aminek csak a sorát kérhetjük le
     * beépített függvénnyel.
     *
     * @param myBoard {@code Board} típusú objektum, a bemeneti tábla
     * @param column  a tábla adott oszlopindexe
     * @return {@code ArrayList<Field>} érték, a tábla egy oszlopa
     */
    private ArrayList<Field> getColumn(Board myBoard, int column) {
        ArrayList<Field> toReturn = new ArrayList<>();
        for (int i = 0; i < myBoard.getBoard().size(); i++) {
            toReturn.add(myBoard.getBoard().get(i).get(column));
        }
        return toReturn;
    }

    /**
     * Megvizsgálja, hogy az adott {@code Field}-eket tartalmazó lista
     * elemei egyszínűek-e.
     *
     * @param fields A {@code Field} típusú lista
     * @return Ha mind egyszínű igaz, egyébként hamis.
     */
    private boolean isThereWinner(ArrayList<Field> fields) {

        Color previous = Color.NONE;

        for (int i = 0; i < fields.size(); i++) {

            if (i == 0) {
                previous = fields.get(i).getColor();
                //continue;
            } else {
                if (previous != fields.get(i).getColor()) {
                    return false;
                }
            }

        }
        return true;
    }

    /**
     * A tábla grafikus reprezentációjáért felel.
     *
     * @param myBoard A kirajzolandó tábla mátrixa
     * @param boardUI A kitöltendő grafikus elem ({@code GridPane}
     * @return A kitöltött {@code Gridpane} elem.
     */
    private GridPane drawBoard(Board myBoard, GridPane boardUI) {
        for (int i = 0; i < myBoard.getBoard().size(); i++) {
            for (int j = 0; j < myBoard.getBoard().get(i).size(); j++) {
                StackPane square = new StackPane();
                square.setMaxSize(66, 66);
                square.setStyle("-fx-background-color: "
                        + myBoard.getBoard().get(i).get(j)
                        + ";");
                boardUI.add(square, i, j);
            }
        }
        return boardUI;
    }

    /**
     * A táblán a játékosok által "elfoglalt" mezőket színezi át a megfelelő színűre.
     *
     * @param event   Számolja a mezőfogalási eseményeket (fontos, hogy tudjuk ki lép)
     * @param ofield  A bekattinott {@code Node} elem
     * @param myBoard A jelenlegi játéktábla
     * @param region  A {@code Stage}, ahol a tábla elhelyezkedik (változtatja a háttér színét az
     *                aktív játékos alapján.
     * @return A nyertes játékos
     */
    private Winner changeColor(int event, OccupiedPosition ofield, Board myBoard, BorderPane region) {

        if (ofield.isTheBoardFull(myBoard)) {
            return Winner.TIE;
        }
        int x = ofield.getPosition()[0];
        int y = ofield.getPosition()[1];
        Node clickedNode = ofield.getClickedNode();
        if (event % 2 == 0) {
            region.setStyle("-fx-background-color: #ff5b5b;");
            myBoard.setField(x, y, Color.PLAYER1);
            clickedNode.setStyle("-fx-background-color: "
                    + Color.PLAYER1.getColor()
                    + ";");
            logger.info("{} turn", Color.PLAYER1.getColor());

            if (isThereWinner(getColumn(myBoard, y))) {
                logger.info(Color.PLAYER1 + " won");
                return Winner.PLAYER1;
            }
        } else {
            region.setStyle("-fx-background-color: #4286f4;");
            myBoard.setField(x, y, Color.PLAYER2);
            clickedNode.setStyle("-fx-background-color: "
                    + Color.PLAYER2.getColor()
                    + ";");
            logger.info("{} turn", Color.PLAYER2.getColor());
            if (isThereWinner(myBoard.getBoard().get(x))) {
                logger.info(Color.PLAYER2 + " won");
                return Winner.PLAYER2;
            }
        }

        ofield.setEventCounter(ofield.getEventCounter() + 1);
        return Winner.NONE;
    }

    private void getPlayerNames(Players players, Stage parent) {
        logger.info("Getting playernames");
        Stage playerStage = new Stage();
        playerStage.setTitle("Color War");
        TextField player1 = new TextField();
        TextField player2 = new TextField();

        player1.setMaxWidth(500);
        player2.setMaxWidth(500);


        Label blue = new Label("Blue:");
        Label red = new Label("Red:");
        blue.setId("blueLabel");
        blue.setId("redLabel");
        player1.setPromptText("Name of BLUE Player");

        player2.setPromptText("Name of RED Player");
        Button submit = new Button("Submit");
        Button cancel = new Button("Cancel");
        Label warning = new Label("");
        warning.setStyle("-fx-text-fill: red;");
        HBox buttons = new HBox();
        buttons.setAlignment(Pos.CENTER);
        buttons.getChildren().addAll(submit, cancel);
        VBox vbox = new VBox();
        vbox.setId("playernames");
        vbox.getChildren().addAll(blue, player1, red, player2, warning, buttons);
        vbox.setAlignment(Pos.CENTER);
        submit.setOnMouseClicked(e -> {
            if ((player1.getText() != null && !player1.getText().isEmpty())
                    && (player1.getText() != null
                    && !player2.getText().isEmpty())) {
                players.setPlayer(player1.getText(), player2.getText());
                playerStage.close();
                play(parent);

            } else {
                warning.setText("Type both name!");
            }
        });
        cancel.setOnMouseClicked(e -> {
            playerStage.close();
            start(parent);
        });
        Scene playerScene = new Scene(vbox);
        playerScene.getStylesheets().add("styles/Styles.css");
        playerStage.setScene(playerScene);
        playerStage.setFullScreen(true);
        playerStage.setFullScreenExitHint("");
        playerStage.showAndWait();

    }

    private void writeTurn(Label turn, Players players, int event) {
        if (event % 2 == 1) {
            turn.setText(players.getPlayer("PLAYER1") + "'s turn");
            turn.setStyle("-fx-text-fill: navy;");
        } else {
            turn.setText(players.getPlayer("PLAYER2") + "'s turn");
            turn.setStyle("-fx-text-fill: #c90000 ;");
        }
    }

    /**
     * Megjeleníti a játékablakot a táblával és a {@code sideMenu}-vel.
     *
     * @param game A felhasználandó {@code Stage}, ahová rajzolhat
     */
    private void play(Stage game) {
        logger.info("Game Starting");

        Players players = new Players();
        Board myBoard = new Board();
        OccupiedPosition ofield = new OccupiedPosition();
        BorderPane root = new BorderPane();
        Scene scene = new Scene(root);
        scene.getStylesheets().add("styles/Styles.css");
        Label title = new Label("Color War");

        title.setId("playtitle");

        root.setPrefSize(720, 1280);
        //Button saveState = new Button("Save");
        Button mainMenu = new Button("Back to Main Menu");
        Button exitGame = new Button("Exit");

        Label playerList = new Label("Player List:");
        Label p1Name = new Label("\t" + players.getPlayer("PLAYER1") + "\n \t(Horizontal)");
        Label p2Name = new Label("\t" + players.getPlayer("PLAYER2") + "\n \t(Vertical)");

        p1Name.setStyle(
                "    -fx-text-fill: navy;");
        p2Name.setStyle(
                "-fx-text-fill:#c90000");

        VBox sidemenu = new VBox();
        sidemenu.setAlignment(Pos.CENTER_LEFT);
        sidemenu.setSpacing(5);
        sidemenu.getChildren().addAll(title, mainMenu, exitGame);

        VBox sidelist = new VBox();
        sidelist.getChildren().addAll(playerList, p1Name, p2Name);
        sidelist.setAlignment(Pos.CENTER_LEFT);
        sidelist.setSpacing(5);

        VBox container = new VBox();
        container.setId("game");
        container.getChildren().addAll(sidemenu, sidelist);
        container.setAlignment(Pos.CENTER_LEFT);
        container.setSpacing(100);

        root.setStyle("-fx-background-color:  #4286f4;");
        root.setLeft(container);

        Label playerTurn = new Label(players.getPlayer("PLAYER1") + "'s turn");
        playerTurn.setStyle(
                "    -fx-text-fill: navy;");
        HBox turn = new HBox(playerTurn);
        turn.setAlignment(Pos.CENTER_RIGHT);
        turn.minWidth(root.getPrefWidth());
        root.setTop(turn);
        turn.setId("turn");
        BorderPane.setAlignment(playerTurn, Pos.CENTER_RIGHT);
        /*
        saveState.setOnMouseClicked(mouseEvent -> {
            //TODO mentés opcionálisan
            logger.info();("Save gamestate");
        });
        */

        mainMenu.setOnMouseClicked(mouseEvent -> {
            logger.info("Back to main menu");
            start(game);
        });
        exitGame.setOnMouseClicked(mouseEvent -> {
            logger.info("Exiting");
            exit();
        });
        GridPane boardUI = drawBoard(myBoard, new GridPane());
        root.setCenter(boardUI);
        boardUI.setMinSize(660, 660);
        boardUI.setMaxSize(660, 660);
        boardUI.setGridLinesVisible(true);


        scene.addEventFilter(MouseEvent.MOUSE_PRESSED, mouseEvent -> {
            Node clickedNode = mouseEvent.getPickResult().getIntersectedNode();
            Integer colIndex = GridPane.getColumnIndex(clickedNode);
            Integer rowIndex = GridPane.getRowIndex(clickedNode);
            try {
                myBoard.getBoard().get(colIndex).get(rowIndex).getColor();

            } catch (NullPointerException e) {
                logger.info("A játékos a griden kívülre kattintott");
                return;
            }
            if (myBoard.getBoard().get(colIndex).get(rowIndex).getColor() == Color.NONE) {
                logger.info("x: " + colIndex + " y: " + rowIndex);
                ofield.setPosition(colIndex, rowIndex);
                ofield.setClickedNode(clickedNode);
                writeTurn(playerTurn, players, ofield.getEventCounter());
                String winner = changeColor(ofield.getEventCounter(), ofield, myBoard, root).toString();
                if (!winner.equals("NONE")) {
                    if (winner.equals("TIE")) {
                        printWinner(winner, game);
                    } else {
                        printWinner(players.getPlayer(winner), game);
                    }
                }
            }

        });

        for (int i = 0; i < myBoard.getBoard().size(); i++) {
            boardUI.getColumnConstraints().add(new ColumnConstraints(10, Control.USE_COMPUTED_SIZE, Double.POSITIVE_INFINITY, Priority.ALWAYS, HPos.CENTER, true));
            boardUI.getRowConstraints().add(new RowConstraints(10, Control.USE_COMPUTED_SIZE, Double.POSITIVE_INFINITY, Priority.ALWAYS, VPos.CENTER, true));
        }
        game.setScene(scene);
        game.setFullScreenExitHint("");
        game.setFullScreen(true);
        game.show();
    }

    /**
     * Egy felugró ablakkal jelzi, hogy a játék véget ért,
     * kiírja a nyertest, és felajánlja a kilépést, vagy új játékot.
     *
     * @param winner A nyertes neve {@code String}-ként
     * @param parent A létrehozó {@code Stage}, mert új játék indításakor
     *               ezt is bezárja.
     */
    private void printWinner(String winner, Stage parent) {
        Stage winnerScreen = new Stage();
        winnerScreen.setTitle("Winner");
        LeaderBoard leaderBoard = new LeaderBoard();

        try {
            leaderBoard = JAXBUtil.fromXML(game.LeaderBoard.class, new FileInputStream(System.getProperty("user.home")
                    + "/ColorWar/leaderboard.xml"));
            logger.info("Try to read leaderboard.xml");
        } catch (JAXBException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            logger.info("No leaderboard found");
        }

        leaderBoard.addName(winner);
        try {
            File file = new File(System.getProperty("user.home") + "/ColorWar");
            logger.info("Try to write the new leaderboard state.");
            if (!file.exists()) {
                if (file.mkdir()) {
                    logger.info("No game directory. Make new directory");
                } else {
                    logger.error("mkdir error");
                }
            }
            JAXBUtil.toXML(leaderBoard, new FileOutputStream(System.getProperty("user.home") + "/ColorWar/leaderboard.xml"));
            logger.info("leaderboard.xml updated");
        } catch (FileNotFoundException | JAXBException e) {
            e.printStackTrace();
        }

        Label congrats = new Label("Congrats " + winner + ", you won!");
        Button playAgain = new Button("Play again");
        Button exit = new Button("That was enough for us");

        Image shrek = new Image("styles/shrek.jpg");
        ImageView shrekview = new ImageView(shrek);

        congrats.prefWidth(50);
        congrats.setWrapText(true);
        congrats.setId("congrats");


        HBox bottom = new HBox();
        bottom.getChildren().addAll(playAgain, exit);
        BorderPane root = new BorderPane();
        root.setTop(congrats);
        BorderPane.setAlignment(congrats, Pos.BOTTOM_LEFT);
        VBox leaderboard = leaderBoard.getNameAsNode();
        root.setLeft(leaderboard);
        BorderPane.setAlignment(leaderboard, Pos.TOP_CENTER);
        root.setRight(shrekview);
        BorderPane.setAlignment(shrekview, Pos.TOP_CENTER);
        root.setBottom(bottom);
        bottom.setAlignment(Pos.BOTTOM_RIGHT);
        BorderPane.setAlignment(bottom, Pos.BOTTOM_RIGHT);

        root.setId("winnerscreen");
        playAgain.setOnMouseClicked(mouseEvent -> {
            winnerScreen.close();
            play(new Stage());
            parent.close();
        });
        exit.setOnMouseClicked(mouseEvent -> exit());

        if (winner.equals("TIE")) {
            congrats.setText("Do you know how to play??\n It's a tie.");
        }
        Scene scene = new Scene(root);
        scene.getStylesheets().add("styles/Styles.css");
        winnerScreen.setFullScreen(true);
        winnerScreen.setFullScreenExitHint("");
        winnerScreen.setScene(scene);
        winnerScreen.show();
    }

    /**
     * Egy felugró ablakot hoz létre, felkínálja a visszalépést,
     * ekkor ez az új ablak bezár és semmi sem történik; ha megerősítjük a
     * program {@code System.exit(0)} paranccsal kilép.
     */
    private void exit() {
        Stage dialog = new Stage();
        dialog.setTitle("You are about to exit");
        BorderPane root = new BorderPane();
        root.setId("popup");
        Label question = new Label("Are you sure?");
        Button ok = new Button("Yes");
        Button cancel = new Button("Cancel");

        root.setTop(question);
        HBox buttons = new HBox();
        buttons.getChildren().addAll(ok, cancel);
        root.setBottom(buttons);
        buttons.setAlignment(Pos.BOTTOM_RIGHT);

        root.setMinSize(300, 100);

        Scene scene = new Scene(root);
        scene.getStylesheets().add("styles/Styles.css");
        dialog.setScene(scene);
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.show();


        ok.setOnMouseClicked(mouseEvent -> {
            logger.info("Exit Ensured");
            System.exit(0);
        });
        cancel.setOnMouseClicked(mouseEvent -> {
            logger.info("Exit Cancelled");
            dialog.close();
        });
        dialog.setScene(scene);
    }

    /**
     * Elindítja a program futását, létrehozza a főmenüt.
     *
     * @param primaryStage A fő programablak
     */
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Color War");
        BorderPane menuRoot = new BorderPane();
        menuRoot.setId("mainmenu");
        VBox menu = new VBox();
        Scene scene = new Scene(menuRoot, javafx.scene.paint.Color.BLACK);

        scene.getStylesheets().add("styles/Styles.css");

        Label menuTitle = new Label("Color War");
        Button startGame = new Button("New Game");
        Button winners = new Button("Winners");
        Button exitGame = new Button("Exit");

        menuTitle.setId("title");
        menu.getChildren().addAll(startGame, winners, exitGame);
        menuRoot.setCenter(menu);
        BorderPane.setAlignment(menu, Pos.BOTTOM_CENTER);
        menu.setFillWidth(true);
        menu.setAlignment(Pos.CENTER);
        menuRoot.setLeft(menuTitle);
        BorderPane.setAlignment(menuTitle, Pos.CENTER_RIGHT);


        menuRoot.setPrefSize(720, 1280);
        startGame.setOnMouseClicked(mouseEvent -> {
            logger.info("Starting a new game...");
            Players players = new Players();
            getPlayerNames(players, primaryStage);
        });
        winners.setOnMouseClicked(mouseEvent -> {
            logger.info("Enter Winners Menu");
            Label leaders = new Label("Leaderboard");
            leaders.setId("leaders");
            VBox vBox = new VBox();
            vBox.setId("winners");
            Scene lb = new Scene(vBox);
            lb.getStylesheets().add("styles/Styles.css");
            Button back = new Button("Back");
            LeaderBoard leaderBoard = new LeaderBoard();

            try {
                leaderBoard = JAXBUtil.fromXML(game.LeaderBoard.class, new FileInputStream(System.getProperty("user.home")
                        + "/ColorWar/leaderboard.xml"));
            } catch (JAXBException | FileNotFoundException e) {
                e.printStackTrace();
            }

            vBox.getChildren().addAll(leaders, leaderBoard.getNameAsNode(), back);
            vBox.setSpacing(5);
            vBox.setAlignment(Pos.CENTER);

            back.setOnMouseClicked(mouseEvent1 -> start(primaryStage));
            primaryStage.setScene(lb);
            primaryStage.setFullScreen(true);
            primaryStage.setFullScreenExitHint("");
            primaryStage.show();

        });
        exitGame.setOnMouseClicked(mouseEvent -> {
            logger.info("Exiting");
            exit();
        });

        primaryStage.setScene(scene);
        primaryStage.setFullScreenExitHint("");
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
