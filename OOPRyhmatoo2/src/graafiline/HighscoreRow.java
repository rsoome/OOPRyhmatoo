package graafiline;

import highscore.Highscore;

public class HighscoreRow extends Highscore {

    private final int place;

    public HighscoreRow(int place, String name, int score) {
        super(name, score);
        this.place = place;
    }

    public int getPlace() {
        return place;
    }

}
