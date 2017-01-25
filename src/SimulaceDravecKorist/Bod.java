package SimulaceDravecKorist;

import java.io.Serializable;

/**
 * Created by stepanmudra on 06.01.17.
 * Je objekt, který v sobě uchovává pozici políčka, na kterém proběhla změna a nápis ve stringu.
 * Nápis říká, na co se hodnota přepsala.
 */
public class Bod implements Serializable{
    private int x;
    private int y;
    private StavBodu stav;

    public Bod(int x, int y, StavBodu stav) {
        this.x = x;
        this.y = y;
        this.stav = stav;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public StavBodu getStav() {
        return stav;
    }

    public void setStav(StavBodu stav) {
        this.stav = stav;
    }

    public enum StavBodu{
        PRAZDNO,
        TRAVA,
        KORIST,
        DRAVEC
    }
}
