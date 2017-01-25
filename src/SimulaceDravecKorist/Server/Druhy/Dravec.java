package SimulaceDravecKorist.Server.Druhy;

import SimulaceDravecKorist.Bod;

import static SimulaceDravecKorist.Bod.StavBodu.DRAVEC;

/**
 * Created by stepanmudra on 20.01.17.
 */
public class Dravec extends Zvire {
    int pocetNasledujcichCyklu = 10;

    public Dravec(int x, int y) {
        super(x, y);
    }
    public int getPocetNasledujcichCyklu() {
        return pocetNasledujcichCyklu;
    }

    public void setPocetNasledujcichCyklu(int pocetNasledujcichCyklu) {
        this.pocetNasledujcichCyklu = pocetNasledujcichCyklu;
    }
    public Bod getBod() {
        return new Bod(x , y, DRAVEC);
    }
}
