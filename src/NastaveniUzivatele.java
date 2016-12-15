import javax.swing.*;
import java.awt.*;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Scanner;

/**
 * Created by stepanmudra on 02.12.16.
 */
public class NastaveniUzivatele extends JPanel implements ActionListener{
    Scanner sc = new Scanner(System.in);
    int radky;
    int sloupce;
    Okno okno;
    final private String SPUST_SIMULACI = "Spustit simulaci";
    final private String VYPNOUT = "Vypnout";

    public NastaveniUzivatele(Okno okno){
        setBackground(Color.BLUE);
        setPreferredSize(new Dimension(500, 100));
        JTextField sir = new JTextField();
        JTextField vys = new JTextField();
        sir.setPreferredSize(new Dimension(170, 47));
        vys.setPreferredSize(new Dimension(170, 47));
        JPanel jPanel = new JPanel();
        jPanel.setLayout(new BoxLayout(jPanel, BoxLayout.PAGE_AXIS));
        JPanel jPanell = new JPanel(new BorderLayout());
        JPanel jPanelll = new JPanel(new BorderLayout());
        JButton s = new JButton(SPUST_SIMULACI);
        JButton v = new JButton(VYPNOUT);
        s.setPreferredSize(new Dimension(170, 47));
        v.setPreferredSize(new Dimension(170, 47));
        jPanell.add(sir, BorderLayout.CENTER);
        jPanell.add(vys, BorderLayout.WEST);
        jPanelll.add(s, BorderLayout.CENTER);
        jPanelll.add(v, BorderLayout.EAST);
        jPanel.add(jPanell);
        jPanel.add(jPanelll);
        add(jPanel);
        this.okno = okno;
    }

    public void provedNastaveni(){
        spustSimulaci(getRadky(), getSloupce());
    }
    public void velikostPole(){
        System.out.println("zadej počet řádků");
        radky = sc.nextInt();
        System.out.println("zadej počet sloupců");
        sloupce = sc.nextInt();
    }

    public int getRadky() {
        return radky;
    }

    public int getSloupce() {
        return sloupce;
    }
    public void spustSimulaci(int radky, int sloupce){
        Engine engine = new Engine();
        engine.vytvorMapuPoli(radky, sloupce);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
