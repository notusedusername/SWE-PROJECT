package controller;

import game.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;



public class GameController {

    private static Logger logger = LoggerFactory.getLogger(GameController.class);
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

    private Board myBoard = new Board();

    @FXML
    void initialize() {
        board = GameUtils.drawBoard(myBoard, board);
        p1Name.setText(Players.getPlayer("PLAYER1") + "\n (Horizontal)");
        p2Name.setText(Players.getPlayer("PLAYER2") + "\n (Vertical)");
        playerTurn.setText(Players.getPlayer("PLAYER1") + "'s turn");
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
            GameUtils.writeTurn(playerTurn, ofield.getEventCounter());
            String winner = GameUtils.changeColor(ofield.getEventCounter(), ofield, myBoard, root).toString();
            if (!winner.equals("NONE")) {
                switchScene(winner);
            }
        }

    }

    @FXML
    private void switchScene(String winner) {

        Scene scene = board.getScene();
        Parent root = null;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/WinnerPopUp.fxml"));// FIXME: 2019.04.29.

        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        WinnerPUController controller = loader.getController();
        controller.setWinner(winner);
        controller.setLeaderboard(getUpdatedLeaderBoard(winner));
        scene.setRoot(root);
        System.out.println(scene.getRoot());

    }

    LeaderBoard getUpdatedLeaderBoard(String winner) {
        LeaderBoard leaderBoard = JAXBUtil.read(logger);
        leaderBoard.addName(winner);
        JAXBUtil.write(leaderBoard, logger);
        return leaderBoard;
    }
}