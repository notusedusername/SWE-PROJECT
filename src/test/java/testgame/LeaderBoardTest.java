package testgame;

import game.LeaderBoard;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Map;


public class LeaderBoardTest {
    @Test
    public void leaderBoardTest() {
        for (int i = 0; i < 3; i++) {
            LeaderBoard.addName("TestName");
        }
        LeaderBoard.addName("TestName2");

        Integer actual = LeaderBoard.getRanks().get("TestName");
        Integer expected = 3;
        assertNotNull(LeaderBoard.getRanks());
        assertEquals(expected, actual);

        var firstEntry = LeaderBoard.getRanks().entrySet().iterator().next();
        assertEquals("TestName", firstEntry.getKey());
        assertEquals(expected, firstEntry.getValue());

        Map.Entry lastEntry = null;
        Map<String, Integer> ranks = LeaderBoard.getRanks();
        for (Map.Entry<String, Integer> e : ranks.entrySet()) {
            lastEntry = e;
        }

        expected = 1;
        assertNotNull(lastEntry);
        assertEquals("TestName2", lastEntry.getKey());
        assertEquals(expected, lastEntry.getValue());
    }
}
