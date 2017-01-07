package Client;

import Oba.Nastaveni;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintWriter;

/**
 * Created by stepanmudra on 02.12.16.
 */
public class NastaveniUzivatele extends JPanel implements ActionListener{
    int radky;
    int sloupce;
    Client.Okno okno;
    final private String SPUST_SIMULACI = "Spustit simulaci";
    final private String VYPNOUT = "Vypnout";
    JTextField sir = new JTextField();
    JTextField vys = new JTextField();

    JLabel sirka = new JLabel();
    JLabel vyska = new JLabel();

    JPanel poleLevo = new JPanel();
    JPanel polePravo = new JPanel();

    JButton s = new JButton(SPUST_SIMULACI);
    JButton v = new JButton(VYPNOUT);
    //Scanner sc;

    public NastaveniUzivatele(Client.Okno okno){
        this.setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
        poleLevo.setLayout(new BoxLayout(poleLevo, BoxLayout.PAGE_AXIS));
        polePravo.setLayout(new BoxLayout(polePravo, BoxLayout.PAGE_AXIS));
        poleLevo.add(sir);
        poleLevo.add(sirka);
        poleLevo.add(s);
        polePravo.add(vys);
        polePravo.add(vyska);
        polePravo.add(v);
        sirka.setText("Zadejte šířku");
        vyska.setText("Zadejte výšku");
        s.addActionListener(this);
        v.addActionListener(this);
        this.add(poleLevo);
        this.add(polePravo);
        this.okno = okno;
        //try {
            //sc = new Scanner(socket.getInputStream());
        //} catch (IOException e) {
            //e.printStackTrace();
        //}
        this.requestFocus();
        this.setVisible(true);
    }

    public void provedNastaveni(PrintWriter pw){
        //spustSimulaci(getRadky(), getSloupce(), pw);
    }
    public void velikostPole(){
        //pw.println("zadej počet řádků");
        //radky = sc.nextInt();
        //pw.println("zadej počet sloupců");
        //sloupce = sc.nextInt();
        //provedNastaveni(pw);
    }

    public int getRadky() {
        return radky;
    }

    public int getSloupce() {
        return sloupce;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String prikaz = e.getActionCommand();
        switch (prikaz){
            case VYPNOUT:
                okno.dispose();
                break;
            case SPUST_SIMULACI:
                int vyy = Integer.parseInt(vys.getText());
                int sii = Integer.parseInt(sir.getText());
                Oba.Nastaveni nastaveni = new Oba.Nastaveni(sii, vyy);
                ProvedNastaveni provedNastaveni = new ProvedNastaveni(nastaveni);
                provedNastaveni.predejObjekt();
                System.out.println(nastaveni);
        }
    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        poleLevo.setPreferredSize(new Dimension(this.getWidth()/2, this.getHeight()/2));
        polePravo.setPreferredSize(new Dimension(this.getWidth()/2, this.getHeight()/2));
        try {
            Thread.currentThread().sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        repaint();
    }
}
