package game;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;


public class GameController {

    private static Logger logger = LoggerFactory.getLogger(GameUtils.class);
    @FXML
    GridPane board;
    @FXML
    BorderPane root;
    @FXML
    Label p1Name;
    @FXML
    Label p2Name;
    @FXML
    Label playerTurn;

    @FXML
    void initialize() {
        Board myBoard = new Board();
        board = GameUtils.drawBoard(myBoard, board);
        p1Name.setText(Players.getPlayer("PLAYER1") + "\n (Horizontal)");
        p2Name.setText(Players.getPlayer("PLAYER2") + "\n (Vertical)");
    }

    @FXML
    protected void backToMain() {
        Scene scene = board.getScene();
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/fxml/MainMenu.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        scene.setRoot(root);
    }

    @FXML
    protected void exit() {
        new MainMenuController().exit();
    }

    @FXML
    protected void gridClicked(MouseEvent mouseEvent) {
        Node clickedNode = mouseEvent.getPickResult().getIntersectedNode();
        Integer colIndex = GridPane.getColumnIndex(clickedNode);
        Integer rowIndex = GridPane.getRowIndex(clickedNode);
        Board myBoard = new Board();
        Players players = new Players();
        OccupiedPosition ofield = new OccupiedPosition();
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
            GameUtils.writeTurn(playerTurn, players, ofield.getEventCounter());
            String winner = GameUtils.changeColor(ofield.getEventCounter(), ofield, myBoard, root).toString();
            if (!winner.equals("NONE")) {
                if (winner.equals("TIE")) {
                    GameUtils.printWinner(winner, (Stage) root.getScene().getWindow());
                } else {
                    GameUtils.printWinner(players.getPlayer(winner), (Stage) root.getScene().getWindow());
                }
            }
        }

    }
}