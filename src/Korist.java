import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by stepanmudra on 19.11.16.
 */
public class Korist extends Trava implements Runnable{
    private ArrayList<ArrayList<Misto>> list;
    private int[] pozice;
    private final Lock lock = new ReentrantLock();
    public Korist(ArrayList<ArrayList<Misto>> list, int[] pozice){
        super(list);
        this.list = list;
        this.pozice = pozice;
    }
    @Override
    public void run() {
        while (true) {
            synchronized (lock) {
                rozhledniSe(list);
                //System.out.println(list);
            }
            //System.out.println("l");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //System.out.println("u");
        }
    }
    private int[] rozhledniSe(ArrayList<ArrayList<Misto>> list){
        int [] nejPozice = new int[2];
        //System.out.println(Arrays.toString(pozice));
        for (int i = ((pozice[0] - 1) < 0) ? 0 : (pozice[0] - 1); i <= (((pozice[0] + 1) < list.size())?(pozice[0]+1):(list.size()-1)); i++) {
            //System.out.println(i);
            //System.out.println(list.size());
            //System.out.println(list.get(i).size());
            for (int j = ((pozice[0] - 1) < 0) ? 0 : (pozice[0] - 1); j <= (((pozice[1] + 1) < list.get(i).size())?(pozice[1]+1):(list.get(i).size()-1)); j++) {
                //System.out.println(Thread.currentThread().getId()+" "+list.get(i).get(j)+" "+j+" "+ Arrays.toString(pozice)+" "+(pozice[1] + 1));
                //System.out.println(j);
                System.out.println( Thread.currentThread().getId()+" "+i+" "+j+" "+list.get(i).get(j));
            }
        }
        //System.out.println(list);
        return nejPozice;
    }
}
