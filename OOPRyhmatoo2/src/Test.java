import klotsid.*;

/**
 * Created by Rasmus Soome on 11/23/2016.
 */
public class Test {
    public static void main(String[] args) {

        Klots[] klotsid = {new IKlots(), new JKlots(), new LKlots(), new OKlots(), new SKlots(), new TKlots(), new ZKlots()};

        for (Klots klots : klotsid){

            System.out.println(klots.getClass().getSimpleName());
            System.out.println(klots);
        }
    }
}
