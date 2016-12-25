package Logika;

import javax.swing.*;
import java.awt.*;

/**
 * Created by stepanmudra on 14.12.16.
 */
public class Okno extends JFrame{
    public Okno(){
        /**
        setTitle("Simulace: dravec - kořist");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setPreferredSize(new Dimension(500, 300));
        this.setResizable(true);
        this.setVisible(true);
         */
    }
    public void pustSe(){
        NastaveniUzivatele nastaveniUzivatele = new NastaveniUzivatele(this);
        add(nastaveniUzivatele);
    }
}
