package game;

import javafx.scene.Node;

/**
 * A játékosok által elfogalt mezőket kezelő osztály
 */
public class OccupiedPosition {
    /**
     * Az elfoglalandó terület koordinátáit tárolja x, y alapján
     * a táblán, {@code -1, -1}-re inicializálva.
     *
     */
    private int[] position = {-1, -1};
    /**
     * Az elfoglalandó terület grafikus megjelenése
     */
    private Node clickedNode;
    /**
     * Az elfoglalásesemények száma, a {@code game.GameMaster play()} függvénye
     * minden új játékesemény során egyszer példányosítja az osztályt, majd a
     * {@code setEventCounter(Integer eventCounter)} segítségével változtatja az értéket,
     * ezért nem {@code static}.
     */
    private int eventCounter;

    public int getEventCounter() {
        return eventCounter;
    }

    public boolean isTheBoardFull(Board board) {
        int boardSize = board.getBoard().size();
        return getEventCounter() >= (boardSize * boardSize) / 2;
    }

    public void setEventCounter(Integer eventCounter) {
        this.eventCounter = eventCounter;
    }

    public Node getClickedNode() {
        return clickedNode;
    }

    public void setClickedNode(Node clickedNode) {
        this.clickedNode = clickedNode;
    }

    public int[] getPosition() {
        return position;
    }

    public void setPosition(int x, int y) {
        this.position[0] = x;
        this.position[1] = y;
    }
}
