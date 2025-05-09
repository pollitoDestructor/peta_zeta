package modelo;

public class Teletransporte extends Casilla {

    private String color;
    public Teletransporte(int pX, int pY, String pColor) {
        super(pX, pY);
        setOcupado(false);
        color = pColor;
    }

    public String getColor() {
        return color;
    }
}
