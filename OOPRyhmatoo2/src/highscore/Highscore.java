package highscore;

import java.io.Serializable;

public class Highscore implements Serializable {

    private final String name;
    private final int score;

    public Highscore(String name, int score) {
        this.name = name;
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }
}
