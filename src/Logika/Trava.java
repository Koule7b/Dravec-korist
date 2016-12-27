package Logika;

import java.util.ArrayList;
import java.util.concurrent.ThreadFactory;

/**
 * Created by stepanmudra on 19.11.16.
 */
public class Trava extends Misto implements Runnable{
    private int zivotnost = 5;
    private ArrayList<ArrayList<Misto>> list;
    private int[] pozice = new int[2];
    public Trava(ArrayList<ArrayList<Misto>> list, int[] pozice){
        this.list = list;
        this.pozice = pozice;
    }

    @Override
    public void run() {
        while(zivotnost != 0){
            this.zivotnost -= 1;
            spi();
        }
        /**
        Thread.currentThread().interrupt();
        Misto misto = new Misto();
        list.get(pozice[0]).set(pozice[1], misto);
         */
    }

    protected void spi(){
        try {
            synchronized (Thread.currentThread()) {
                Thread.currentThread().wait(1000);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public int getZivotnost() {
        return zivotnost;
    }
}
