package SimulaceDravecKorist.Client;

import SimulaceDravecKorist.Zmena;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

/**
 * Created by stepanmudra on 07.01.17.
 */
public class OknoSimulace extends JPanel {
    private Okno okno;
    private ObjectInputStream ois;
    private ArrayList<ArrayList<String>> polee = new ArrayList<>();
    private ArrayList<Zmena> zmenList = new ArrayList<>();
    boolean nacteno = false;
    public OknoSimulace(Okno okno, ObjectInputStream ois){
        this.ois = ois;
        this.okno = okno;

    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        this.setPreferredSize(new Dimension(okno.getWidth(), okno.getHeight()));
        try {
            if(!nacteno) {
                Object object = ois.readObject();
                System.out.println(object);
                polee = (ArrayList<ArrayList<String>>) object;
                nacteno = true;
                vykresliPole(g);
                repaint();
                //PrijemZmen prijemZmen = new PrijemZmen();
                //Thread vProjemZmen = new Thread(prijemZmen);
                //vProjemZmen.start();
            }else {
                Object object = ois.readObject();
                Zmena zmena = (Zmena) object;
                System.out.println(zmena);
                //polee.get(zmena.getY()).set(zmena.getX(), zmena.getZmenenoNa());
                System.out.println(zmena.getY()+" "+zmena.getX()+" "+zmena.getZmenenoNa());
                vykresliPole(g);

                repaint();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }catch (Exception e){
            e.printStackTrace();
        }

        try {
            Thread.currentThread().sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
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
    public void setZmena(Zmena zmena){
        polee.get(zmena.getY()).set(zmena.getX(), String.valueOf(zmena.getZmenenoNa()));
    }
}
