/**
 * Created by Rasmus Soome on 11/20/2016.
 */
public class Mänguloogika {
    static int[][] väljak = new int[22][10];

    public static void main(String[] args) {
        for (int[] rida : väljak){
            for (int pos : rida){
                System.out.print(pos);
            }
            System.out.println();
        }
    }
}