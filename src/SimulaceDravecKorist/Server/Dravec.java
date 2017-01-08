package SimulaceDravecKorist.Server;

import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by stepanmudra on 19.11.16.
 */
public class Dravec extends Korist implements Runnable, Serializable{
    private ArrayList<ArrayList<Misto>> list;
    private int zivotnost = 5;
    private int[] pozice;
    private ObjectOutputStream pw;
    public Dravec(ArrayList<ArrayList<Misto>> list, int [] pozice, ObjectOutputStream pw){
        super(list, pozice, pw);
        this.list = list;
        this.pozice = pozice;
        this.pw = pw;
    }
    @Override
    public void run() {
        Korist korist = new Korist(list, pozice, pw);
        while (zivotnost > 0) {
            rozhledniSe(list, korist);
            spi(500);
            zivotnost--;
        }
    }
}
