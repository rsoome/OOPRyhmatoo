package main;

/**
 * Created by Rasmus Soome on 12/2/2016.
 */

public class Taimer {

    private long periood = -1;
    private long lõppAeg = -1;
    private boolean staatus = false;

    public Taimer(){};

    public Taimer(long periood) {
        this.periood = periood;
    }

    public void setPeriood(long periood){this.periood = periood;}

    public void start(){lõppAeg = System.currentTimeMillis() + periood;}

    public boolean onAeg(){
        if (lõppAeg - System.currentTimeMillis() > 0)
            return false;
        return true;
    }

    public boolean getStaatus(){return staatus;}
    public void setStaatus(boolean staatus){this.staatus = staatus;}

}
