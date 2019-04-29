package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;


public class ExitDialogController {
    @FXML
    Button cancel;

    @FXML
    protected void EnsureExit() {
        System.exit(0);
    }

    @FXML
    protected void CancelExit() {
        Stage currentStage = (Stage) cancel.getScene().getWindow();
        currentStage.close();
    }
}
