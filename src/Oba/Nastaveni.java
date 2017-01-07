package Oba;

import java.io.Serializable;

/**
 * Created by stepanmudra on 06.01.17.
 */
public class Nastaveni implements Serializable{
    private int sirka;
    private int vyska;

    public Nastaveni(int sirka, int vyska) {
        this.sirka = sirka;
        this.vyska = vyska;
    }

    public int getSirka() {
        return sirka;
    }

    public int getVyska() {
        return vyska;
    }
}
