package SimulaceDravecKorist.Client;

import SimulaceDravecKorist.Bod;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * Created by stepanmudra on 07.01.17.
 * Třída, která zajišťuje naplnění okna simulací.
 */
public class ComponentSimulace extends JPanel {
    private ArrayList<Bod> seznamBodu = new ArrayList<>();
    int pocetBoduNaSirku;
    int pocetBoduNaVysku;

    public ComponentSimulace(int pocetBoduNaSirku, int pocetBoduNaVysku) {
        this.pocetBoduNaSirku = pocetBoduNaSirku;
        this.pocetBoduNaVysku = pocetBoduNaVysku;
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        vykresliPole(g);
        repaint();

    }
    public void vykresliPole(Graphics g){
        int sirkaBodu = this.getWidth()/pocetBoduNaSirku;
        int vyskaBodu = this.getHeight()/pocetBoduNaVysku;
        for (Bod bod : seznamBodu) {
            switch (bod.getStav()){
                case TRAVA:
                    g.setColor(Color.green);
                    break;
                case  PRAZDNO:
                    g.setColor(Color.gray);
                    break;
                case KORIST:
                    g.setColor(Color.blue);
                    break;
                case DRAVEC:
                    g.setColor(Color.red);
                    break;
            }
            g.fillRect(bod.getX() * sirkaBodu, bod.getY() * vyskaBodu, sirkaBodu, vyskaBodu);
        }
    }
    public void setSeznamBodu(ArrayList<Bod> seznamBodu) {
        this.seznamBodu = seznamBodu;
        repaint();
    }
}
