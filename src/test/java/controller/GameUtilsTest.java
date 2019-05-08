package controller;

import game.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

public class GameUtilsTest {

    private ArrayList<Field> fillWithSame(ArrayList<Field> fields){
        for(int i = 0; i < 5; i++){
            Field field = new Field();
            field.setColor(Color.PLAYER1);
            fields.add(field);
        }
        return null;
    }


    @Test
    public void getcolumnTest(){
        Board board = new Board();
        ArrayList<Field> column = GameUtils.getColumn(board, 0);
        assertNotNull(column.get(0));
        assertEquals(Color.NONE, column.get(0).getColor());
        assertNotNull(column.get(1));
        assertEquals(Color.PLAYER2, column.get(1).getColor());

    }

    @Test
    public  void isThereAwinnerTest(){
        ArrayList<Field> fields = new ArrayList<>();
        fillWithSame(fields);
        assertTrue(GameUtils.isThereWinner(fields));

        Field field = new Field();
        field.setColor(Color.PLAYER2);
        fields.add(field);

        assertFalse(GameUtils.isThereWinner(fields));
    }
}
