package game;

/**
 * A játékmezők színét tartalmazó enum.
 *
 * Alapértelmezésként a {@code PLAYER1} kék, a {@code PLAYER2}
 * piros, az üres mező pedig fehér
 */
public enum Color {
    PLAYER1("BLUE"),
    PLAYER2("RED"),
    NONE("WHITE");

    private String color;

    Color(String color){
        this.color = color;
    }


    public String getColor(){
        return this.color;
    }
}
