package klotsid;

/**
 * Created by Rasmus Soome on 11/20/2016.
 */
public class SKlots extends Klots{
    public SKlots() {
        int[][][] asendid = new int[2][3][3];

        int[][] asend1 = new int[3][3];
        int[][] asend2 = new int[3][3];


        for (int i = 0; i < 2; i++){
            asend1[i + 1][1] = 1;
            asend2[i][1] = 1;
            asend2[i+ 1][2] = 1;
        }
        asend1[2][0] = 1;
        asend1[1][2] = 1;


        asendid[0] = asend1;
        asendid[1] = asend2;

        setAsendid(asendid);

    }

}
