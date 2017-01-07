package Client;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Created by stepanmudra on 14.12.16.
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
        NastaveniUzivatele nastaveniUzivatele = new NastaveniUzivatele(this);
        this.add(nastaveniUzivatele);
        pack();
        setVisible(true);
    }
}
