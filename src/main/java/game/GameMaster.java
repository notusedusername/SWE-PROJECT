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
 * A projekt fő osztálya.
 *
 * Slf4j logolást használ.
 */
public class GameMaster extends Application {

    /**
     *  A játékprogram indulását kezelő függvény.
     *
     *  Betölti a {@code MainMenu.fxml} állományt.
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
        primaryStage.setTitle("ColorWar");
        primaryStage.setFullScreen(true);
        primaryStage.setFullScreenExitHint("");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * A program belépési pontja.
     *
     * @param args parancssori argumentumok
     */
    public static void main(String[] args) {
        launch(args);
    }

}
