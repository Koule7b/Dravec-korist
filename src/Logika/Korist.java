package Logika;

import java.util.ArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.ObjDoubleConsumer;

/**
 * Created by stepanmudra on 19.11.16.
 */
public class Korist extends Trava implements Runnable{
    private ArrayList<ArrayList<Misto>> list;
    private int[] pozice;
    private final Object lock = new Object();
    private final Object lock2 = new Object();
    private final Object lock3 = new Object();
    private boolean stoji = false;
    private int neco = 9;
    private boolean konecKola = false;
    public Korist(ArrayList<ArrayList<Misto>> list, int[] pozice){
        super(list);
        this.list = list;
        this.pozice = pozice;
    }
    @Override
    public void run() {
        while (Thread.currentThread().isAlive()) {
            synchronized (lock) {
                rozhledniSe(list);
                //System.out.println(list);
            }
            //System.out.println("l");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if(neco == 0){
                //Thread.currentThread().destroy();
            }
            konecKola = false;
            //System.out.println("u");
        }
    }
    private synchronized void rozhledniSe(ArrayList<ArrayList<Misto>> list){
        //System.out.println(Arrays.toString(pozice));
        for (int i = ((pozice[0] - 1) < 0) ? 0 : (pozice[0] - 1); i <= (((pozice[0] + 1) < list.size())?(pozice[0]+1):(list.size()-1)); i++) {
            for (int j = ((pozice[0] - 1) < 0) ? 0 : (pozice[0] - 1); j <= (((pozice[1] + 1) < list.get(i).size())?(pozice[1]+1):(list.get(i).size()-1)); j++) {
                //System.out.print(list.get(i).get(j)+" ");
                //System.out.println(list);
                if(!konecKola) {
                    synchronized (lock2) {

                        /**
                        if (list.get(i).get(j).getClass().equals(Misto.class)) {
                            //System.out.println();
                            //System.out.print(list.get(i).get(j)+" ");
                            //System.out.println();
                            //presunSe(i, j);
                            //rozmnozSe(i, j);
                            konecKola = true;
                        }
                         */
                        if (list.get(i).get(j).getClass().equals(Trava.class)) {
                            snez(i, j);
                            konecKola = true;
                        }
                    }
                }
            }
        }
        this.neco--;
    }
    private synchronized void presunSe(int x, int y){
        int[] pom = new int[2];
        pom[0] = x;
        pom[1] = y;
        Misto misto = list.get(x).get(y);
        list.get(x).set(y, this);
        list.get(pozice[0]).set(pozice[1], misto);
        System.out.println(list);
    }
    private synchronized void rozmnozSe(int x, int y){
        int[] pom = new int[2];
        pom[0] = x;
        pom[1] = y;
        Korist korist = new Korist(list, pom);
        Thread t = new Thread(korist);
        t.start();
        synchronized (lock) {
            list.get(x).set(y, korist);
        }
        System.out.println(list);
    }
    public boolean getStoji(){
    return stoji;}
    private synchronized void snez(int x, int y){
        this.neco += list.get(x).get(y).getZivotnost();
        Misto misto = new Misto();
        System.out.println(Thread.currentThread().getId()+" "+list);
        synchronized (lock3){
            list.get(x).set(y, misto);
        }
        System.out.println(Thread.currentThread().getId()+" "+list);
        System.out.println(Thread.currentThread().getId()+" "+getZivotnost());
    }

    public int getZivotnost() {
        return neco;
    }
}
