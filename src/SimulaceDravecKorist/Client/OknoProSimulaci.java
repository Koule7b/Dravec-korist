package SimulaceDravecKorist.Client;

import SimulaceDravecKorist.Bod;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * Created by stepanmudra on 24.01.17.
 */
public class OknoProSimulaci extends JFrame{
    private JButton spustit = new JButton("Spustit");
    private JButton pauza = new JButton("Pauza");
    private ComponentSimulace panelSimulace;
    private JPanel hlavniPanel = new JPanel();
    Komunikator komunikator;
    int pocetBoduNaVysku;
    int pocetBoduNaSirku;

    public OknoProSimulaci(Komunikator komunikator, int pocetBoduNaVysku, int pocetBoduNaSirku){

        setTitle("Simulace: dravec-kořist");
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        this.komunikator = komunikator;
        this.pocetBoduNaVysku = pocetBoduNaVysku;
        this.pocetBoduNaSirku = pocetBoduNaSirku;

        hlavniPanel.setLayout(new BorderLayout());

        this.panelSimulace = new ComponentSimulace(pocetBoduNaSirku, pocetBoduNaVysku);
        hlavniPanel.setBorder(new EmptyBorder(10, 13, 10, 10));
        panelSimulace.setMinimumSize(new Dimension(500, 500));
        panelSimulace.setPreferredSize(new Dimension(500, 500));
        hlavniPanel.add(panelSimulace, BorderLayout.CENTER);
        //JPanel panelTlacitek = new JPanel();
        //panelTlacitek.add(spustit);
        //panelTlacitek.add(pauza);
        //hlavniPanel.add(panelTlacitek, BorderLayout.PAGE_END);
        this.add(hlavniPanel);
        pack();
        setVisible(true);

        LinkedBlockingDeque<ArrayList<Bod>> blokujiciFronta = new LinkedBlockingDeque<>();
        komunikator.setFrontaStavu(blokujiciFronta);

        new Thread(() -> {
            while (true) {
                ArrayList<Bod> stav = null;
                try {
                    stav = blokujiciFronta.takeFirst();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                panelSimulace.setSeznamBodu(stav);
            }
        }
        ).start();
    }
}
