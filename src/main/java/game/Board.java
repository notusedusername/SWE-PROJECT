package game;


import java.util.ArrayList;

public class Board {

    private ArrayList<ArrayList<Field>> board = new ArrayList<ArrayList<Field>>();

    /**
     * A tábla kezdeti állapota
     */
    public Board() {
        for(int i = 0; i < 11; i++){
            ArrayList<Field> row=new ArrayList<Field>();
            for(int j = 0; j < 11; j++){
                Field field = new Field();
                if(i % 2 == 0 && j % 2 == 1){
                    field.setColor(Color.PLAYER1);
                }
                else if(i % 2 == 1 && j % 2 == 0){
                    field.setColor(Color.PLAYER2);
                }
                row.add(field);
            }
            board.add(row);

        }

    }

    /**
     * A táblát olvasható {@code String} formátumra alakítja
     * @return tábla string reprezentációja
     */
    @Override
    public String toString() {
        StringBuilder string = new StringBuilder();
        for(int i = 0; i < board.size(); i++){

            string.append("\n");

            string.append(board.get(i));
        }
        return string.toString();
    }
}
