package SimulaceDravecKorist.Server.Druhy;

import SimulaceDravecKorist.Bod;

import static SimulaceDravecKorist.Bod.StavBodu.TRAVA;

/**
 * Created by stepanmudra on 20.01.17.
 */
public class Trava  extends  Prazdno{

    int pocetNasledujcichCyklu = 9;

    public Trava(int x, int y) {
        super(x, y);
    }

    public int getPocetNasledujcichCyklu() {
        return pocetNasledujcichCyklu;
    }

    public void setPocetNasledujcichCyklu(int pocetNasledujcichCyklu) {
        this.pocetNasledujcichCyklu = pocetNasledujcichCyklu;
    }
    public Bod getBod() {
        return new Bod(x , y, TRAVA);
    }
}
