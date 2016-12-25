package Logika;

import Logika.Misto;

import java.util.ArrayList;

/**
 * Created by stepanmudra on 19.11.16.
 */
public class Trava extends Misto implements Runnable{
    private int zivotnost = 5;
    private ArrayList<ArrayList<Misto>> list;
    public Trava(ArrayList<ArrayList<Misto>> list){
        this.list = list;
    }

    @Override
    public void run() {
        while(true){
            this.zivotnost -= 1;
            try {
                wait(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(zivotnost);
        }
    }
}
