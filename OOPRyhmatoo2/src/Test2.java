import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Created by Rasmus Soome on 11/28/2016.
 */
public class Test2 {

    static Mänguloogika mänguloogika = new Mänguloogika();

    public static void main(String[] args) throws FileNotFoundException {
        //System.setOut(new PrintStream(new BufferedOutputStream(new FileOutputStream("output.txt"))));
        for (int i = 0; i < Mänguloogika.VÄLJAKULAIUS; i++) mänguloogika.getVäljak()[Mänguloogika.VÄLJAKUKÕRGUS - 1][i] = 1;

        Scanner scanner = new Scanner(System.in);

        boolean alumineVaba = true;

        mänguloogika.setPraeguneKlots(mänguloogika.getKlotsid()[(int) (Math.random() * 7)]);

        while (mänguloogika.sisestaEemaldaKlots(1)) {
            while (true) {

                mänguloogika.prindiVäljakuSeis();

                if(!mänguloogika.kontrolliAlumist()) break;

                mänguloogika.sisestaEemaldaKlots(0);

                mänguloogika.küsiSuunda(scanner);

                mänguloogika.getPraeguneKlots().uuendaY(1);

                mänguloogika.sisestaEemaldaKlots(1);
            }
            System.out.println("klotsid.Klots paigas");
            mänguloogika.prindiVäljakuSeis();
            mänguloogika.kontrolliRidu();
            mänguloogika.prindiVäljakuSeis();
            mänguloogika.getPraeguneKlots().reset();
            mänguloogika.setPraeguneKlots(mänguloogika.getKlotsid()[(int) (Math.random() * 7)]);
        }

    }
}
