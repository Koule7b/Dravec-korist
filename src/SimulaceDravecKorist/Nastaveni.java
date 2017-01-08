package SimulaceDravecKorist;

import java.io.Serializable;

/**
 * Created by stepanmudra on 06.01.17.
 * Třída ve které je uloženo počáteční nastavení simulace. Předá se Enginu pro vytvoření simulace dle nastavení.
 */
public class Nastaveni implements Serializable{
    private int sirka;
    private int vyska;
    private int procentoMista;
    private int procentoTravy;
    private int procentoKoristi;
    private int procentoDravcu;

    public Nastaveni(int sirka, int vyska, int procentoMista, int procentoTravy, int procentoKoristi, int procentoDravcu) {
        this.sirka = sirka;
        this.vyska = vyska;
        this.procentoMista = procentoMista;
        this.procentoTravy = procentoTravy;
        this.procentoKoristi = procentoKoristi;
        this.procentoDravcu = procentoDravcu;
    }

    public int getSirka() {
        return sirka;
    }

    public int getVyska() {
        return vyska;
    }

    public int getProcentoMista() {
        return procentoMista;
    }

    public int getProcentoTravy() {
        return procentoTravy;
    }

    public int getProcentoKoristi() {
        return procentoKoristi;
    }

    public int getProcentoDravcu() {
        return procentoDravcu;
    }
}
