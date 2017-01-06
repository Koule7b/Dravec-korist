package Server;

import java.io.PrintWriter;
import java.util.ArrayList;

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
    private int zivotnost = 9;
    private boolean konecKola = false;
    private PrintWriter pw;
    public Korist(ArrayList<ArrayList<Misto>> list, int[] pozice, PrintWriter pw){
        super(list, pozice, pw);
        this.list = list;
        this.pozice = pozice;
        this.pw = pw;
    }
    @Override
    public void run() {
        Trava trava = new Trava(list, pozice, pw);
        while (Thread.currentThread().isAlive()) {
            synchronized (lock) {
                rozhledniSe(list,trava);
                //System.out.println(list);
            }
            //System.out.println("l");
            if(neco == 0){
                //Thread.currentThread().destroy();
            }
            konecKola = false;
            //System.out.println("u");
            spi();
        }
    }

    /**
     * metoda, kterou se jedinec rozhlédne, přijímá jako parametr list a případně co za potravu má hledat.
     * @param list
     * @param potrava
     */
    protected synchronized void rozhledniSe(ArrayList<ArrayList<Misto>> list, Trava potrava){
        //System.out.println(Arrays.toString(pozice));
        for (int i = ((pozice[0] - 1) < 0) ? 0 : (pozice[0] - 1); i <= (((pozice[0] + 1) < list.size())?(pozice[0]+1):(list.size()-1)); i++) {
            for (int j = ((pozice[0] - 1) < 0) ? 0 : (pozice[0] - 1); j <= (((pozice[1] + 1) < list.get(i).size())?(pozice[1]+1):(list.get(i).size()-1)); j++) {
                //System.out.print(list.get(i).get(j)+" ");
                //System.out.println(list);
                if(!konecKola) {
                        // TODO: 27.12.16  předělat tyto podmínky, tak aby se provedla jen jedna z možností, nebo přehodit do switche
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
                        if (list.get(i).get(j).getClass().equals(potrava.getClass())) {
                            snez(i, j);
                            konecKola = true;
                        }

                }
            }
        }
        this.neco--;
    }

    /**
     * metoda, která po rozhlédnutí se přesuje jedince na jiné místo.
     * @param x
     * @param y
     */
    protected synchronized void presunSe(int x, int y){
        int[] pom = new int[2];
        pom[0] = x;
        pom[1] = y;
        Misto misto = list.get(x).get(y);
        list.get(x).set(y, this);
        list.get(pozice[0]).set(pozice[1], misto);
    }

    /**
     * metoda, která rozmnoží jedince.
     * @param x
     * @param y
     */
    protected synchronized void rozmnozSe(int x, int y){
        int[] pom = new int[2];
        pom[0] = x;
        pom[1] = y;
        Korist korist = new Korist(list, pom, pw);
        Thread t = new Thread(korist);
        t.start();
        synchronized (lock) {
            list.get(x).set(y, korist);
            System.out.println(list);
        }
    }

    /**
     * metoda, která sní potravu vedle.
     * @param x
     * @param y
     */
    protected synchronized void snez(int x, int y){
        this.neco += list.get(x).get(y).getZivotnost();
        Misto misto = new Misto();
        list.get(x).set(y, misto);
        pw.println(list);
    }

    /**
     * metoda, která vrací zbývající "kola".
     * @return
     */
    public int getZivotnost() {
        return neco;
    }
}
