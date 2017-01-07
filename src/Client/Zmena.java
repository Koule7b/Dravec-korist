package Client;

/**
 * Created by stepanmudra on 06.01.17.
 */
public class Zmena {
    private int x;
    private int y;
    private Object zmenenoNa;

    public Zmena(int x, int y, Object zmenenoNa) {
        this.x = x;
        this.y = y;
        this.zmenenoNa = zmenenoNa;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Object getZmenenoNa() {
        return zmenenoNa;
    }
}
