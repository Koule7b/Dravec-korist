import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by stepanmudra on 19.11.16.
 */
public class Korist extends Trava implements Runnable{
    private ArrayList<ArrayList<Misto>> list;
    private int[] pozice;
    public Korist(ArrayList<ArrayList<Misto>> list, int[] pozice){
        super(list);
        this.list = list;
        this.pozice = pozice;
    }
    @Override
    public void run() {
        while (true) {
            System.out.println(list);
            rozhledniSe(list);
            System.out.println("l");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("u");
        }
    }
    private int[] rozhledniSe(ArrayList<ArrayList<Misto>> list){
        int [] nejPozice = {0, 0};
        System.out.println(Arrays.toString(pozice));
        for (int i = ((pozice[0] - 1) < 0) ? 0 : (pozice[0] - 1); i < (((pozice[0] + 1) <= list.size())?(pozice[0]+1):(list.size()-1)); i++) {
            System.out.println(i);
            //System.out.println(list.size());
            //System.out.println(list.get(i).size());
            for (int j = ((pozice[0] - 1) < 0) ? 0 : (pozice[0] - 1); j < (((pozice[1] + 1) <= list.get(i).size())?(pozice[1]+1):(list.get(i).size()-1)); j++) {
                //System.out.println(j);
                System.out.println(list.get(i).get(j)+" ");
            }
        }
        //System.out.println(list);
        return nejPozice;
    }
}
