package SimulaceDravecKorist.Client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintWriter;

/**
 * Created by stepanmudra on 02.12.16.
 * Třída, která je grafickým panelem.
 * V tomto panelu se nastavuje vše potřebné ke spuštění simulace tj. velikost mapy a přibližné procentuální zastoupení
 * prázdného místa, trávy, kořisti a dravců.
 */
public class OknoNastaveni extends JPanel implements ActionListener{
    int radky;
    int sloupce;
    SimulaceDravecKorist.Client.Okno okno;
    final private String SPUST_SIMULACI = "Spustit simulaci";
    final private String VYPNOUT = "Vypnout";
    JTextField sir = new JTextField();
    JTextField vys = new JTextField();
    JTextField procentoMist = new JTextField();
    JTextField procentoTrav = new JTextField();
    JTextField procentoKoristi = new JTextField();
    JTextField procentoDravcu = new JTextField();

    JLabel sirka = new JLabel();
    JLabel vyska = new JLabel();
    JLabel misto = new JLabel();
    JLabel trava = new JLabel();
    JLabel korist = new JLabel();
    JLabel dravci = new JLabel();

    JPanel poleLevo = new JPanel();
    JPanel polePravo = new JPanel();

    JButton s = new JButton(SPUST_SIMULACI);
    JButton v = new JButton(VYPNOUT);
    //Scanner sc;

    public OknoNastaveni(SimulaceDravecKorist.Client.Okno okno){
        this.setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
        poleLevo.setLayout(new BoxLayout(poleLevo, BoxLayout.PAGE_AXIS));
        polePravo.setLayout(new BoxLayout(polePravo, BoxLayout.PAGE_AXIS));
        poleLevo.add(sir);
        poleLevo.add(sirka);
        poleLevo.add(procentoMist);
        poleLevo.add(misto);
        poleLevo.add(procentoKoristi);
        poleLevo.add(korist);
        poleLevo.add(s);
        polePravo.add(vys);
        polePravo.add(vyska);
        polePravo.add(procentoTrav);
        polePravo.add(trava);
        polePravo.add(procentoDravcu);
        polePravo.add(dravci);
        polePravo.add(v);
        sirka.setText("Zadejte šířku");
        vyska.setText("Zadejte výšku");
        misto.setText("Zadejte prázdná místa v %");
        trava.setText("Zadejte obsazení trávou v %");
        korist.setText("Zadejte obsazení kořistí v %");
        dravci.setText("Zadejte obsazení dravcema v %");
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
                int mista = Integer.valueOf(procentoMist.getText());
                int travy = Integer.valueOf(procentoTrav.getText());
                int koristi = Integer.valueOf(procentoKoristi.getText());
                int dravci = Integer.valueOf(procentoDravcu.getText());
                //Nastaveni nastaveni = new Nastaveni(sii, vyy, mista, travy, koristi, dravci);
                //this.requestFocus(false);
                this.setVisible(false);
                okno.remove(this);
                //PredaniNastaveni predaniNastaveni = new PredaniNastaveni(nastaveni, okno);
                //predaniNastaveni.predejObjekt();
                //System.out.println(nastaveni);
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
