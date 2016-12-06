
package main;
import graafiline.GraafilineEsitus;
import klotsid.*;

import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

/**
 * Created by Rasmus Soome on 11/20/2016.
 */
public class Mänguloogika implements Runnable {

    static final int VÄLJAKUKÕRGUS = 22;
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
        Taimer renderTaimer = new Taimer(1);

        setPraeguneKlots(getKlotsid()[(int) (Math.random() * 7)]);
        mänguTaimer.start();
        renderTaimer.start();

        while (sisestaEemaldaKlots(1)) {
            while (true) {

                if (renderTaimer.onAeg()) {
                    graafilineEsitus.renderGame(getVäljak());
                    renderTaimer.start();
                }

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
            //prindiVäljakuSeis();
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
            if (sisestaVõiEemalda == 1 && väljak[koordinaadiPaar[1]][3 + koordinaadiPaar[0]] == 1) return false;
            väljak[koordinaadiPaar[1]][3 + koordinaadiPaar[0]] = sisestaVõiEemalda;
        }
        return true;
    }

    public void muudaKlotsiAsendit(int suund){
        sisestaEemaldaKlots(0);
        praeguneKlots.muudaAsendit(suund);
        sisestaEemaldaKlots(1);
    }

    public void liiguta(int suund){
        switch(suund){
            case -1 :
                if (4 + praeguneKlots.getX() - praeguneKlots.getKlotsiX0() > 0){
                    sisestaEemaldaKlots(0);
                    praeguneKlots.uuendaX(-1);
                    sisestaEemaldaKlots(1);
                }
                break;
            case 0 :
                if(!kontrolliAlumist()){
                    sisestaEemaldaKlots(0);
                    praeguneKlots.uuendaY(1);
                    sisestaEemaldaKlots(1);
                }
                break;
            case 1 :
                if (kontrolliKlotsiParemPool()){
                    sisestaEemaldaKlots(0);
                    praeguneKlots.uuendaX(1);
                    sisestaEemaldaKlots(1);
                }
                break;
            default:
                break;
        }
    }

    private boolean kontrolliKlotsiParemPool(){
        int[][] koordinaadid = praeguneKlots.klotsiKoordinaadid();
        int parempoolseimKoordinaat = 0;
        for (int[] koordinaadiPaar : koordinaadid){
            if (koordinaadiPaar[0] > parempoolseimKoordinaat) parempoolseimKoordinaat = koordinaadiPaar[0];
        }
        if (3 + parempoolseimKoordinaat > VÄLJAKULAIUS)
        return true;
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
            System.out.println("*");
            summa = 0;
            for (int j : väljak[i]) summa += j;
            if(summa == VÄLJAKULAIUS){
                strike++;
            }
            System.out.println(summa);
        }

        skoor += strike * Math.pow(2, strike) * 10 * (level + 1);
        cleared += strike;
        if (cleared % 20 == 0 && cleared != 0) level++;
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

}