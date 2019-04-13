package game;

public class Field {
    /**
     *A tábla egy mezőjét reprezentálja,
     * egy tulajdonsága van, a színe
     */
    private Color color;

    /**
     * Az alapértelmezett szín fehér ({@code Color.NONE}).
     */
    public Field() {
        this.color = Color.NONE;
    }

    public void setColor(Color color){
        this.color = color;
    }

    public Color getColor() {
        return color;
    }

    @Override
    public String toString(){
        return color.getColor();
    }
}
