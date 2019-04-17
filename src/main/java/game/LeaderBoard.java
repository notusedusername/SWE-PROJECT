package game;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * Tarolja a győztes játékosok nevét és
 * a grafikus megjeleniteset is tartalmazza a listanak.
 */
@javax.xml.bind.annotation.XmlRootElement(name = "leaderboard")
@XmlAccessorType(XmlAccessType.FIELD)
public class LeaderBoard {
    /*
        @XmlElement(name = "name", required = true)
        private static ArrayList<String> name = new ArrayList<>();
        @XmlElement(name = "points", required = true)
        private  static  ArrayList<Integer> counter = new ArrayList<>();
    */
    @XmlElement(name = "player")
    private static Map<String, Integer> ranks = new LinkedHashMap<>();

    /**
     * Egy {@code ScrollPane} tipusu listat ad vissza,
     * {@code ranks} ID-vel hivatkozható CSS-ből
     * @return {@code ScrollPane} tipusu gyozteslista
     */
    VBox getNameAsNode() {
        VBox leaderBoard = new VBox();
        leaderBoard.setId("ranks");
        leaderBoard.setPadding(new Insets(5));
        leaderBoard.getChildren().add(new Label("5 most OP player:\n\n"));
        ranks = ranks.entrySet()
                .stream()
                .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue, LinkedHashMap::new));
        if (ranks.size() > 0) {
            int i = 0;
            for (String s : ranks.keySet()) {
                leaderBoard.getChildren().add(new HBox(new Label("\t" + s + ranks.get(s))));
                i++;
                if (i == 4) {
                    break;
                }
            }
        } else {
            leaderBoard.getChildren().add(new Label("\t Nothing to show..."));
        }
        return leaderBoard;
    }


    /**
     * Hozzaadja a {@code param} nevet a listahoz, vagy
     * ha a lista már tartalmazza növeli a győzelmei számát.
     *
     * @param name A hozzaadando nev
     */
    void addName(String name) {
        if (ranks.containsKey(name)) {
            ranks.put(name, ranks.get(name) + 1);
        } else {
            ranks.put(name, 1);
        }
    }
}



