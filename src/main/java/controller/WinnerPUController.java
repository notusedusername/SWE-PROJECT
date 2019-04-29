package controller;

import game.LeaderBoard;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

public class WinnerPUController {

    private static Logger logger = LoggerFactory.getLogger(WinnerPUController.class);

    @FXML
    VBox leaderboard;
    @FXML
    Label congrats;


    @FXML
    protected void printWinner(String winner) {//todo
        LeaderBoard leaderBoard = JAXBUtil.read(logger);

        leaderBoard.addName(winner);

        JAXBUtil.write(leaderBoard, logger);

        leaderboard.getChildren().add(leaderBoard.getNameAsNode());

        if (winner.equals("TIE")) {
            congrats.setText("Do you know how to play??\n It's a tie.");
        } else {
            String text = "Congrats" + winner + ", you won!";
            congrats.setText(text);
        }

    }

    @FXML
    public void playAgain() {
        Scene scene = leaderboard.getScene();
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/fxml/Game.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        scene.setRoot(root);
    }

    @FXML
    public void exit() {
        new MainMenuController().exit();
    }
}
