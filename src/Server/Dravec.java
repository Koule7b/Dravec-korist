package Server;

import java.io.PrintWriter;
import java.util.ArrayList;

/**
 * Created by stepanmudra on 19.11.16.
 */
public class Dravec extends Korist {
    private ArrayList<ArrayList<Misto>> list;
    private int zivotnost;
    private int[] pozice;
    private PrintWriter pw;
    public Dravec(ArrayList<ArrayList<Misto>> list, int [] pozice, PrintWriter pw){
        super(list, pozice, pw);
        this.list = list;
        this.pozice = pozice;
        this.pw = pw;
    }
    @Override
    public void run() {
        Korist korist = new Korist(list, pozice, pw);
        while (true) {
            rozhledniSe(list, korist);
            spi();
        }
    }
    public int getZivotnost(){
        return zivotnost;
    }
}
