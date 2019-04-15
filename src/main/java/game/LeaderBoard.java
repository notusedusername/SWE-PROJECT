package game;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.util.ArrayList;

/**
 * Tárolja az utolsó 5 győztes játékos nevét,
 * a grafikus megjelenítését is tartalmazza a listának.
 */
public class LeaderBoard {
    private static ArrayList<String> name = new ArrayList<>();

    public ArrayList<String> getName() {
        return name;
    }

    /**
     * Egy {@code VBox} típusú listát ad vissza last 5 winners címmel
     *
     * @return VBox típusú győzteslista 5 névvel
     */
    public VBox getNameAsNode() {
        VBox leaderBoard = new VBox();
        leaderBoard.setPadding(new Insets(5));
        leaderBoard.getChildren().add(new Label("Last 5 winners:\n\n"));
        for (String s : name) {
            leaderBoard.getChildren().add(new Label("\t" + s));
        }
        return leaderBoard;
    }

    /**
     * Hozzáadja a {@code param} nevet a listához, és eltávolítja
     * a legkorábbi nevet, ha már van 5 név a listában.
     *
     * @param name A hozzáadandó név
     */
    public void addName(String name) {
        if (LeaderBoard.name.size() >= 5) {
            LeaderBoard.name.remove(0);
        }
        LeaderBoard.name.add(name);

    }
}
