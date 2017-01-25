package SimulaceDravecKorist.Server.Druhy;

/**
 * Created by stepanmudra on 25.01.17.
 */
public abstract class Zvire extends Prazdno{

    public Zvire(int x, int y) {
        super(x, y);
    }
    public void rozmnozSe(int x, int y){
        /*
        for (Bod bod : seznamBodu) {
            if( bod.getX() == x && bod.getY() == y){
                bod.setStav(Bod.StavBodu.TRAVA);
            }
        }
        */
    }
    public void snez(int x, int y){
        /*
        for (Bod bod : seznamBodu) {
            if(bod.getX() == x && bod.getY() == y){
                bod.setStav(Bod.StavBodu.PRAZDNO);
            }
        }
        */
    }
    public void pohniSe(int xPred, int yPred, int xPo, int yPo){
        /*
        for (Bod bod : seznamBodu) {
            if(bod.getX() == xPred && bod.getY() == yPred){
                bod.setStav(Bod.StavBodu.PRAZDNO);
            }
            if(bod.getX() == xPo && bod.getY() == yPo){
                bod.setStav(Bod.StavBodu.KORIST);
            }
        }
        */
    }
}
