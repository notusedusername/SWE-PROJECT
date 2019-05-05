package testgame;

import game.Board;;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BoardTest {

    @Test
    public void boardTest() {
        Board storedBoardONE = new Board();
        Board storedBoardTWO = new Board();

        assertNotNull(storedBoardONE);
        assertNotNull(storedBoardTWO);
        assertEquals(storedBoardONE.getBoard(), storedBoardTWO.getBoard());


    }
}
