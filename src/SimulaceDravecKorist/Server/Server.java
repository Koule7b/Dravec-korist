package SimulaceDravecKorist.Server;

import SimulaceDravecKorist.Nastaveni;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

/**
 * Created by stepanmudra on 28.12.16.
 */
public class Server {
    int portNumber = 23456;
    public void pustServer(){
        try (
                ServerSocket serverSocket =
                        new ServerSocket(portNumber)
        ) {
            while(true){
                Socket clientSocket = serverSocket.accept();
                System.out.println("připojejí z "+clientSocket.getLocalAddress().getHostName());
                Client simulace = new Client(clientSocket);
                Thread v = new Thread(simulace);
                v.start();
            }
        } catch (IOException e) {
            System.out.println("Exception caught when trying to listen on port "
                    + portNumber + " or listening for a connection");
            System.out.println(e.getMessage());
        }
    }
}
class Client implements Runnable {
    Socket socket;
    private int sloupce;
    private int radky;
    ObjectOutputStream os;
    ObjectInputStream is;

    public Client(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        //SimulaceDravecKorist.Client.Okno okno = new SimulaceDravecKorist.Client.Okno();
        //okno.pustSe(socket);
        try {
            os = new ObjectOutputStream(socket.getOutputStream());
            System.out.println(socket.isClosed());
            is = new ObjectInputStream(socket.getInputStream());
            System.out.println(socket.isClosed());
        } catch (IOException e) {
            e.printStackTrace();
        }
        Nastaveni nastaveni;
        while (!socket.isClosed()) {

                try {
                    Object obj = is.readObject();
                    if (obj == null) {
                        socket.close();
                        break;
                    }
                    nastaveni = (Nastaveni) obj;
                    System.out.println(nastaveni.getVyska());
                    Engine engine = new Engine(os);
                    engine.vytvorMapuPoli(nastaveni);

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
                // TODO: 06.01.17 spustit engine s nastavením, které client poslal Serveru.
                // TODO: 06.01.17 To znamená vytvořit něco jako socketInputReader, který dostane nastavení z okna.
                // TODO: 06.01.17 Poté co obdrží nastavení je předá Enginu a ten spustí simulaci.
                // TODO: 06.01.17 Vytvořit třídu změna, která bude obsahovat souřadnice na kterých se provedla změna a jaká to byla změna (na co se dané políčko přepsalo.
                // TODO: 06.01.17 Poté SimulaceDravecKorist.Server pošle Změnu Clientovy, ten zjistí kde se změna odehrála a jaká a vykresí ji.
            }
        }


