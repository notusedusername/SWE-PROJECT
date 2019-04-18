package game;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

    @XmlElement(name = "player")
    private static Map<String, Integer> ranks = new LinkedHashMap<>();

    private static Logger logger = LoggerFactory.getLogger(LeaderBoard.class);

    /**
     * Egy {@code VBox} tipusu listat ad vissza, az 5 legtöbbet nyert játékos nevével
     * és győzelmeik számával.
     * {@code ranks} ID-vel hivatkozható CSS-ből a {@code VBox}
     * @return {@code VBox} tipusu gyozteslista
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
        logger.info("Leaderboard sorted");
        if (ranks.size() > 0) {
            int i = 0;
            for (String s : ranks.keySet()) {
                Label name = new Label(s);
                Label wins = new Label(ranks.get(s).toString());
                HBox left = new HBox(name);
                HBox right = new HBox(wins);
                left.setMinWidth(300);
                right.setMinWidth(300);
                left.setAlignment(Pos.CENTER_RIGHT);
                right.setAlignment(Pos.CENTER_RIGHT);

                if (i == 0) {
                    name.setText("Name");
                    wins.setText("Wins");
                    name.setStyle("-fx-underline: true; -fx-font-style: italic;");
                    wins.setStyle("-fx-underline: true; -fx-font-style: italic;");
                }
                leaderBoard.getChildren().add(new HBox(left, right));
                i++;
                if (i == 5) {
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
            logger.info("Player record updated");
        } else {
            ranks.put(name, 1);
            logger.info("New player record added");
        }
    }
}



