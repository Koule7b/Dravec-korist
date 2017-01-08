package SimulaceDravecKorist.Server;

import SimulaceDravecKorist.Nastaveni;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;

/**
 * Created by stepanmudra on 19.11.16.
 */
public class Engine {
    ArrayList<ArrayList<Misto>> pole2D = new ArrayList<>();
    Random ran = new Random();
    ObjectOutputStream oos;
    public Engine(ObjectOutputStream oos){
        this.oos = oos;
    }

    /**
     * V této metodě probíhá celé nastavení simulace dle předaného nastavení v parametru.
     * Nastavení ještě závisí na náhodě, proto říkám, že uživatelské nastavení je pouze přibližné.
     * @param nastaveni
     * @return
     */
    public ArrayList<ArrayList<Misto>> vytvorMapuPoli(Nastaveni nastaveni) {
        Stack<Thread> zasobnik = new Stack<>();
        ArrayList<ArrayList<String>> polePopisku = new ArrayList<>();
        int radky = nastaveni.getVyska();
        int sloupce = nastaveni.getSirka();
        int hraniceMista = nastaveni.getProcentoMista();
        int hraniceTravy = nastaveni.getProcentoTravy() + hraniceMista;
        int hraniceKoristi = nastaveni.getProcentoKoristi() + hraniceTravy;
        int hraniceDravcu = nastaveni.getProcentoDravcu() + hraniceKoristi;
        for (int i = 0; i < radky; i++) {
            ArrayList<Misto> pole1D = new ArrayList<>();
            ArrayList<String> radek = new ArrayList<>();
            for (int j = 0; j < sloupce; j++) {
                int[] pozice = new int[2];
                int cislo = ran.nextInt(100) + 1;
                if (0 < cislo && cislo <= hraniceMista) {
                    pozice[0] = i;
                    pozice[1] = j;
                    Misto misto = new Misto();
                    pole1D.add(misto);
                    radek.add("korist");
                } else if (hraniceMista < cislo && cislo <= hraniceTravy) {
                    pozice[0] = i;
                    pozice[1] = j;
                    Trava trava = new Trava(pole2D, pozice, oos);
                    Thread vTravy = new Thread(trava);
                    pole1D.add(trava);
                    radek.add("trava");
                    zasobnik.push(vTravy);
                } else if (hraniceTravy < cislo && cislo <= hraniceKoristi) {
                    pozice[0] = i;
                    pozice[1] = j;
                    Korist korist = new Korist(pole2D, pozice, oos);
                    Thread vKoristi = new Thread(korist);
                    pole1D.add(korist);
                    radek.add("korist");
                    zasobnik.push(vKoristi);
                }else if(hraniceKoristi < cislo && cislo <= hraniceDravcu)
                        pozice[0] = i;
                        pozice[1] = j;
                        Dravec dravec = new Dravec(pole2D, pozice, oos);
                        Thread vDravce = new Thread(dravec);
                        pole1D.add(dravec);
                        radek.add("dravec");
                        zasobnik.push(vDravce);
            }
            polePopisku.add(radek);
            pole2D.add(pole1D);
        }
        try {
            oos.writeObject(polePopisku);
        } catch (IOException e) {
            e.printStackTrace();
        }
        while (!zasobnik.empty()){
            zasobnik.peek().start();
            zasobnik.pop();
        }
        return pole2D;
    }
}
