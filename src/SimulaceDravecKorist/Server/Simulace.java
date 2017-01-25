package SimulaceDravecKorist.Server;

import SimulaceDravecKorist.Bod;
import SimulaceDravecKorist.Server.Druhy.Dravec;
import SimulaceDravecKorist.Server.Druhy.Korist;
import SimulaceDravecKorist.Server.Druhy.Prazdno;
import SimulaceDravecKorist.Server.Druhy.Trava;

import java.util.ArrayList;
import java.util.ListIterator;
import java.util.Random;

/**
 * Created by stepanmudra on 19.01.17.
 */
public class Simulace implements Runnable {
    int pocetRadku;
    int pocetSloupcu;
    int procentaDravcu;
    int procentaKoristi;
    int procentaTravy;
    int procentaPrazdnehoMista;
    double celkovyPocetBodu;
    double pocetDravcu;
    double pocetKoristi;
    double pocetTravy;
    double pocetPrazdna;
    private Client client;
    boolean simulaceBezi = true;

    Random random = new Random();
    ArrayList<Prazdno> seznamJedincu = new ArrayList<>();
    private boolean pozastaveno = false;

    public Simulace(int pocetRadku, int pocetSloupcu, int procentaDravcu, int procentaKoristi, int procentaTravy, int procentaPrazdnehoMista) {
        this.pocetRadku = pocetRadku;
        this.pocetSloupcu = pocetSloupcu;
        this.procentaDravcu = procentaDravcu;
        this.procentaKoristi = procentaKoristi;
        this.procentaTravy = procentaTravy;
        this.procentaPrazdnehoMista = procentaPrazdnehoMista;
        this.celkovyPocetBodu = pocetRadku * pocetSloupcu;
        this.pocetDravcu = Math.floor((celkovyPocetBodu / 100) * procentaDravcu);
        this.pocetKoristi = Math.floor((celkovyPocetBodu / 100) * procentaKoristi);
        this.pocetTravy = Math.floor((celkovyPocetBodu / 100) * procentaTravy);
        this.pocetPrazdna = Math.floor((celkovyPocetBodu / 100) * procentaPrazdnehoMista);
        System.out.println((pocetDravcu + pocetTravy + pocetKoristi + pocetPrazdna));
        if ((pocetDravcu + pocetTravy + pocetKoristi + pocetPrazdna) < celkovyPocetBodu) {
            pocetPrazdna++;
        }
    }

    public void pauza() {
        pozastaveno = true;
    }

