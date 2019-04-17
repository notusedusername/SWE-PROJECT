package game;


import java.util.ArrayList;

/**
 * A tábla állapotának tárolásáért felelő osztály
 */
public class Board {

    private ArrayList<ArrayList<Field>> board = new ArrayList<>();

    /**
     * A tábla kezdeti állapota
     */
    public Board() {
        for(int i = 0; i < 11; i++){
            ArrayList<Field> row = new ArrayList<>();
            for(int j = 0; j < 11; j++){
                Field field = new Field();
                if(i % 2 == 0 && j % 2 == 1){
                    field.setColor(Color.PLAYER1);
                } else if (i % 2 == 1 && j % 2 == 0) {
                    field.setColor(Color.PLAYER2);
                }
                row.add(field);
            }
            board.add(row);

        }

    }

    public void setField(int x, int y, Color player){
        board.get(x).get(y).setColor(player);
    }

    public ArrayList<ArrayList<Field>> getBoard() {
        return board;
    }

    /**
     * A táblát olvasható {@code String} formátumra alakítja,
     * a konzolos megjelenése is mátrix.
     * @return tábla string reprezentációja
     */
    @Override
    public String toString() {
        StringBuilder string = new StringBuilder();
        for (ArrayList<Field> fields : board) {
            string.append("\n");

            string.append(fields);
        }
        return string.toString();
    }
}
