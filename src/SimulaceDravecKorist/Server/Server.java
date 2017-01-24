package SimulaceDravecKorist.Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by stepanmudra on 28.12.16.
 * Třída, která spustí server a čeká na připojení klientů.
 */
public class Server {
    public static void main(String[] args) {
        Server server = new Server();
        server.pustServer();
    }
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


