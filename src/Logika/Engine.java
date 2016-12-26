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
                //switch ((ran.nextInt(4))) {
                switch (j){
                    case 0:
                        pozice[0] = i;
                        pozice[1] = j;
                        Korist korist = new Korist(pole2D, pozice);
                        Thread t = new Thread(korist);
                        t.start();
                        pole1D.add(korist);
                        break;
                    case 1:
                        pole1D.add(new Trava(pole2D));
                        break;
                    case 2:
                        pole1D.add(new Dravec(pole2D, pozice));
                        break;
                    case 3:
                        pole1D.add(new Trava(pole2D));
                        break;
                    case 4:
                        pole1D.add(new Misto());
                        break;
                }
            }
            pole2D.add(pole1D);
        }
        return pole2D;
    }
}
