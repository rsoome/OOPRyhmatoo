import java.sql.Time;
import java.util.Timer;

/**
 * Created by Rasmus Soome on 12/2/2016.
 */

public class Taimer {

    private long periood = -1;
    private long algusAeg = -1;
    private boolean staatus = false;

    public Taimer(){};

    public Taimer(long periood) {
        this.periood = periood;
    }

    public void setPeriood(long periood){this.periood = periood;}

    public void start(){algusAeg = System.currentTimeMillis();}

    public boolean onAeg(){
        if (algusAeg - System.currentTimeMillis() > periood)
            return false;
        algusAeg = System.currentTimeMillis();
        return true;
    }

    public boolean getStaatus(){return staatus;}
    public void setStaatus(boolean staatus){this.staatus = staatus;}

}
