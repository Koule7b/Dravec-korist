package SimulaceDravecKorist.Server;

import SimulaceDravecKorist.Bod;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by stepanmudra on 19.01.17.
 */
public class Simulace implements Runnable{
    int pocetRadku;
    int pocetSloupcu;
    int procentaDravcu;
    int procentaKoristi;
    int procentaTravy;
    int procentaPrazdnehoMista;
    private Client client;

    public Simulace(int pocetRadku, int pocetSloupcu, int procentaDravcu, int procentaKoristi, int procentaTravy, int procentaPrazdnehoMista) {
        this.pocetRadku = pocetRadku;
        this.pocetSloupcu = pocetSloupcu;
        this.procentaDravcu = procentaDravcu;
        this.procentaKoristi = procentaKoristi;
        this.procentaTravy = procentaTravy;
        this.procentaPrazdnehoMista = procentaPrazdnehoMista;
    }

    @Override
    public void run() {
        System.out.println("Simulace se spustila");
        client.odesliStav(test());
    }
    public ArrayList<Bod> test(){
        ArrayList<Bod> seznamBodu = new ArrayList<>();
        Random random = new Random();
        for (int x = 0; x < pocetSloupcu; x++) {
            for (int y = 0; y < pocetRadku; y++) {
                Bod.StavBodu stav;
                switch (random.nextInt(3) + 1){
                    case 1:
                        stav = Bod.StavBodu.PRAZDNO;
                        break;
                    case 2:
                        stav = Bod.StavBodu.TRAVA;
                        break;
                    case 3:
                        stav = Bod.StavBodu.DRAVEC;
                        break;
                    case 4:
                        stav = Bod.StavBodu.KORIST;
                        break;
                    default:
                        stav = Bod.StavBodu.TRAVA;
                }
                seznamBodu.add(new Bod(x, y, stav));
            }
        }
        return seznamBodu;
    }

    public void setClient(Client client) {
        this.client = client;
    }
}
