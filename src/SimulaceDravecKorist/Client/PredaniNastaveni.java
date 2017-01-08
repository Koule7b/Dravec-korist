package SimulaceDravecKorist.Client;

import SimulaceDravecKorist.Nastaveni;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Created by stepanmudra on 07.01.17.
 * Třída zajišťující předání nastavení z okna nastavení serveru.
 */
public class PredaniNastaveni {
    Socket socket;
    Nastaveni nastaveni;
    Okno okno;
    ObjectOutputStream oos;
    ObjectInputStream ois;

    public PredaniNastaveni(Nastaveni nastaveni, Okno okno) {
        this.nastaveni = nastaveni;
        this.okno = okno;
        String hostName = "127.0.0.1";
        int portNumber = 23456;
        try
        {
            this.socket = new Socket(hostName, portNumber);
            oos = new ObjectOutputStream(socket.getOutputStream());
            ois = new ObjectInputStream(socket.getInputStream());
        }catch(UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void predejObjekt(){
        try {
            oos.writeObject(nastaveni);
            otevriSimulaci();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void otevriSimulaci(){
        OknoSimulace oknoSimulace = new OknoSimulace(okno, ois);
        okno.add(oknoSimulace);
        oknoSimulace.setVisible(true);

    }
}
