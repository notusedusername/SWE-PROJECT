package game;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import java.util.ArrayList;


/**
 * Tarolja az utolso 5 gyoztes jatekos nevet,
 * a grafikus megjeleniteset is tartalmazza a listanak.
 */
@javax.xml.bind.annotation.XmlRootElement(name = "leaderboard")
@XmlAccessorType(XmlAccessType.FIELD)
public class LeaderBoard {

    @XmlElementWrapper(name = "players")
    @XmlElement(name = "player")
    private static ArrayList<String> name = new ArrayList<>();

    public ArrayList<String> getName() {
        return name;
    }

    /**
     * Egy {@code VBox} tipusu listat ad vissza last 5 winners cimmel
     *
     * @return VBox tipusu gyozteslista 5 nevvel
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

    public static void setName(ArrayList<String> name) {
        LeaderBoard.name = name;
    }

    /**
     * Hozzaadja a {@code param} nevet a listahoz, es eltavolitja
     * a legkorabbi nevet, ha mar van 5 nev a listaban.
     *
     * @param name A hozzaadando nev
     */
    public void addName(String name) {
        if (LeaderBoard.name.size() >= 5) {
            LeaderBoard.name.remove(0);
        }
        LeaderBoard.name.add(name);

    }
}



