package Client;

import Server.Engine;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

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
    JTextField dra = new JTextField();
    JTextField kor = new JTextField();
    JTextField tra = new JTextField();
    JPanel popisekSirkyVysky = new JPanel();
    JLabel sirka = new JLabel();
    JLabel vyska = new JLabel();
    JLabel dravci = new JLabel();
    JLabel koristi = new JLabel();
    JLabel trava = new JLabel();
    JPanel textovePoleSirkaVYska = new JPanel();
    JPanel poleProTlacitka = new JPanel();
    JPanel popisekDravecKorist = new JPanel();
    JPanel popisekTrava = new JPanel();
    JPanel textovePoleDravecKorist = new JPanel();
    JPanel textovePoleTrava = new JPanel();
    JPanel panelProPanely = new JPanel();
    JPanel panelProPanely2 = new JPanel();
    JButton s = new JButton(SPUST_SIMULACI);
    JButton v = new JButton(VYPNOUT);
    Socket socket;
    Scanner sc;

    public NastaveniUzivatele(Client.Okno okno, Socket socket){
        setBackground(Color.BLUE);
        this.setLayout(new BorderLayout());
        this.socket = socket;
        popisekSirkyVysky.setLayout(new BorderLayout());
        textovePoleSirkaVYska.setLayout(new BorderLayout());
        poleProTlacitka.setLayout(new BorderLayout());
        textovePoleDravecKorist.setLayout(new BorderLayout());
        textovePoleTrava.setLayout(new BorderLayout());
        panelProPanely2.setLayout(new BorderLayout());
        popisekDravecKorist.setLayout(new BorderLayout());
        popisekTrava.setLayout(new BorderLayout());
        textovePoleSirkaVYska.add(sir, BorderLayout.WEST);
        textovePoleSirkaVYska.add(vys, BorderLayout.EAST);
        textovePoleDravecKorist.add(dra, BorderLayout.WEST);
        textovePoleDravecKorist.add(kor, BorderLayout.EAST);
        textovePoleTrava.add(tra, BorderLayout.CENTER);
        poleProTlacitka.add(s, BorderLayout.WEST);
        poleProTlacitka.add(v, BorderLayout.EAST);
        sirka.setText("Zadejte šířku");
        vyska.setText("Zadejte výšku");
        dravci.setText("Zadejte počet dravců");
        koristi.setText("Zadejte počet kořistí");
        trava.setText("Zadejte počet trávy");
        popisekSirkyVysky.add(sirka, BorderLayout.WEST);
        popisekSirkyVysky.add(vyska,BorderLayout.EAST);
        popisekDravecKorist.add(dravci, BorderLayout.WEST);
        popisekDravecKorist.add(koristi, BorderLayout.EAST);
        popisekTrava.add(trava, BorderLayout.CENTER);
        panelProPanely.setLayout(new BorderLayout());
        panelProPanely.add(textovePoleDravecKorist, BorderLayout.NORTH);
        panelProPanely.add(popisekDravecKorist, BorderLayout.CENTER);
        panelProPanely.add(panelProPanely2, BorderLayout.SOUTH);
        panelProPanely2.add(textovePoleTrava, BorderLayout.NORTH);
        panelProPanely2.add(popisekTrava, BorderLayout.CENTER);
        panelProPanely2.add(poleProTlacitka, BorderLayout.SOUTH);
        s.addActionListener(this);
        v.addActionListener(this);
        this.add(textovePoleSirkaVYska, BorderLayout.NORTH);
        this.add(popisekSirkyVysky, BorderLayout.SOUTH);
        this.add(panelProPanely, BorderLayout.CENTER);
        this.okno = okno;
        try {
            sc = new Scanner(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.requestFocus();
        velikostPole();
        this.setVisible(true);
    }

    public void provedNastaveni(PrintWriter pw){
        spustSimulaci(getRadky(), getSloupce(), pw);
    }
    public void velikostPole(){
        PrintWriter pw = null;
        try {
            pw = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        pw.println("zadej počet řádků");
        radky = sc.nextInt();
        pw.println("zadej počet sloupců");
        sloupce = sc.nextInt();
        provedNastaveni(pw);
    }

    public int getRadky() {
        return radky;
    }

    public int getSloupce() {
        return sloupce;
    }
    public void spustSimulaci(int radky, int sloupce, PrintWriter pw){
        Engine engine = new Engine();
        engine.vytvorMapuPoli(radky, sloupce, pw);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String prikaz = e.getActionCommand();
        switch (prikaz){
            case VYPNOUT:
                okno.dispose();
                break;
            case SPUST_SIMULACI:
                System.out.println(sir.getText());
        }
    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        popisekSirkyVysky.setPreferredSize(new Dimension(okno.getWidth(), okno.getHeight() / 7));
        textovePoleSirkaVYska.setPreferredSize(new Dimension(okno.getWidth(), okno.getHeight() / 7));
        poleProTlacitka.setPreferredSize(new Dimension(okno.getWidth(), okno.getHeight() / 7));
        popisekDravecKorist.setPreferredSize(new Dimension(okno.getWidth(), okno.getHeight() / 7));
        popisekTrava.setPreferredSize(new Dimension(okno.getWidth(), okno.getHeight() / 7));
        panelProPanely.setPreferredSize(new Dimension(okno.getWidth(), okno.getHeight() / 7));
        panelProPanely2.setPreferredSize(new Dimension(okno.getWidth(), panelProPanely.getHeight() / 3));
        sir.setPreferredSize(new Dimension(textovePoleSirkaVYska.getWidth() / 2, textovePoleSirkaVYska.getHeight()));
        vys.setPreferredSize(new Dimension(textovePoleSirkaVYska.getWidth() / 2, textovePoleSirkaVYska.getHeight()));
        sirka.setPreferredSize(new Dimension(popisekSirkyVysky.getWidth() / 2, popisekSirkyVysky.getHeight()));
        vyska.setPreferredSize(new Dimension(popisekSirkyVysky.getWidth() / 2, popisekSirkyVysky.getHeight()));
        s.setPreferredSize(new Dimension(poleProTlacitka.getWidth() / 2, poleProTlacitka.getHeight()));
        v.setPreferredSize(new Dimension(poleProTlacitka.getWidth() / 2, poleProTlacitka.getHeight()));
        dra.setPreferredSize(new Dimension(textovePoleDravecKorist.getWidth() / 2, textovePoleDravecKorist.getHeight()));
        kor.setPreferredSize(new Dimension(textovePoleDravecKorist.getWidth() / 2, textovePoleDravecKorist.getHeight()));
        dravci.setPreferredSize(new Dimension(popisekDravecKorist.getWidth() / 2, popisekDravecKorist.getHeight()));
        koristi.setPreferredSize(new Dimension(popisekDravecKorist.getWidth() / 2, popisekDravecKorist.getHeight()));
        tra.setPreferredSize(new Dimension(textovePoleTrava.getWidth(), textovePoleTrava.getHeight()));
        trava.setPreferredSize(new Dimension(popisekTrava.getWidth(), popisekTrava.getHeight()));
        textovePoleSirkaVYska.setBackground(Color.CYAN);
        poleProTlacitka.setBackground(Color.YELLOW);
        repaint();
    }
}
