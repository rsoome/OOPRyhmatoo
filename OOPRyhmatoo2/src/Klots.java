/**
 * Created by Rasmus Soome on 11/20/2016.
 */
public abstract class Klots {
    private int[][][] asendid;
    private int asend = 0;

    void muudaAsendit(int suund){
        int uusAsend = asend + suund;

        if(uusAsend != asendid.length && uusAsend != -1 ) asend = uusAsend;
        else if (uusAsend == asendid.length) asend = 0;
        else asend = asendid.length - 1;
    }

    public int[][][] getAsendid() {
        return asendid;
    }

    public void setAsendid(int[][][] asendid) {
        this.asendid = asendid;
    }

    public int getAsend() {
        return asend;
    }
}
