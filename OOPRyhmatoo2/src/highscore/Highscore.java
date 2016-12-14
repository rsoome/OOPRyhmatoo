package highscore;

import java.io.Serializable;

//hoiab ühte mänguskoori
public class Highscore implements Serializable {
    static final long serialVersionUID = 687962192884005033L;

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