    @Override
    public void run() {
        System.out.println("Simulace se spustila");
        vytvoreniPole();
        client.odesliStav(getSeznamBodu());

        try {
            Thread.currentThread().sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        boolean mrtvo = false;
        while (simulaceBezi && !mrtvo) {
            if (pozastaveno) {
                try {
                    this.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            ListIterator<Prazdno> iterator = seznamJedincu.listIterator();
            while (iterator.hasNext()) {
                Prazdno jedinec = iterator.next();
                rozhledniSe(iterator, jedinec);
                overZivotnost(iterator, jedinec);
            }

            try {
                Thread.currentThread().sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            mrtvo = true;
            for (Prazdno o : seznamJedincu) {
                if (o instanceof Korist || o instanceof Dravec) {
                    mrtvo = false;
                    break;
                }

            }
            client.odesliStav(getSeznamBodu());
        }
    }

    private void rozhledniSe(ListIterator<Prazdno> iterator, Prazdno jedinec) {
        ListIterator<Prazdno> okoli;
        int x = jedinec.getX();
        int y = jedinec.getX();
        int pocetNaX = seznamJedincu.get(seznamJedincu.size() - 1).getX() + 1;
        int yHranice= seznamJedincu.get(seznamJedincu.size() - 1).getY();
        int[][] souradniceDravcu;
        int[][] souradniceKoristi;
        int[][] souradniceTravy;
        int indexJedince = pocetNaX * jedinec.getY() + jedinec.getY();
        System.out.println(indexJedince);
        int moznyPocatecniIndex = indexJedince - (pocetNaX + 1);
        int pocatecniIndex = moznyPocatecniIndex < 0 ? 0 : moznyPocatecniIndex;
        System.out.println(pocatecniIndex);
        //for (int i = ((x - 1) < 0) ? 0 : (x - 1); i <= (((x + 1) < xHranice)?(x + 1): xHranice); i++) {
            //for (int j = ((y - 1) < 0) ? 0 : (y - 1); i <= (((y + 1) < yHranice)?(x + 1): yHranice); j++) {
            //}
        //}
    }

    private void overZivotnost(ListIterator<Prazdno> iterator, Prazdno jedinec) {
        if (jedinec instanceof Trava) {
            Trava trava = (Trava) jedinec;
            int dalsiCykly = trava.getPocetNasledujcichCyklu();
            if (dalsiCykly == 0) {
                iterator.set(new Prazdno(jedinec.getX(), jedinec.getY()));
            }
            dalsiCykly--;
            trava.setPocetNasledujcichCyklu(dalsiCykly);
        } else if (jedinec instanceof Dravec) {
            Dravec dravec = (Dravec) jedinec;
            int dalsiCykly = dravec.getPocetNasledujcichCyklu();
            if (dalsiCykly == 0) {
                iterator.set(new Prazdno(jedinec.getX(), jedinec.getY()));
            }
            dalsiCykly--;
            dravec.setPocetNasledujcichCyklu(dalsiCykly);
        } else if (jedinec instanceof Korist) {
            Korist korist = (Korist)jedinec;
            int dalsiCykly = korist.getPocetNasledujcichCyklu();
            if (dalsiCykly == 0) {
                iterator.set(new Prazdno(jedinec.getX(), jedinec.getY()));
            }
            dalsiCykly--;
            korist.setPocetNasledujcichCyklu(dalsiCykly);
        }
    }

    public void vytvoreniPole() {

        for (int x = 0; x < pocetSloupcu; x++) {
            for (int y = 0; y < pocetRadku; y++) {
                vytvorBod(x, y);
            }
        }
    }

    private void vytvorBod(int x, int y) {
        Bod.StavBodu stav = vygenerujStavBodu();
        if (overZdaJeStavMozny(stav)) {
            switch (stav) {
                case KORIST:
                    seznamJedincu.add(new Korist(x, y));
                    break;
                case TRAVA:
                    seznamJedincu.add(new Trava(x, y));
                    break;
                case PRAZDNO:
                    seznamJedincu.add(new Prazdno(x, y));
                    break;
                case DRAVEC:
                    seznamJedincu.add(new Dravec(x, y));
                    break;
            }


        } else {
            vytvorBod(x, y);
        }
    }

    private Bod.StavBodu vygenerujStavBodu() {
        int nahoda = random.nextInt(4);
        switch (nahoda) {
            case 0:
                return Bod.StavBodu.PRAZDNO;
            case 1:
                return Bod.StavBodu.TRAVA;
            case 2:
                return Bod.StavBodu.DRAVEC;
            case 3:
                return Bod.StavBodu.KORIST;
        }
        return Bod.StavBodu.PRAZDNO;
    }

    private boolean overZdaJeStavMozny(Bod.StavBodu stav) {
        if (stav.equals(Bod.StavBodu.KORIST) && pocetKoristi > 0) {
            pocetKoristi--;

            return true;
        }
        if (stav.equals(Bod.StavBodu.TRAVA) && pocetTravy > 0) {
            pocetTravy--;
            return true;
        }
        if (stav.equals(Bod.StavBodu.DRAVEC) && pocetDravcu > 0) {
            pocetDravcu--;
            return true;
        }
        if (stav.equals(Bod.StavBodu.PRAZDNO) && pocetPrazdna > 0) {
            pocetPrazdna--;
            return true;
        }
        return false;
    }


    public void setPozastaveno(boolean pozastaveno) {
        this.pozastaveno = pozastaveno;
    }

    public void zastavSimulaci() {
        this.simulaceBezi = false;
    }

    public ArrayList<Bod> getSeznamBodu() {
        ArrayList<Bod> seznamBodu = new ArrayList<>();
        for (Prazdno prazdno : seznamJedincu) {
            seznamBodu.add(prazdno.getBod());
        }
        return seznamBodu;
    }

    public void setClient(Client client) {
        this.client = client;
    }
}
