package testcontroller;

import controller.GameUtils;
import game.Board;
import game.Color;
import game.Field;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

public class GameUtilsTest {

    ArrayList<Field> fillWithSame(){
        for(int i = 0; i < 5; i++){

        }
        return null;
    }

    Board makeABoard(ArrayList<ArrayList<Field>> board){
        Board toReturn = new Board();
        ArrayList<Field> temp = new ArrayList<>();
            for (int j = 0; j < 2; j++){
                Field field = new Field();
                field.setColor(Color.PLAYER1);
                temp.add(field);
            }
            board.add(temp);
        Board.setBoard(board);
        return toReturn;
    }

    @Test
    public void columnTest(){
        Board board = new Board();
        board = makeABoard(board.getBoard());
        ArrayList<Field> column = GameUtils.getColumn(board, 0);
        assertNotNull(column.get(0));
        assertEquals(Color.PLAYER1, column.get(0).getColor());
        assertNotNull(column.get(1));
        assertEquals(Color.PLAYER1, column.get(1).getColor());

    }

    @Test
    public  void winnerTest(){
        ArrayList<Field> fields = new ArrayList<>();
        for(int i = 0; i < 2; i++){
            Field field = new Field();
            field.setColor(Color.PLAYER1);
            fields.add(field);
        }
        assertTrue(GameUtils.isThereWinner(fields));

        Field field = new Field();
        field.setColor(Color.PLAYER2);
        fields.add(field);

        assertFalse(GameUtils.isThereWinner(fields));
    }
}
