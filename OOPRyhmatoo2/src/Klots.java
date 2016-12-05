/**
 * Created by Rasmus Soome on 11/20/2016.
 */
public abstract class Klots {
    private int[][][] asendid;
    private int asend = 0;

    void muudaAsendit(int suund){
        asend = (asend + asendid.length + suund) % asendid.length;
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
