package controller;

import game.LeaderBoard;
import game.Players;
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
    VBox fxleaderboard;
    @FXML
    Label congrats;
    @FXML
    void initialize() {
        fxleaderboard.getChildren().add(LeaderBoard.getLeaderBoardAsNode());
    }

    void printWinner(String winner) {
        if (winner.equals("TIE")) {
            congrats.setText("Do you know how to play??\n It's a tie.");
        } else {
            String text = "Congrats " + Players.getPlayer(winner) + ", you won!";
            congrats.setText(text);
        }
    }
    @FXML
    public void playAgain() {
        Scene scene = fxleaderboard.getScene();
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
