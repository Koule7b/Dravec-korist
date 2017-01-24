package SimulaceDravecKorist.Server;

import SimulaceDravecKorist.Bod;
import SimulaceDravecKorist.Prikaz;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;

/**
 * Třída, která spustí klienta v serverové části.
 */
public class Client implements Runnable {
    Socket socket;
    ObjectOutputStream outputStream;
    ObjectInputStream inputStream;
    Simulace simulace;

    public Client(Socket socket) {
        this.socket = socket;
        try {
            outputStream = new ObjectOutputStream(socket.getOutputStream());
            inputStream = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while (!socket.isClosed()) {

                try {
                    Prikaz prikaz = (Prikaz) inputStream.readObject();
                    switch (prikaz.getTyp()){
                        case NASTAVENI:
                            spusteniSimulace(
                                    prikaz.precistParametr("pocetRadku"),
                                    prikaz.precistParametr("pocetSloupcu"),
                                    prikaz.precistParametr("procentaDravcu"),
                                    prikaz.precistParametr("procentaKoristi"),
                                    prikaz.precistParametr("procentaTravy"),
                                    prikaz.precistParametr("procentaPrazdnehoMista")
                            );
                            break;
                    }

                } catch (IOException | ClassNotFoundException e) {
                    if (e instanceof SocketException && e.getMessage().equals("Connection reset")) {
                        try {
                            socket.close();
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                    }
                    e.printStackTrace();
                }
            }

            }

    private void spusteniSimulace(int pocetRadku, int pocetSloupcu, int procentaDravcu, int procentaKoristi, int procentaTravy, int procentaPrazdnehoMista) {
        simulace = new Simulace(pocetRadku, pocetSloupcu, procentaDravcu, procentaKoristi, procentaTravy, procentaPrazdnehoMista);
        simulace.setClient(this);
        Thread vlaknoSimulace = new Thread(simulace);
        vlaknoSimulace.start();
    }
    public void odesliStav(ArrayList<Bod> stav){
        try {
            outputStream.writeObject(stav);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
