package SimulaceDravecKorist;

import java.io.Serializable;
import java.util.HashMap;

/**
 * Created by stepanmudra on 19.01.17.
 */
public class Prikaz implements Serializable{
    Typ typ;
    HashMap<String, Object> parametry = new HashMap<>();
    public Prikaz(Typ typ) {
        this.typ = typ;
    }
    public void pridatParametr(String klic, Object hodnota){
        parametry.put(klic, hodnota);
    }
    public <T> T precistParametr(String klic){
        return (T) parametry.get(klic);
    }

    public Typ getTyp() {
        return typ;
    }

    public enum Typ{
        PAUZA, CONTINUE, NASTAVENI
 }
}
