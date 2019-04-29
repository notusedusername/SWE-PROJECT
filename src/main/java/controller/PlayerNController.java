package controller;

import game.Players;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;

public class PlayerNController {
    @FXML
    Button submitNames;
    @FXML
    TextField player1;
    @FXML
    TextField player2;
    @FXML
    Label warning;

    @FXML
    protected void submitNames() {
        if (player1.getText().equals("") || player2.getText().equals("")) {
            warning.setText("Type both name!");
        } else {
            Players.setPlayer(player1.getText(), player2.getText());
            Scene scene = warning.getScene();
            Parent root = null;
            try {
                root = FXMLLoader.load(getClass().getResource("/fxml/Game.fxml"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            scene.setRoot(root);
        }
    }

    @FXML
    protected void backToMain() {
        Scene scene = submitNames.getScene();
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/fxml/MainMenu.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        scene.setRoot(root);
    }
}
