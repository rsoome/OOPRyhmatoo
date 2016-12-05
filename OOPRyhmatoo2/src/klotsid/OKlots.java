package klotsid;

/**
 * Created by Rasmus Soome on 11/20/2016.
 */
public class OKlots extends Klots {
    public OKlots() {
        int[][][] asend = new int[1][3][3];
        for (int j = 0; j < 2; j++){
            for(int i = 1; i < 3 ; i++){
                asend[0][j][i] = 1;
            }
        }
        setAsendid(asend);

    }
}
