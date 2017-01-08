package SimulaceDravecKorist.Client;

import javax.swing.*;
import java.io.ObjectInputStream;
import java.util.ArrayList;

/**
 * Created by stepanmudra on 07.01.17.
 */
public class OknoGrafu extends JPanel{
    private Okno okno;
    private ObjectInputStream ois;
    private ArrayList<Integer> pocetMist = new ArrayList<>();
    private ArrayList<Integer> pocetTrav = new ArrayList<>();
    private ArrayList<Integer> pocetDravcu = new ArrayList<>();
    private ArrayList<Integer> pocetKoristi = new ArrayList<>();
    public OknoGrafu(Okno okno, ObjectInputStream ois){
        this.ois = ois;
        this.okno = okno;
    }
}
