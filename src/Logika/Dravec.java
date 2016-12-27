package Logika;

import Logika.Misto;

import java.util.ArrayList;

/**
 * Created by stepanmudra on 19.11.16.
 */
public class Dravec extends Korist {
    private ArrayList<ArrayList<Misto>> list;
    private int zivotnost;
    private int[] pozice;
    public Dravec(ArrayList<ArrayList<Misto>> list, int [] pozice){
        super(list, pozice);
        this.list = list;
        this.pozice = pozice;
    }
    @Override
    public void run() {
        Korist korist = new Korist(list, pozice);
        while (true) {
            rozhledniSe(list, korist);
        }
    }
    public int getZivotnost(){
        return zivotnost;
    }
}
