package Server;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;

/**
 * Created by stepanmudra on 19.11.16.
 */
public class Engine {
    ArrayList<ArrayList<Misto>> pole2D = new ArrayList<>();
    Random ran = new Random();

    /**
     * metoda vytvářející dvourozměrné pole náhodně rozmístěných dravců, míst, kořistí a trávy.
     * @param radky
     * @param sloupce
     * @return
     */
    public ArrayList<ArrayList<Misto>> vytvorMapuPoli(int radky, int sloupce, PrintWriter pw) {
        Stack<Thread> zasobnik = new Stack<>();
        for (int i = 0; i < radky; i++) {
            ArrayList<Misto> pole1D = new ArrayList<>();
            for (int j = 0; j < sloupce; j++) {
                int[] pozice = new int[2];
                switch ((ran.nextInt(4))) {
                //switch (j){
                    case 0:
                        pozice[0] = i;
                        pozice[1] = j;
                        Korist korist = new Korist(pole2D, pozice, pw);
                        Thread vk = new Thread(korist);
                        pole1D.add(korist);
                        zasobnik.push(vk);
                        break;
                    case 1:
                        pozice[0] = i;
                        pozice[1] = j;
                        Trava trava = new Trava(pole2D, pozice, pw);
                        Thread vt = new Thread(trava);
                        pole1D.add(trava);
                        zasobnik.push(vt);
                        break;
                    case 2:
                        pozice[0] = i;
                        pozice[1] = j;
                        Dravec dravec = new Dravec(pole2D, pozice, pw);
                        Thread vd = new Thread(dravec);
                        pole1D.add(dravec);
                        zasobnik.push(vd);
                        break;
                    case 3:
                        pozice[0] = i;
                        pozice[1] = j;
                        Korist k = new Korist(pole2D, pozice, pw);
                        Thread vkk = new Thread(k);
                        pole1D.add(k);
                        zasobnik.push(vkk);
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
            while (!zasobnik.empty()){
                zasobnik.peek().start();
                zasobnik.pop();
            }
        }
        return pole2D;
    }
}
