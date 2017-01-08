package SimulaceDravecKorist.Client;

import javax.swing.*;
import java.awt.*;

/**
 * Created by stepanmudra on 14.12.16.
 * Vytvoření okna.
 */
public class Okno extends JFrame{
    public Okno(){
        setTitle("Simulace: dravec - kořist");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setPreferredSize(new Dimension(500, 300));
        this.setResizable(true);
        this.setVisible(true);
        pack();
    }
    public void pustSe(){
        OknoNastaveni oknoNastaveni = new OknoNastaveni(this);
        this.add(oknoNastaveni);
        pack();
        setVisible(true);
    }
}
