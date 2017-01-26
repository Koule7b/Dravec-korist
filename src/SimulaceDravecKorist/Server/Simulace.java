package SimulaceDravecKorist.Server;

import SimulaceDravecKorist.Bod;
import SimulaceDravecKorist.Server.Druhy.Dravec;
import SimulaceDravecKorist.Server.Druhy.Korist;
import SimulaceDravecKorist.Server.Druhy.Prazdno;
import SimulaceDravecKorist.Server.Druhy.Trava;

import java.util.ArrayList;
import java.util.Arrays;
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
    ArrayList<Prazdno> starySeznamJedincu = new ArrayList<>();
    Prazdno[] novySeznamJedincu;
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
        novySeznamJedincu = starySeznamJedincu.stream().toArray(Prazdno[]::new);
        client.odesliStav(getSeznamBodu());

        try {
            Thread.currentThread().sleep(10000);
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
            for (int i = 0; i < starySeznamJedincu.size(); i++){
                Prazdno jedinec = starySeznamJedincu.get(i);
                rozhledniSe(jedinec, i);
                overZivotnost(jedinec, i);
            }
            starySeznamJedincu = new ArrayList<>(Arrays.asList(novySeznamJedincu));

            try {
                Thread.currentThread().sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            mrtvo = true;
            for (Prazdno o : starySeznamJedincu) {
                if (o instanceof Korist || o instanceof Dravec) {
                    mrtvo = false;
                    break;
                }

            }
            client.odesliStav(getSeznamBodu());
        }
    }

    private void rozhledniSe(Prazdno jedinec, int k) {
        int pocetNaX = starySeznamJedincu.get(starySeznamJedincu.size() - 1).getX() + 1;
        int pocetNaY = starySeznamJedincu.get(starySeznamJedincu.size() - 1).getY() + 1;
        int indexJedince = k;
        int moznyPocatecniIndex = indexJedince - pocetNaX - 1;
        int pocatecniIndex = moznyPocatecniIndex < 0 ? 0 : moznyPocatecniIndex;
        int moznyKonecnyIndex = indexJedince + pocetNaX + 1;
        int konecnyIndex = moznyKonecnyIndex >= pocetNaX * pocetNaY ? (pocetNaX * pocetNaY) - 1 : moznyKonecnyIndex;
        if(jedinec instanceof Korist) {
            Korist korist = (Korist) jedinec;
            int hlad = korist.getHlad();
            int strach = korist.getStrach();
            korist.setHlad((hlad + 1));
                for (int i = pocatecniIndex; i < konecnyIndex; i++) {
                    if (starySeznamJedincu.get(i) instanceof Dravec) {
                        korist.setStrach((strach + 1));
                    }
                }
            for (int i = pocatecniIndex; i < konecnyIndex; i++) {
                int x = starySeznamJedincu.get(i).getX();
                int y = starySeznamJedincu.get(i).getY();
                if(starySeznamJedincu.get(i) instanceof Trava && hlad >= strach){
                    novySeznamJedincu[i] = new Prazdno(x, y);
                    korist.setHlad((hlad - 1));
                    return;
                }else if(starySeznamJedincu.get(i) instanceof Prazdno){
                    int xJedince = jedinec.getX();
                    int yJedince = jedinec.getY();
                    Random random = new Random();
                    if(random.nextInt(6) % 5 == 0){
                        novySeznamJedincu[i] = new Korist(starySeznamJedincu.get(i).getX(), starySeznamJedincu.get(i).getY());
                    }else {
                        novySeznamJedincu[i] =  jedinec;
                        novySeznamJedincu[indexJedince] =  new Prazdno(xJedince, yJedince);
                        return;
                    }

                }
            }
        }else if(jedinec instanceof Dravec){
            for (int i = pocatecniIndex; i < konecnyIndex; i++) {
                if(starySeznamJedincu.get(i) instanceof Korist){
                    int x = starySeznamJedincu.get(i).getX();
                    int y = starySeznamJedincu.get(i).getY();
                    novySeznamJedincu[i] =  new Prazdno(x, y);
                    return;
                }else if(random.nextInt(5) % 3 == 0){
                    int xJedince = jedinec.getX();
                    int yJedince = jedinec.getY();
                    novySeznamJedincu[i] =  jedinec;
                    novySeznamJedincu[indexJedince] = new Prazdno(xJedince, yJedince);
                }
            }
        }else if(jedinec instanceof Trava){
            for (int i = pocatecniIndex; i < konecnyIndex; i++) {
                if(random.nextInt(5) % 3 == 0){
                    int xJedince = starySeznamJedincu.get(i).getX();
                    int yJedince = starySeznamJedincu.get(i).getY();
                    novySeznamJedincu[indexJedince] = new Trava(xJedince, yJedince);
                }
            }
        }
    }

    private void overZivotnost(Prazdno jedinec, int i) {
        if (jedinec instanceof Trava) {
            Trava trava = (Trava) jedinec;
            int dalsiCykly = trava.getPocetNasledujcichCyklu();
            if (dalsiCykly == 0) {
                novySeznamJedincu[i] = new Prazdno(jedinec.getX(), jedinec.getY());
            }
            dalsiCykly--;
            trava.setPocetNasledujcichCyklu(dalsiCykly);
        } else if (jedinec instanceof Dravec) {
            Dravec dravec = (Dravec) jedinec;
            int dalsiCykly = dravec.getPocetNasledujcichCyklu();
            if (dalsiCykly == 0) {
                novySeznamJedincu[i] = new Prazdno(jedinec.getX(), jedinec.getY());
            }
            dalsiCykly--;
            dravec.setPocetNasledujcichCyklu(dalsiCykly);
        } else if (jedinec instanceof Korist) {
            Korist korist = (Korist)jedinec;
            int dalsiCykly = korist.getPocetNasledujcichCyklu();
            if (dalsiCykly == 0) {
                novySeznamJedincu[i] = new Prazdno(jedinec.getX(), jedinec.getY());
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
                    starySeznamJedincu.add(new Korist(x, y));
                    break;
                case TRAVA:
                    starySeznamJedincu.add(new Trava(x, y));
                    break;
                case PRAZDNO:
                    starySeznamJedincu.add(new Prazdno(x, y));
                    break;
                case DRAVEC:
                    starySeznamJedincu.add(new Dravec(x, y));
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
        for (Prazdno prazdno : starySeznamJedincu) {
            seznamBodu.add(prazdno.getBod());
        }
        return seznamBodu;
    }

    public void setClient(Client client) {
        this.client = client;
    }
}
