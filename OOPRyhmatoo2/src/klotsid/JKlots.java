package klotsid;

/**
 * Created by Rasmus Soome on 11/20/2016.
 */
public class JKlots extends Klots {
    public JKlots() {
        int[][][] asendid = new int[4][3][3];

        int[][] asend1 = new int[3][3];
        int[][] asend2 = new int[3][3];
        int[][] asend3 = new int[3][3];
        int[][] asend4 = new int[3][3];

        for (int i = 0; i < 3; i++){
            asend1[1][i] = 1;
            asend3[1][i] = 1;
        }
        asend1[0][0] = 1;
        asend3[2][2] = 1;

        for (int i = 0; i < 3; i++){
            asend4[i][1] = 1;
            asend2[i][1] = 1;
        }
        asend4[2][0] = 1;
        asend2[0][2] = 1;


        asendid[0] = asend1;
        asendid[1] = asend2;
        asendid[2] = asend3;
        asendid[3] = asend4;

        setAsendid(asendid);

    }

}
