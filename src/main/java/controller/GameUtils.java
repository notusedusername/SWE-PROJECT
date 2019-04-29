package controller;


import game.*;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.ArrayList;

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
     * A táblán a játékosok által "elfoglalt" mezőket színezi át a megfelelő színűre.
     *
     * @param event   Számolja a mezőfogalási eseményeket (fontos, hogy tudjuk ki lép)
     * @param ofield  A bekattinott {@code Node} elem
     * @param myBoard A jelenlegi játéktábla
     * @param region  A {@code Stage}, ahol a tábla elhelyezkedik (változtatja a háttér színét az
     *                aktív játékos alapján.
     * @return A nyertes játékos
     */
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

    /**
     * Az éppen következő játékos nevének megjelenését
     *
     * @param turn  a módosítandó {@code Label }
     * @param event az eseményszám, amiből a soron következő játékost számoljuk
     */
    static void writeTurn(Label turn, int event) {
        if (event % 2 == 1) {
            turn.setText(Players.getPlayer("PLAYER1") + "'s turn");
            turn.setStyle("-fx-text-fill: navy;");
        } else {
            turn.setText(Players.getPlayer("PLAYER2") + "'s turn");
            turn.setStyle("-fx-text-fill: #c90000 ;");
        }
    }
}
