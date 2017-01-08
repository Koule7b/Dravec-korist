package SimulaceDravecKorist;

import java.io.Serializable;

/**
 * Created by stepanmudra on 06.01.17.
 * Je objekt, který v sobě uchovává pozici políčka, na kterém proběhla změna a nápis ve stringu.
 * Nápis říká, na co se hodnota přepsala.
 */
public class Zmena implements Serializable{
    private int x;
    private int y;
    private String zmenenoNa;

    public Zmena(int x, int y, String zmenenoNa) {
        this.x = x;
        this.y = y;
        this.zmenenoNa = zmenenoNa;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public String getZmenenoNa() {
        return zmenenoNa;
    }
}
