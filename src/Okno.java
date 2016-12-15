import javax.swing.*;

/**
 * Created by stepanmudra on 14.12.16.
 */
public class Okno extends JFrame{
    public Okno(){
        setTitle("Simulace: dravec - kořist");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(true);
        this.setVisible(true);
    }
    public void pustSe(){
        NastaveniUzivatele nastaveniUzivatele = new NastaveniUzivatele(this);
        add(nastaveniUzivatele);
        pack();
        this.setVisible(true);
    }
}
