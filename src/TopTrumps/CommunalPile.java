package TopTrumps;

/**
 * @author PC-ON
 */
public class CommunalPile {
	
    private Card[] cards;
    public Card[] getCards() {return cards;}
    
    // Constructor 
    public CommunalPile() {this.cards = new Card[0];}
    
    public void giveCard(Card newCard) {
        Card[] newHand = new Card[this.cards.length+1];
        System.arraycopy(this.cards, 0, newHand, 0, cards.length);
        newHand[cards.length] = newCard;
        this.cards = newHand;
    }
}
