package highscore;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Kärt Poots on 6.12.2016.
 */
public class HighscoreRepository {

    private static final String FILE_NAME = "highscores.dat";

    private final List<Highscore> highscores;

    @SuppressWarnings("unchecked")
    public HighscoreRepository() {
        List<Highscore> highscores = null;
        try {
            highscores = (List<Highscore>) new ObjectInputStream(new FileInputStream(FILE_NAME)).readObject();
        } catch (Exception e) {
            //Midagi läks katki, teeme uue tühja TreeSeti
            //Treeset annab elemente kasvavas järjekorras, vaja on kahanevas, sellest miinus märk lambda alguses
            highscores = new ArrayList<>();
        } finally {
            this.highscores = highscores;
        }
    }

    public void add(Highscore highscore) {
        highscores.add(highscore);
        highscores.sort((o1, o2) -> -Integer.compare(o1.getScore(), o2.getScore()));
        try {
            new ObjectOutputStream(new FileOutputStream(FILE_NAME)).writeObject(highscores);
        } catch (Exception ignored) {}
    }

    public List<Highscore> findAll() {
        return Collections.unmodifiableList(highscores);
    }

}
