package game;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;

import static game.GameMaster.exit;

public class GameUtils {
    private static Logger logger = LoggerFactory.getLogger(GameUtils.class);

    /**
     * A tábla grafikus reprezentációjáért felel.
     *
     * @param myBoard A kirajzolandó tábla mátrixa
     * @param boardUI A kitöltendő grafikus elem ({@code GridPane}
     * @return A kitöltött {@code Gridpane} elem.
     */
    static GridPane drawBoard(Board myBoard, GridPane boardUI) {
        for (int i = 0; i < myBoard.getBoard().size(); i++) {
            for (int j = 0; j < myBoard.getBoard().get(i).size(); j++) {
                StackPane square = new StackPane();
                square.setMinSize(50, 50);
                square.setStyle("-fx-background-color: "
                        + myBoard.getBoard().get(i).get(j)
                        + ";");
                boardUI.add(square, i, j);
            }
        }
        logger.info("Board initial state done");
        return boardUI;
    }

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
    static ArrayList<Field> getColumn(Board myBoard, int column) {
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
    static boolean isThereWinner(ArrayList<Field> fields) {

        Color previous = Color.NONE;

        for (int i = 0; i < fields.size(); i++) {
            System.out.println(fields.get(i).getColor());
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

    static Winner changeColor(int event, OccupiedPosition ofield, Board myBoard, BorderPane region) {

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

    static void writeTurn(Label turn, Players players, int event) {
        if (event % 2 == 1) {
            turn.setText(players.getPlayer("PLAYER1") + "'s turn");
            turn.setStyle("-fx-text-fill: navy;");
        } else {
            turn.setText(players.getPlayer("PLAYER2") + "'s turn");
            turn.setStyle("-fx-text-fill: #c90000 ;");
        }
    }

    /**
     * Egy felugró ablakkal jelzi, hogy a játék véget ért,
     * kiírja a nyertest, és felajánlja a kilépést, vagy új játékot.
     *
     * @param winner A nyertes neve {@code String}-ként
     * @param parent A létrehozó {@code Stage}, mert új játék indításakor
     *               ezt is bezárja.
     */
    static void printWinner(String winner, Stage parent) {
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
            //play(new Stage());
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
}
