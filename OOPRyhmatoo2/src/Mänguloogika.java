import klotsid.*;

import java.util.Scanner;

import klotsid.Klots;

/**
 * Created by Rasmus Soome on 11/20/2016.
 */
public class Mänguloogika {

    static final int VÄLJAKUKÕRGUS = 13;
    static final int VÄLJAKULAIUS = 10;

    int[][] väljak = new int[VÄLJAKUKÕRGUS][VÄLJAKULAIUS];
    private Klots[] klotsid = {new IKlots(), new JKlots(), new LKlots(), new OKlots(), new SKlots(), new TKlots(), new ZKlots()};
    private Klots praeguneKlots;

    private int skoor = 0;
    private int level = 1;

    public boolean sisestaEemaldaKlots(int sisestaVõiEemalda){
        for (int[] koordinaadiPaar : praeguneKlots.klotsiKoordinaadid()){
            //System.out.println("Y: " + koordinaadiPaar[1]);
            if (sisestaVõiEemalda == 1 && väljak[koordinaadiPaar[1]][3 + koordinaadiPaar[0]] == 1) return false;
            väljak[koordinaadiPaar[1]][3 + koordinaadiPaar[0]] = sisestaVõiEemalda;
        }
        return true;
    }

    private void liiguta(int suund){

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
        //System.out.println("*");
        int[][] koordinaadid = praeguneKlots.klotsiKoordinaadid();
        int klotsiKõrgus = koordinaadid[koordinaadid.length - 1][1];
        //System.out.println("Koordinaatide arv: " + koordinaadid.length);
        for (int i = koordinaadid.length - 1; i >= 0 && koordinaadid[i][1] == klotsiKõrgus; i--){
            //System.out.println("Kontrollin: " + (koordinaadid[i][0] + 3)+ "," + (praeguneKlots.getY() + praeguneKlots.getKlotsiPõhi() + 1));
            //System.out.println("Y : " + praeguneKlots.getY() + " Klotsipõhi: " + praeguneKlots.getKlotsiPõhi());
            //System.out.println(väljak [praeguneKlots.getY() + praeguneKlots.getKlotsiPõhi() + 1][koordinaadid[i][0] + 3]);
            if (väljak[praeguneKlots.getY() + praeguneKlots.getKlotsiPõhi() + 1][koordinaadid[i][0] + 3] != 0){
                //System.out.println("alumine hõivatud");
                return false;
            }
        }
        return true;
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