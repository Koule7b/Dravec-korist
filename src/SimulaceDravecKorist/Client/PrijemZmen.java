package SimulaceDravecKorist.Client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

/**
 * Created by stepanmudra on 08.01.17.
 */
public class PrijemZmen implements Runnable {
    private Socket socket;
    private ObjectInputStream ois;
    private OknoSimulace oknoSimulace;
    @Override
    public void run() {
        while (!socket.isClosed()){
            try {
                Object object = ois.readObject();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

        }
    }
}
