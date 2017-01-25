package SimulaceDravecKorist.Server.Druhy;

import SimulaceDravecKorist.Bod;

import static SimulaceDravecKorist.Bod.StavBodu.KORIST;

/**
 * Created by stepanmudra on 20.01.17.
 */
public class Korist extends Dravec{
    int pocetNasledujcichCyklu = 9;
    int strach = 0;
    int hlad = 0;

    public Korist(int x, int y) {
        super(x, y);
    }

    public int getPocetNasledujcichCyklu() {
        return pocetNasledujcichCyklu;
    }

    public int getStrach() {
        return strach;
    }

    public int getHlad() {
        return hlad;
    }

    public void setPocetNasledujcichCyklu(int pocetNasledujcichCyklu) {
        this.pocetNasledujcichCyklu = pocetNasledujcichCyklu;
    }

    public void setStrach(int strach) {
        this.strach = strach;
    }

    public void setHlad(int hlad) {
        this.hlad = hlad;
    }
    public Bod getBod() {
        return new Bod(x , y, KORIST);
    }
}
