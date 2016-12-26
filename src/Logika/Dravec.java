package Logika;

import Logika.Misto;

import java.util.ArrayList;

/**
 * Created by stepanmudra on 19.11.16.
 */
public class Dravec extends Korist {
    private ArrayList<ArrayList<Misto>> list;
    private int zivotnost;
    public Dravec(ArrayList<ArrayList<Misto>> list, int [] pozice){
        super(list, pozice);
    }
    @Override
    public void run() {

    }
    public int getZivotnost(){
        return zivotnost;
    }
}
