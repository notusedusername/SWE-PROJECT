package game;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * A projekt fő osztálya, a az egész játékmenetért felel.
 */
public class GameMaster extends Application {

    private static Logger logger = LoggerFactory.getLogger(GameMaster.class);

    /**
     * Elindítja a program futását, létrehozza a főmenüt.
     *
     * @param primaryStage A fő programablak
     */
    public void start(Stage primaryStage) {
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/fxml/MainMenu.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene scene = new Scene(root);
        scene.getStylesheets().add("styles/Styles.css");
        primaryStage.setTitle("ColorWar");
        primaryStage.setFullScreen(true);
        primaryStage.setFullScreenExitHint("");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
