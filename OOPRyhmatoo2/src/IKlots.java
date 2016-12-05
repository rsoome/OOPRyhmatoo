/**
 * Created by Rasmus Soome on 11/20/2016.
 */
public class IKlots extends Klots {

    public IKlots() {
        int[][][] asendid = new int[2][4][4];

        int[][] asend1 = new int[4][4];
        int[][] asend2 = new int[4][4];

        for (int i = 0; i < 4; i++){
            asend1[1][i] = 1;
        }

        for (int[] rida : asend2){
            rida[1] = 1;
        }

        asendid[0] = asend1;
        asendid[1] = asend2;

        setAsendid(asendid);
    }

    public static void main(String[] args) {
        Klots iKlots = new IKlots();

        int[][][] asendid = iKlots.getAsendid();

        for (int i = 0; i < 2; i++){
            System.out.println();
            for (int j = 0; j < 4; j++){
                for (int k = 0; k < 4; k++){
                    System.out.print(asendid[i][j][k]);
                }
                System.out.println();
            }
        }
    }

}
