package SimulaceDravecKorist.Server;

/**
 * Created by stepanmudra on 06.11.16.
 * Třída spouštějící serverovou část.
 */
public class Spusteni {
    public static void main(String[] args) {
        Server server = new Server();
        server.pustServer();
    }
}
