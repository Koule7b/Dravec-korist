package SimulaceDravecKorist.Client;

import SimulaceDravecKorist.Prikaz;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by stepanmudra on 23.01.17.
 */
public class Nastaveni extends JFrame {
    private JButton spustitButton;
    private JButton zrušitButton;
    private JTextField sirka;
    private JTextField vyska;
    private JTextField dravci;
    private JTextField korist;
    private JTextField trava;
    private JTextField prazdnyMisto;
    private JPanel jPanel;

    public Nastaveni() {
        super();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setContentPane(jPanel);

        pack();
        setVisible(true);
        ActionListener listener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String tlacitko = e.getActionCommand();
                if (tlacitko.equals("Spustit")) {
                    System.out.println("heh");
                    int vyskaInt = Integer.valueOf(vyska.getText());
                    int sirkaInt = Integer.valueOf(sirka.getText());
                    // TODO: 24.01.17 dodelat
                    Komunikator komunikator = new Komunikator("127.0.0.1", 23456);
                    new Thread(komunikator).start();
                    Prikaz prikaz = new Prikaz(Prikaz.Typ.NASTAVENI);
                    prikaz.pridatParametr("pocetRadku", vyskaInt);
                    prikaz.pridatParametr("pocetSloupcu", sirkaInt);
                    prikaz.pridatParametr("procentaDravcu", 25);
                    prikaz.pridatParametr("procentaKoristi", 25);
                    prikaz.pridatParametr("procentaTravy", 25);
                    prikaz.pridatParametr("procentaPrazdnehoMista", 25);
                    komunikator.odesliPrikaz(prikaz);
                    OknoProSimulaci oknoSimulace = new OknoProSimulaci(komunikator, vyskaInt, sirkaInt);
                    dispose();
                } else if (tlacitko.equals("Zrušit")) {
                    System.out.println(dravci.getText());
                    System.out.println("bla");
                    dispose();
                }
            }
        };
        zrušitButton.addActionListener(listener);
        spustitButton.addActionListener(listener);
    }
}
