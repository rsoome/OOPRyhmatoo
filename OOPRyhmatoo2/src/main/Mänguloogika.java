
package main;
import graafiline.GraafilineEsitus;
import klotsid.*;

import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Created by Rasmus Soome on 11/20/2016.
 */
public class Mänguloogika implements Runnable {

    static final int VÄLJAKUKÕRGUS = 13;
    static final int VÄLJAKULAIUS = 10;
    public static final int PAREMALE = 1;
    public static final int VASAKULE = -1;
    public static final int ALLA = 0;

    int[][] väljak = new int[VÄLJAKUKÕRGUS][VÄLJAKULAIUS];
    private Klots[] klotsid = {new IKlots(), new JKlots(), new LKlots(), new OKlots(), new SKlots(), new TKlots(), new ZKlots()};
    private Klots praeguneKlots;

    private int skoor = 0;
    private int level = 1;
    private int cleared = 0;
    Taimer mänguTaimer = new Taimer(1/level * 1000);

    private final GraafilineEsitus graafilineEsitus;

    public Mänguloogika(GraafilineEsitus graafilineEsitus) {
        this.graafilineEsitus = graafilineEsitus;
    }

    @Override
    public void run() {
        for (int i = 0; i < Mänguloogika.VÄLJAKULAIUS; i++)
            getVäljak()[Mänguloogika.VÄLJAKUKÕRGUS - 1][i] = 1;

        Taimer mänguTaimer = new Taimer((long) (1.0/level * 1000));

        setPraeguneKlots(getKlotsid()[(int) (Math.random() * 7)]);
        mänguTaimer.start();

        while (sisestaEemaldaKlots(1)) {
            while (true) {

                graafilineEsitus.renderGame(getVäljak());
                //prindiVäljakuSeis();

                if (kontrolliAlumist()) break;

                if (mänguTaimer.onAeg()) {
                    sisestaEemaldaKlots(0);
                    getPraeguneKlots().uuendaY(1);
                    sisestaEemaldaKlots(1);
                    mänguTaimer.setPeriood((long) (1.0/level * 1000));
                    mänguTaimer.start();
                }
            }
            kontrolliRidu();
            prindiVäljakuSeis();
            getPraeguneKlots().reset();
            setPraeguneKlots(getKlotsid()[(int) (Math.random() * 7)]);
            mänguTaimer.setPeriood((long) (1.0/level * 1000));
            mänguTaimer.start();
        }
        graafilineEsitus.endGame(0);
    }

    public boolean sisestaEemaldaKlots(int sisestaVõiEemalda){
        if(!mänguTaimer.getStaatus()){ mänguTaimer.start(); mänguTaimer.setStaatus(true);}
        for (int[] koordinaadiPaar : praeguneKlots.klotsiKoordinaadid()){
            //System.out.println("Y: " + koordinaadiPaar[1]);
            if (sisestaVõiEemalda == 1 && väljak[koordinaadiPaar[1]][3 + koordinaadiPaar[0]] == 1) return false;
            väljak[koordinaadiPaar[1]][3 + koordinaadiPaar[0]] = sisestaVõiEemalda;
        }
        return true;
    }

    public void liiguta(int suund){
        switch(suund){
            case -1 :
                if (4 + praeguneKlots.getX() - praeguneKlots.getKlotsiX0() > 0) praeguneKlots.uuendaX(-1);
                break;
            case 0 :
                if(!kontrolliAlumist()) praeguneKlots.uuendaY(1);
                break;
            case 1 :
                if (3 + praeguneKlots.getX() + praeguneKlots.getLaius() - praeguneKlots.getKlotsiX0()  < VÄLJAKULAIUS - 1)praeguneKlots.uuendaX(1);
                break;
            default:
                break;
        }
    }

    public void prindiVäljakuSeis(){
        for (int i = 0; i < VÄLJAKUKÕRGUS; i++){
            for (int j = 0; j < VÄLJAKULAIUS; j++){
                System.out.print(väljak[i][j]);
            }
            System.out.println();
        }
        System.out.println();
    }

