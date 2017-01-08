package SimulaceDravecKorist.Server;

import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by stepanmudra on 19.11.16.
 */
public class Trava extends Misto implements Runnable, Serializable{
    private int zivotnost = 5;
    private ArrayList<ArrayList<Misto>> list;
    private int[] pozice = new int[2];
    private ObjectOutputStream pw;
    public Trava(ArrayList<ArrayList<Misto>> list, int[] pozice, ObjectOutputStream pw){
        this.list = list;
        this.pozice = pozice;
        this.pw = pw;
    }

    @Override
    public void run() {
        while (zivotnost > 0) {
            spi(10000);
            zivotnost--;
        }
    }

    /**
     * metoda která uspí vlákno na určitý čas předaný parametrem
     */
    protected void spi(int cas){
        try {
            synchronized (Thread.currentThread()) {
                Thread.currentThread().wait(cas);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
