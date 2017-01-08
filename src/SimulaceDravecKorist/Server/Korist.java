package SimulaceDravecKorist.Server;

import SimulaceDravecKorist.Zmena;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by stepanmudra on 19.11.16.
 */
public class Korist extends Trava implements Runnable, Serializable{
    private ArrayList<ArrayList<Misto>> list;
    private int[] pozice;
    private final Object lock = new Object();
    private final Object lock2 = new Object();
    private final Object lock3 = new Object();
    private boolean stoji = false;
    private int neco = 9;
    private int zivotnost = 9;
    private boolean konecKola = false;
    private ObjectOutputStream pw;
    public Korist(ArrayList<ArrayList<Misto>> list, int[] pozice, ObjectOutputStream pw){
        super(list, pozice, pw);
        this.list = list;
        this.pozice = pozice;
        this.pw = pw;
    }
    @Override
    public void run() {
        Trava trava = new Trava(list, pozice, pw);
        while (Thread.currentThread().isAlive()) {
            if(neco == 0){
                Misto misto = new Misto();
                list.get(pozice[0]).set(pozice[1], misto);
                Zmena zmena = new Zmena(pozice[0], pozice[1], "misto");
                try {
                    pw.writeObject(zmena);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Thread.currentThread().interrupt();
            }
            rozhledniSe(list,trava);
            spi(1000);
        }
    }

    /**
     * metoda, kterou se jedinec rozhlédne, přijímá jako parametr list a případně co za potravu má hledat.
     * @param list
     * @param potrava
     */
    protected synchronized void rozhledniSe(ArrayList<ArrayList<Misto>> list, Trava potrava){
        for (int i = ((pozice[0] - 1) < 0) ? 0 : (pozice[0] - 1); i <= (((pozice[0] + 1) < list.size())?(pozice[0]+1):(list.size()-1)); i++) {
            for (int j = ((pozice[0] - 1) < 0) ? 0 : (pozice[0] - 1); j <= (((pozice[1] + 1) < list.get(i).size())?(pozice[1]+1):(list.get(i).size()-1)); j++) {
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
        Zmena zmena = new Zmena(x, y, "korist");
        list.get(pozice[0]).set(pozice[1], misto);
        Zmena zmena1 = new Zmena(pozice[0], pozice[1], "misto");
        try {
            pw.writeObject(zmena);
            pw.writeObject(zmena1);
        } catch (IOException e) {
            e.printStackTrace();
        }
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
        Zmena zmena = new Zmena(x, y, "misto");
        Object object = new Object();
        try {
            pw.writeObject(object);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * metoda, která vrací zbývající "kola".
     * @return
     */
    public int getZivotnost() {
        return neco;
    }
}
