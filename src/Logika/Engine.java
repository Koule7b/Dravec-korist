package Logika;

import Logika.Dravec;
import Logika.Misto;
import Logika.Trava;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by stepanmudra on 19.11.16.
 */
public class Engine {
    ArrayList<ArrayList<Misto>> pole2D = new ArrayList<>();
    Random ran = new Random();

    public ArrayList<ArrayList<Misto>> vytvorMapuPoli(int radky, int sloupce) {
        for (int i = 0; i < radky; i++) {
            ArrayList<Misto> pole1D = new ArrayList<>();
            for (int j = 0; j < sloupce; j++) {
                int[] pozice = new int[2];
                switch ((ran.nextInt(4))) {
                //switch (j){
                    case 0:
                        pozice[0] = i;
                        pozice[1] = j;
                        Korist korist = new Korist(pole2D, pozice);
                        Thread t = new Thread(korist);
                        t.start();
                        pole1D.add(korist);
                        break;
                    case 1:
                        pozice[0] = i;
                        pozice[1] = j;
                        Trava trava = new Trava(pole2D, pozice);
                        Thread v = new Thread(trava);
                        v.start();
                        pole1D.add(trava);
                        break;
                    case 2:
                        pozice[0] = i;
                        pozice[1] = j;
                        Dravec dravec = new Dravec(pole2D, pozice);
                        Thread vd = new Thread(dravec);
                        vd.start();
                        pole1D.add(dravec);
                        break;
                    case 3:
                        pozice[0] = i;
                        pozice[1] = j;
                        Korist k = new Korist(pole2D, pozice);
                        Thread vk = new Thread(k);
                        vk.start();
                        pole1D.add(k);
                        break;
                    case 4:
                        pozice[0] = i;
                        pozice[1] = j;
                        Misto misto = new Misto();
                        pole1D.add(misto);
                        break;
                }
            }
            pole2D.add(pole1D);
        }
        return pole2D;
    }
}
