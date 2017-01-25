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
        setTitle("Nastavení simulace.");
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
                    int procentoDravcu = Integer.valueOf(dravci.getText());
                    int procentoKoristi = Integer.valueOf(korist.getText());
                    int procentoTravy = Integer.valueOf(trava.getText());
                    int procentoPrazdna = Integer.valueOf(prazdnyMisto.getText());
                    Komunikator komunikator = new Komunikator("127.0.0.1", 23456);
                    new Thread(komunikator).start();
                    Prikaz prikaz = new Prikaz(Prikaz.Typ.NASTAVENI);
                    prikaz.pridatParametr("pocetRadku", vyskaInt);
                    prikaz.pridatParametr("pocetSloupcu", sirkaInt);
                    prikaz.pridatParametr("procentaDravcu", procentoDravcu);
                    prikaz.pridatParametr("procentaKoristi", procentoKoristi);
                    prikaz.pridatParametr("procentaTravy", procentoTravy);
                    prikaz.pridatParametr("procentaPrazdnehoMista", procentoPrazdna);
                    komunikator.odesliPrikaz(prikaz);
                    new OknoProSimulaci(komunikator, vyskaInt, sirkaInt);
                    dispose();
                } else if (tlacitko.equals("Zrušit")) {
                    System.out.println(dravci.getText());
                    System.out.println("bla");
                    System.exit(0);
                }
            }
        };
        zrušitButton.addActionListener(listener);
        spustitButton.addActionListener(listener);
    }
}
