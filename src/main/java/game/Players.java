package game;

import java.util.ArrayList;

public class Players {
    private ArrayList<String> players = new ArrayList<>();

    public String getPlayer(String player) {
        if (player.equals("PLAYER1")) {
            return players.get(0);
        } else {
            return players.get(1);
        }
    }

    public void setPlayer(String player1, String player2) {
        this.players.add(player1);
        this.players.add(player2);
    }

}
