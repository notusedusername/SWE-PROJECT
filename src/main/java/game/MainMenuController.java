package game;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;


public class MainMenuController {
    @FXML
    protected void startGame(ActionEvent event) {

    }

    @FXML
    protected void showWinners(ActionEvent event) {

    }

    @FXML
    protected void exit(ActionEvent event) {
        GameMaster.exit();
    }
}
