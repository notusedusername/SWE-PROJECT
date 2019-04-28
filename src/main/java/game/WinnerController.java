package game;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;

import javax.xml.bind.JAXBException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class WinnerController {
    @FXML
    VBox leaderboard = null;

    @FXML
    public void initialize() {
        LeaderBoard ranks = new LeaderBoard();
        try {
            ranks = JAXBUtil.fromXML(game.LeaderBoard.class, new FileInputStream(System.getProperty("user.home")
                    + "/ColorWar/leaderboard.xml"));
            //logger.info("Try to read leaderboard.xml"); todo
        } catch (JAXBException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            //logger.info("No leaderboard found");
        }
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
