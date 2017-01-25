package SimulaceDravecKorist;

import java.util.Random;

/**
 * Created by stepanmudra on 24.01.17.
 */
public class GeneratorCisel {
    public static void main(String[] args) {
        GeneratorCisel gen = new GeneratorCisel();
        gen.vygenerujCislo(15);
    }
    public void vygenerujCislo(int cislo){
        if(cislo > 0) {
            Random random = new Random();
            System.out.println(random.nextInt(4));
            cislo--;
            vygenerujCislo(cislo);
        }
    }
}
