package ch.zhaw.it.pm2.professor.model;

/**
 * Represents the player of the game.
 */
public class User {

    private String name;
    private int score;
    private int highscore;
    private static final int NONE = 0;

    /**
     * Constuctor Class User.
     * @param name name of the user
     * @param score score of the user
     * @param highscore highscore of the user
     */
    public User(String name, int score, int highscore) {
        this.name = name;
        this.score = score;
        this.highscore = highscore;
    }

    public User(String name) {
        this(name, NONE, NONE);
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getHighscore() {
        return highscore;
    }

    public void setHighscore(int highscore) {
        this.highscore = highscore;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return name.equals(user.name);
    }
}
