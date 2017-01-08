package SimulaceDravecKorist.Client;

import SimulaceDravecKorist.Zmena;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

/**
 * Created by stepanmudra on 07.01.17.
 * Třída, která zajišťuje naplnění okna simulací.
 */
public class OknoSimulace extends JPanel {
    private Okno okno;
    private ObjectInputStream ois;
    private ArrayList<ArrayList<String>> polee = new ArrayList<>();
    boolean nacteno = false;
    public OknoSimulace(Okno okno, ObjectInputStream ois){
        this.ois = ois;
        this.okno = okno;

    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        this.setPreferredSize(new Dimension(okno.getWidth(), okno.getHeight()));
        try {
            Object object = ois.readObject();
            if(!nacteno) {
                System.out.println(object);
                this.polee = (ArrayList<ArrayList<String>>) object;
                nacteno = true;
                vykresliPole(g);
                repaint();
            }else {
                if(object != null) {
                    Zmena zmena = (Zmena) object;
                    System.out.println(zmena);
                    polee.get(zmena.getY()).set(zmena.getX(), zmena.getZmenenoNa());
                    System.out.println(zmena.getY() + " " + zmena.getX() + " " + zmena.getZmenenoNa());
                    vykresliPole(g);
                    repaint();
                }else {
                    vykresliPole(g);
                    repaint();
                }
            }
        } catch (IOException e) {
            vykresliPole(g);
            repaint();
        } catch (ClassNotFoundException e) {
            vykresliPole(g);
            repaint();
        }catch (Exception e){
            vykresliPole(g);
            repaint();
        }
    }
    public void vykresliPole(Graphics g){
        int x = 0;
        int y = 0;
        for (int i = 0; i < polee.size(); i++) {
            for (int j = 0; j < polee.get(i).size(); j++) {
                switch (polee.get(i).get(j)){
                    case "dravec":
                        g.setColor(Color.RED);
                        break;
                    case "korist":
                        g.setColor(Color.BLUE);
                        break;
                    case "trava":
                        g.setColor(Color.GREEN);
                        break;
                    case "misto":
                        g.setColor(Color.GRAY);
                }
                g.fillRect(x, y, okno.getWidth()/polee.get(i).size(), okno.getHeight()/polee.size());
                x += okno.getWidth()/polee.get(i).size();
            }
            x = 0;
            y += okno.getHeight()/polee.size();
        }
    }
}
