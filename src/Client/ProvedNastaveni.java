package Client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Created by stepanmudra on 07.01.17.
 */
public class ProvedNastaveni {
    Socket socket = null;
    Oba.Nastaveni nastaveni;
    ObjectOutputStream oos;
    ObjectInputStream ois;

    public ProvedNastaveni(Oba.Nastaveni nastaveni) {
        this.nastaveni = nastaveni;
        String hostName = "127.0.0.1";
        int portNumber = 23456;
        try (
                Socket echoSocket = new Socket(hostName, portNumber);


        ) {
            this.socket = echoSocket;
            oos = new ObjectOutputStream(socket.getOutputStream());
            oos.flush();
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
            oos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
