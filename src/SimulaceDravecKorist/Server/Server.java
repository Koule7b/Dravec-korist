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
 * Třída, která spustí server a čeká na připojení klientů.
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

/**
 * Třída, která spustí klienta v serverové části.
 */
class Client implements Runnable {
    Socket socket;
    ObjectOutputStream os;
    ObjectInputStream is;

    public Client(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
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

            }
        }


