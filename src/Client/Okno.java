package Client;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Created by stepanmudra on 14.12.16.
 */
public class Okno extends JFrame{
    public Okno(){
        setTitle("Simulace: dravec - kořist");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setPreferredSize(new Dimension(500, 300));
        this.setResizable(true);
        this.setVisible(true);
        pack();
    }
    public void pustSe(Socket socket){
        NastaveniUzivatele nastaveniUzivatele = new NastaveniUzivatele(this, socket);
        this.add(nastaveniUzivatele);
    }
    public void pripojSeKServeru(){
        String hostName = "127.0.0.1";
        int portNumber = 23456;
        try (
                Socket echoSocket = new Socket(hostName, portNumber);
                PrintWriter out =
                        new PrintWriter(echoSocket.getOutputStream(), true);
                ObjectInputStream in =
                        new ObjectInputStream(echoSocket.getInputStream());
        ) {
            pustSe(echoSocket);
        }catch(UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
