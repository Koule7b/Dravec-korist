package SimulaceDravecKorist.Client;

import SimulaceDravecKorist.Bod;
import SimulaceDravecKorist.Prikaz;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * Created by stepanmudra on 19.01.17.
 */
public class Komunikator implements Runnable{
    Socket socket;
    ObjectOutputStream outputStream;
    ObjectInputStream inputStream;
    private LinkedBlockingDeque<ArrayList<Bod>> frontaStavu;

    public Komunikator(String hostName, int portNumber){
        try {
            this.socket = new Socket(hostName, portNumber);
            outputStream = new ObjectOutputStream(socket.getOutputStream());
            inputStream = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void run() {
        while (!socket.isClosed()){
            try {
                ArrayList<Bod> seznamBodu = (ArrayList<Bod>) inputStream.readObject();
                frontaStavu.putLast(seznamBodu);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    public void odesliPrikaz(Prikaz prikaz){
        try {
            outputStream.writeObject(prikaz);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setFrontaStavu(LinkedBlockingDeque<ArrayList<Bod>> frontaStavu) {
        this.frontaStavu = frontaStavu;
    }
}
