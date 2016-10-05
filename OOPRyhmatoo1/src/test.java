import java.io.File;

/**
 * Created by Rasmus Soome on 9/14/2016.
 */
public class test {
    public static void main(String[] args) {
        String path = System.getenv("Path").split(";")[0];
        System.out.println(path);

    }
}
