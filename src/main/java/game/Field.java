package game;

public class Field {
    /**
     *
     */
    private Color color;

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
