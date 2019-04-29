package controller;

import game.LeaderBoard;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;

public class WinnerController {

    private static Logger logger = LoggerFactory.getLogger(WinnerController.class);
    @FXML
    VBox leaderboard;

    @FXML
    public void initialize() {
        LeaderBoard ranks = JAXBUtil.read(logger);
        leaderboard.getChildren().add(ranks.getNameAsNode());
    }

    @FXML
    protected void backToMain() {
        Scene scene = leaderboard.getScene();
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/fxml/MainMenu.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        scene.setRoot(root);
    }

}
