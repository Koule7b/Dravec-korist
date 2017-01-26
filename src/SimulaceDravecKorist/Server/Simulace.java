package SimulaceDravecKorist.Server;

import SimulaceDravecKorist.Bod;
import SimulaceDravecKorist.Server.Druhy.Dravec;
import SimulaceDravecKorist.Server.Druhy.Korist;
import SimulaceDravecKorist.Server.Druhy.Prazdno;
import SimulaceDravecKorist.Server.Druhy.Trava;

import java.util.ArrayList;
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
    Prazdno[][] starySeznamJedincu;

    Prazdno[][] novySeznamJedincu;
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

        novySeznamJedincu = starySeznamJedincu;
        boolean mrtvo = false;
        while (simulaceBezi && !mrtvo) {
            if (pozastaveno) {
                try {
                    this.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            for (int x = 0; x < starySeznamJedincu.length; x++) {
                for (int y = 0; y < starySeznamJedincu[x].length; y++) {
                    try {
                        Prazdno jedinec = starySeznamJedincu[x][y];
                        rozhledniSe(jedinec, x, y);
                        overZivotnost(jedinec, x, y);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            }
            starySeznamJedincu = novySeznamJedincu;

            try {
                Thread.currentThread().sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            mrtvo = true;
            for (int x = 0; x < starySeznamJedincu.length; x++) {
                for (int y = 0; y < starySeznamJedincu[x].length; y++) {
                    Prazdno jedinec = starySeznamJedincu[x][y];
                    if (jedinec instanceof Korist || jedinec instanceof Dravec) {
                        mrtvo = false;
                        break;
                    }
                }
            }
            client.odesliStav(getSeznamBodu());
        }
    }

    private void rozhledniSe(Prazdno jedinec, int x, int y) {
        ArrayList<Prazdno> sousedi = new ArrayList<>();
        if (x >= 1) {
            sousedi.add(starySeznamJedincu[x - 1][y]);

            if (y >= 1) {
                sousedi.add(starySeznamJedincu[x - 1][y - 1]);
            }
            if (y < pocetRadku - 1) {
                sousedi.add(starySeznamJedincu[x - 1][y + 1]);
            }
        }

        if (y >= 1) {
            sousedi.add(starySeznamJedincu[x][y - 1]);
        }
        if (y < pocetRadku - 1) {
            sousedi.add(starySeznamJedincu[x][y + 1]);
        }

        if (x < pocetSloupcu - 1) {
            if (y >= 1) {
                sousedi.add(starySeznamJedincu[x + 1][y - 1]);
            }
            sousedi.add(starySeznamJedincu[x + 1][y]);
            if (y < pocetRadku - 1) {
                sousedi.add(starySeznamJedincu[x + 1][y + 1]);
            }
        }

        int xJedince = jedinec.getX();
        int yJedince = jedinec.getY();

        if (jedinec instanceof Korist) {
            Korist korist = (Korist) jedinec;
            int hlad = korist.getHlad();
            int strach = korist.getStrach();
            korist.setHlad((hlad + 1));
            for (Prazdno soused : sousedi) {
                if (soused instanceof Dravec) {
                    korist.setStrach((strach + 1));
                }
            }
            for (Prazdno soused : sousedi) {
                if (soused instanceof Trava && hlad >= strach) {
                    novySeznamJedincu[soused.getX()][soused.getY()] = new Prazdno(soused.getX(), soused.getY());
                    korist.setHlad((hlad - 1));
                    return;
                } else if (!(soused instanceof Korist) && !(soused instanceof Dravec) && !(soused instanceof Trava)) {
                    if (random.nextInt(6) % 5 == 0) {
                        // rozmnožení
                        novySeznamJedincu[soused.getX()][soused.getY()] = new Korist(soused.getX(), soused.getY());
                        return;
                    } else if (random.nextInt(6) % 5 == 0) {
                        // přesun na sousední pole
                        korist.presun(soused.getX(), soused.getY());
                        novySeznamJedincu[soused.getX()][soused.getY()] = korist;
                        novySeznamJedincu[xJedince][yJedince] = new Prazdno(xJedince, yJedince);
                        return;
                    }

                }
            }
        } else if (jedinec instanceof Dravec) {
            for (Prazdno soused : sousedi) {
                if (soused instanceof Korist) {
                    if (random.nextInt(5) % 3 == 0) {
                        novySeznamJedincu[soused.getX()][soused.getY()] = new Prazdno(soused.getX(), soused.getY());
                        return;
                    }
                } else if (!(soused instanceof Trava) && !(soused instanceof  Dravec) && random.nextInt(5) % 3 == 0) {

                    jedinec.presun(soused.getX(), soused.getY());
                    novySeznamJedincu[soused.getX()][soused.getY()] = jedinec;
                    novySeznamJedincu[xJedince][yJedince] = new Prazdno(xJedince, yJedince);
                    return;
                }
            }
        } else if (jedinec instanceof Trava) {
            for (Prazdno soused : sousedi) {
                if (!(soused instanceof Dravec) && !(soused instanceof Trava) && !(soused instanceof  Korist) && random.nextInt(5) % 3 == 0) {
                    novySeznamJedincu[soused.getX()][soused.getY()] = new Trava(soused.getX(), soused.getY());
                    return;
                }
            }
        }
    }

    private void overZivotnost(Prazdno jedinec, int x, int y) {
        int xJedince = jedinec.getX();
        int yJedince = jedinec.getY();

        if (jedinec instanceof Trava) {
            Trava trava = (Trava) jedinec;
            int dalsiCykly = trava.getPocetNasledujcichCyklu();
            if (dalsiCykly == 0) {
                novySeznamJedincu[xJedince][yJedince] = new Prazdno(xJedince, yJedince);
            }
            dalsiCykly--;
            trava.setPocetNasledujcichCyklu(dalsiCykly);
        } else if (jedinec instanceof Dravec) {
            Dravec dravec = (Dravec) jedinec;
            int dalsiCykly = dravec.getPocetNasledujcichCyklu();
            if (dalsiCykly == 0) {
                novySeznamJedincu[xJedince][yJedince] = new Prazdno(xJedince, yJedince);
            }
            dalsiCykly--;
            dravec.setPocetNasledujcichCyklu(dalsiCykly);
        } else if (jedinec instanceof Korist) {
            Korist korist = (Korist) jedinec;
            int dalsiCykly = korist.getPocetNasledujcichCyklu();
            if (dalsiCykly == 0 || korist.getHlad() >= 10) {
                novySeznamJedincu[xJedince][yJedince] = new Prazdno(xJedince, yJedince);
            }
            dalsiCykly--;
            korist.setPocetNasledujcichCyklu(dalsiCykly);
        }
    }

    public void vytvoreniPole() {
        starySeznamJedincu = new Prazdno[pocetSloupcu][pocetRadku];
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
                    starySeznamJedincu[x][y] = new Korist(x, y);
                    break;
                case TRAVA:
                    starySeznamJedincu[x][y] = new Trava(x, y);
                    break;
                case PRAZDNO:
                    starySeznamJedincu[x][y] = new Prazdno(x, y);
                    break;
                case DRAVEC:
                    starySeznamJedincu[x][y] = new Dravec(x, y);
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
        for (int x = 0; x < starySeznamJedincu.length; x++) {
            for (int y = 0; y < starySeznamJedincu[x].length; y++) {
                seznamBodu.add(starySeznamJedincu[x][y].getBod());
            }
        }
        return seznamBodu;
    }

    public void setClient(Client client) {
        this.client = client;
    }
}
