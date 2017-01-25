package SimulaceDravecKorist.Server.Druhy;

import SimulaceDravecKorist.Bod;

import java.util.ArrayList;

import static SimulaceDravecKorist.Bod.StavBodu.DRAVEC;

/**
 * Created by stepanmudra on 20.01.17.
 */
public class Dravec extends Zvire {
    ArrayList<Bod> seznamBodu;
    int pocetNasledujcichCyklu = 9;

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
