package TopTrumps;

public class Player {
    
    private Card[] hand; 
    private final String name;
    private boolean keepPlaying;

    // Constructor
    public Player(String name) {
        this.name = name;
        this.hand = new Card[0];
        this.keepPlaying = true;
    }

    public String getName() {return name;}

    public Card[] getHand() {return hand;}
    public void setHand(Card[] hand) {this.hand = hand;}

    public boolean isKeepPlaying() {return keepPlaying;}
    public void setKeepPlaying(boolean keepPlaying) {this.keepPlaying = keepPlaying;}
    
    public void giveCard(Card newCard) {
        Card[] newHand = new Card[this.hand.length+1];
        System.arraycopy(this.hand, 0, newHand, 0, hand.length);
        newHand[hand.length] = newCard;
        this.hand = newHand;
    }
}