    public void küsiSuunda(Scanner scanner){
        String suund = "";
        while (!suund.matches("[wasd]")) {
            suund = scanner.next();
        }
        switch (suund){
            case "w" :
                praeguneKlots.muudaAsendit(1);
                break;
            case "s" :
                praeguneKlots.muudaAsendit(-1);
                break;
            case "a" :
                if (4 + praeguneKlots.getX() - praeguneKlots.getKlotsiX0() > 0) praeguneKlots.uuendaX(-1);
                break;
            case "d" :
                if (3 + praeguneKlots.getX() + praeguneKlots.getLaius() - praeguneKlots.getKlotsiX0()  < VÄLJAKULAIUS - 1)praeguneKlots.uuendaX(1);
                break;
        }
    }

    public boolean kontrolliAlumist(){
        int[][] koordinaadid = praeguneKlots.klotsiKoordinaadid();
        int klotsiKõrgus = koordinaadid[koordinaadid.length - 1][1];
        for (int i = koordinaadid.length - 1; i >= 0 && koordinaadid[i][1] == klotsiKõrgus; i--){
            if (väljak[praeguneKlots.getY() + praeguneKlots.getKlotsiPõhi() + 1][koordinaadid[i][0] + 3] != 0){
                return true;
            }
        }
        return false;
    }

    public void kontrolliRidu(){
        int summa;
        int strike = 0;
        for (int i = väljak.length - 2; i < 0; i--){
            summa = 0;
            for (int j : väljak[i]) summa += j;
            if(summa == VÄLJAKULAIUS) strike++;
        }

        skoor += strike * Math.pow(2, strike) * 10 * (level + 1);
        cleared += strike;
        if (cleared % 20 == 0) level++;
    }

    public int[][] getVäljak() {
        return väljak;
    }

    public Klots[] getKlotsid() {
        return klotsid;
    }

    public Klots getPraeguneKlots() {
        return praeguneKlots;
    }

    public int getSkoor() {
        return skoor;
    }

    public int getLevel() {
        return level;
    }

    public void setPraeguneKlots(Klots praeguneKlots) {
        this.praeguneKlots = praeguneKlots;
    }

    public boolean uuendaVäljakuSeis(){
        if (kontrolliAlumist()){
            kontrolliRidu();
            praeguneKlots.reset();
            praeguneKlots = klotsid[(int) (Math.random() * 7)];
        }
        if (mänguTaimer.onAeg()){
            praeguneKlots.uuendaY(1);
        }
        return true;
    }

    public static void main(String[] args) throws FileNotFoundException {
        graafiline.GraafilineEsitus graafika = new GraafilineEsitus();
        Mänguloogika mänguloogika = new Mänguloogika(graafika);
        graafika.launch(GraafilineEsitus.class);
        for (int i = 0; i < Mänguloogika.VÄLJAKULAIUS; i++)
            mänguloogika.getVäljak()[Mänguloogika.VÄLJAKUKÕRGUS - 1][i] = 1;

        boolean alumineVaba = true;

        mänguloogika.setPraeguneKlots(mänguloogika.getKlotsid()[(int) (Math.random() * 7)]);

        while (mänguloogika.sisestaEemaldaKlots(1)) {
            while (true) {

                graafika.renderGame(mänguloogika.getVäljak());
                //mänguloogika.prindiVäljakuSeis();

                if (!mänguloogika.kontrolliAlumist()) {
                    System.out.println("*");break;}

                mänguloogika.sisestaEemaldaKlots(0);

                mänguloogika.getPraeguneKlots().uuendaY(1);

                mänguloogika.sisestaEemaldaKlots(1);
            }
            mänguloogika.kontrolliRidu();
            mänguloogika.getPraeguneKlots().reset();
            mänguloogika.setPraeguneKlots(mänguloogika.getKlotsid()[(int) (Math.random() * 7)]);
        }
    }

}