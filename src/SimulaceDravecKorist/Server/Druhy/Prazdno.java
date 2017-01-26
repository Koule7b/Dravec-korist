package SimulaceDravecKorist.Server.Druhy;

import SimulaceDravecKorist.Bod;

import static SimulaceDravecKorist.Bod.StavBodu.PRAZDNO;

/**
 * Created by stepanmudra on 20.01.17.
 */
public class Prazdno {
    int x;
    int y;

    public Prazdno(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Bod getBod() {
        return new Bod(x , y, PRAZDNO);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }


    public void presun(int x, int y) {
        this.x = x;
        this.y = y;
    }
}
