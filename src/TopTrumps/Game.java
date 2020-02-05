package TopTrumps;

/**
 * @author PC-ON
 */
public class Game {
    private int numDraws;
    private int numRounds;

    public Game(int numDraws, int numRounds) {
        this.numDraws = numDraws;
        this.numRounds = numRounds;
    }

    public int getNumDraws() {return numDraws;}
    public void setNumDraws(int numDraws) {this.numDraws = numDraws;}
    public int getNumRounds() {return numRounds;}
    public void setNumRounds(int numRounds) {this.numRounds = numRounds;}
}
