package klotsid;

/**
 * Created by Rasmus Soome on 11/20/2016.
 */
public abstract class Klots {

    private int[][][] asendid;
    private int asend = 0;

    private int praeguneX = 0;
    private int praeguneY = 0;

    private int laius;
    private int kõrgus;

    private int klotsiParemPool;
    private int klotsiVasakPool;
    private int klotsiPõhi;
    private int absoluutneKõrgus;
    private int absoluutneLaius;

    private int klotsiX0;

    public void muudaAsendit(int suund){
        asend = (asend + asendid.length + suund) % asendid.length;

        leiaAsendiKõrgusJaLaius();

        klotsiX0 = (int) (Math.round(laius/2.0) - 1);
        klotsiParemPool = laius - 1 - klotsiX0;
        klotsiVasakPool = 0 - klotsiX0;
        absoluutneKõrgus = asendid.length;
        absoluutneLaius = asendid[0].length;
    }

    public void setAsendid(int[][][] asendid) {

        this.asendid = asendid;
        leiaAsendiKõrgusJaLaius();

        klotsiX0 = (int) (Math.round(laius/2.0) - 1);

        absoluutneKõrgus = asendid.length;
        absoluutneLaius = asendid[0].length;

    }

    public int[][] getAsend(){
        return asendid[asend];
    }

    public int getLaius() {
        return laius;
    }

    public int getKõrgus() {
        return kõrgus;
    }

    public int getKlotsiParemPool() {
        return klotsiParemPool;
    }

    public int getKlotsiVasakPool() {
        return klotsiVasakPool;
    }

    public int getKlotsiPõhi() {
        return klotsiPõhi;
    }

    public int getAbsoluutneKõrgus() {
        return absoluutneKõrgus;
    }

    public int getAbsoluutneLaius() {
        return absoluutneLaius;
    }

    public int getKlotsiX0() {
        return klotsiX0;
    }

    public void reset(){
        asend = 0;
        praeguneX = 0;
        praeguneY = 0;
        leiaAsendiKõrgusJaLaius();
    }

    public int[][] klotsiKoordinaadid(){
        int[][] koordinaadid = new int[4][2];
        int[][] praeguneklots = getAsend();
        int pointer = 0;
        for (int i = 0; i < praeguneklots.length; i++){
            for (int j = 0; j < praeguneklots[0].length; j++){
                if (praeguneklots[i][j] == 1){
                    koordinaadid[pointer][0] = j + praeguneX;
                    koordinaadid[pointer][1] = i + praeguneY;
                    pointer++;
                }
            }
        }
        return koordinaadid;
    }

    @Override
    public String toString() {
        String asendidStringina = "";
        asendidStringina += "Laius: " + laius + "\n";
        asendidStringina += "Kõrgus: " + kõrgus + "\n";
        asendidStringina += "X: " + praeguneX + "\n";
        asendidStringina += "Y: " + praeguneY + "\n";
        for (int[][] asend : asendid) {
            asendidStringina += "\n";
            for (int[] rida : asend) {
                for (int pos : rida) {
                    asendidStringina += pos;
                }
                asendidStringina += "\n";
            }
        }
        return asendidStringina;
    }

    public int getX() {
        return praeguneX;
    }

    public void uuendaX(int suund) {
        praeguneX += suund;
    }

    public int getY() {
        return praeguneY;
    }

    public void uuendaY(int suund) {
        praeguneY += suund;
    }

    private void leiaAsendiKõrgusJaLaius(){

        int kõrgus = 0;
        int laius = 0;
        int klotsiPõhi = 0;


        int[][] praeguneAsend = asendid[asend];
        int[] laiuseKontrollBitid = new int[praeguneAsend.length];
        int[] kõrguseKontrollBitid = new int[praeguneAsend.length];
        for (int i = 0; i < praeguneAsend.length; i++){
            for (int j = 0; j < praeguneAsend[0].length; j++){
                if (praeguneAsend[i][j] == 1){
                    if (laiuseKontrollBitid[j] == 0){
                        laius++;
                        laiuseKontrollBitid[j] = 1;

                    }
                    if (kõrguseKontrollBitid[i] == 0){
                        kõrgus++;
                        kõrguseKontrollBitid[i] = 1;
                    }
                    if(i > klotsiPõhi) klotsiPõhi = i;
                }
            }
        }

        this.laius = laius;
        this.kõrgus = kõrgus;
        this.klotsiPõhi = klotsiPõhi;

    }


}
