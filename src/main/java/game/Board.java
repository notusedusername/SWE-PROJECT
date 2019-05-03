package game;


import java.util.ArrayList;

/**
 * A tábla állapotának tárolásáért felelő osztály.
 */
public class Board {
    /**
     * A tábla {@code ArrayList<ArrayList<Field>>} reprezentációja.
     */
    private static ArrayList<ArrayList<Field>> board;

    /**
     * A tábla konstruktora.
     *
     * Beállítja a tábla kezdeti állapotát (minden 2. mező piros/kék).
     */
    public Board() {
        board = new ArrayList<>();
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

    /**
     * Beállítja az adott koordinátájú {@link Field} {@link Color} értékét.
     *
     * @param x      A {@link Field} x koordinátája
     * @param y      A {@link Field} y koordinátája
     * @param player Az x, y koordinátájú {@link Field} kívánt színe
     */
    public void setFieldColor(int x, int y, Color player) {
        board.get(x).get(y).setColor(player);
    }

    /**
     * A tábla reprezentációjának getter függvénye.
     * @return A tábla {@code ArrayList<ArrayList<Field>>} formában
     */
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
