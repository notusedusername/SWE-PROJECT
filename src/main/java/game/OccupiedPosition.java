package game;

import javafx.scene.Node;

public class OccupiedPosition {
    /**
     * Az elfoglalandó terület koordinátáit tárolja x, y alapján
     * a táblán.
     *
     */
    private int[] position = {-1, -1};
    private Node clickedNode;
    private int eventCounter;

    public int getEventCounter() {
        return eventCounter;
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
