import java.util.Scanner;

/**
 * Created by stepanmudra on 02.12.16.
 */
public class NastaveniUzivatele {
    Scanner sc = new Scanner(System.in);
    int radky;
    int sloupce;
    public NastaveniUzivatele(){
    }
    public void provedNastaveni(){
        velikostPole();
        spustSimulaci(getRadky(), getSloupce());
    }
    public void velikostPole(){
        System.out.println("zadej počet řádků");
        radky = sc.nextInt();
        System.out.println("zadej počet sloupců");
        sloupce = sc.nextInt();
    }

    public int getRadky() {
        return radky;
    }

    public int getSloupce() {
        return sloupce;
    }
    public void spustSimulaci(int radky, int sloupce){
        Engine engine = new Engine();
        engine.vytvorMapuPoli(radky, sloupce);
    }
}
