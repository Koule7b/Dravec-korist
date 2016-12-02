import java.util.ArrayList;

/**
 * Created by stepanmudra on 19.11.16.
 */
public class Engine {
    ArrayList<ArrayList<Misto>> pole2D = new ArrayList<>();
    int[] pozice = {0, 1};


    public ArrayList<ArrayList<Misto>> vytvorMapuPoli(int radky, int sloupce) {
        for (int i = 0; i < radky; i++) {
            ArrayList<Misto> pole1D = new ArrayList<>();
            for (int j = 0; j < sloupce; j++) {
                switch (j) {
                    case 0:
                        Korist korist = new Korist(pole2D, pozice);
                        Thread t = new Thread(korist);
                        t.start();
                        pole1D.add(korist);
                        break;
                    case 1:
                        pole1D.add(new Misto());
                        break;
                    case 2:
                        pole1D.add(new Misto());
                        break;
                    case 3:
                        pole1D.add(new Misto());
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
