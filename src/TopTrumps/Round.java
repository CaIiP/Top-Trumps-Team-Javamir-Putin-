package TopTrumps;

import java.util.Arrays;

public class Round {
	private final int ATTR = 5;
	private final Player[] players;
	private Card[] Cards;
	private final int Index;
	private CommunalPile pile;
	private Player winner = null;
	private final Player decidingPlayer;
	private final Deck deck;
	private boolean draw = false;
	private final int[] prevValues;

	public Round(Player[] playersArray, Player CurrentDecidingPlayer, CommunalPile communalPipe, int Index, Deck deck,
			 int numCards) {
	    this.players = playersArray;
	    this.deck = deck;
	    this.pile = communalPipe;
	    this.decidingPlayer=CurrentDecidingPlayer;
	    prevValues = new int[players.length];
	    if (Index == 0) {
		this.Index = getIndex();
	    } else {
		this.Index = Index;
            }
	}

        public Player[] getPlayers() {return players;}
        public Deck getDeck() {return deck;}
        public CommunalPile getPile() {return pile;}
        public Player getWinner() {return winner;}
        public boolean isDraw() {return draw;}
        
    
	private int getIndex() {
	    System.out.println(decidingPlayer.getName());
	    Card topCard = decidingPlayer.getHand()[0];
	    int AttributeIndex = 0;
	    int AttributeValue = 0;
	    for (int i = 0; i < ATTR; i++) {
                int Attribute = -1;
                if(i == 1){Attribute = topCard.getSize();
                }else if(i == 2){Attribute = topCard.getSpeed();
                }else if(i == 3){Attribute = topCard.getRange();
                }else if(i == 4){Attribute = topCard.getFirepower();
                }else if(i == 5){Attribute = topCard.getCargo();
                }
		if (Attribute > AttributeValue) {
                    AttributeValue = Attribute;
		    AttributeIndex = i;
		}
            }
	    return AttributeIndex;
	}
        
        public void saveValues() {
	    Card[] cards = new Card[players.length];
	    for(int i = 0; i < cards.length; i++) {
		if (players[i].getHand().length != 0) {
		    cards[i] = players[i].getHand()[0];
                    int Attribute = -1;
                    if(Index == 1){Attribute = cards[i].getSize();
                    }else if(Index == 2){Attribute = cards[i].getSpeed();
                    }else if(Index == 3){Attribute = cards[i].getRange();
                    }else if(Index == 4){Attribute = cards[i].getFirepower();
                    }else if(Index == 5){Attribute = cards[i].getCargo();}  
		    prevValues[i] = Attribute;
		}
            }
	}
        
        public String getRoundString(int type) {
            if(type == 1){
                String roundString = String.format("");
                String Attribute = "";
                if(Index == 1){Attribute = this.deck.getSize();
                }else if(Index == 2){Attribute = this.deck.getSpeed();
                }else if(Index == 3){Attribute = this.deck.getRange();
                }else if(Index == 4){Attribute = this.deck.getFirepower();
                }else if(Index == 5){Attribute = this.deck.getCargo();}
                roundString += String.format("Previous round attribute: %s%n", Attribute);
                String score = String.format("");
                for (int i = 0; i < this.players.length; i++) {
                    Player p = players[i];
                    if (p.getHand().length > 0) {
                        Card c = p.getHand()[0];
                        score += String.format("%s: ", players[i].getName());
                        score += String.format("%d    ", prevValues[i]);
                    }
                }
                score += String.format("%n%n");
                roundString += score;

                System.out.println();
                String WinLost = String.format("%n%n");
                Player user = this.players[0];
                if (user.getHand().length == this.deck.getDeck().size()-this.pile.getCards().length) {
                    if(this.winner != null){
                        WinLost += String.format("YOU WON THE GAME!: "+ this.winner.getName());
                    }else{
                        WinLost += String.format("YOU WON THE GAME!:"+ user.getName());
                    }
                } else if (user.getHand().length == 0) {
                    if(this.winner != null){
                        WinLost += String.format("YOU LOST THE GAME!:"+ this.winner.getName());	
                    }else{
                        WinLost += String.format("YOU LOST THE GAME!:"+ user.getName());
                    }
                }
                roundString += WinLost;
                System.out.println();
                String WinnerDraw = String.format("%n%n");
                if (this.draw) {WinnerDraw += String.format("This round was a draw.%n%n");
                } else {WinnerDraw += String.format("%s won the previous round%n%n", this.winner.getName());}
                roundString += WinnerDraw;
                return roundString;
            }else{
                String roundString = String.format("");
                String Attribute = "";
                if(Index == 1){Attribute = this.deck.getSize();
                }else if(Index == 2){Attribute = this.deck.getSpeed();
                }else if(Index == 3){Attribute = this.deck.getRange();
                }else if(Index == 4){Attribute = this.deck.getFirepower();
                }else if(Index == 5){Attribute = this.deck.getCargo();}
                roundString += String.format("Previous round attribute: %s%n", Attribute);
                String score = String.format("");
                for (int i = 0; i < this.players.length; i++) {
                    Player p = players[i];
                    if (p.getHand().length > 0) {
                        Card c = p.getHand()[0];
                        score += String.format("%s: ", players[i].getName());
                        score += String.format("%d    ", prevValues[i]);
                    }
                }
                score += String.format("%n%n");
                roundString += score;

                String WinLost = String.format("%n%n");
                
                for (int i = 1; i < players.length; i++) {
                    Player user = this.players[i];
                    if (user.getHand().length == this.deck.getDeck().size()-this.pile.getCards().length) {
                        if(this.winner != null){
                            WinLost += String.format("YOU WON THE GAME!: "+ this.winner.getName());
                        }else{
                            WinLost += String.format("YOU WON THE GAME!: "+ user.getName());
                        }
                    } 
                }
                roundString += WinLost;
                
                String WinnerDraw = String.format("%n%n");
                if (this.draw) {WinnerDraw += String.format("This round was a draw.%n%n");
                } else {WinnerDraw += String.format("%s won the previous round%n%n", this.winner.getName());}
                roundString += WinnerDraw;
                return roundString;
            }
	}
        
	public void startHovering() {
	    System.out.println("---------------------------");
            Card[] cardsArray = { null, null, null, null, null };
            for (int i = 0; i < players.length; i++) {
		if (players[i].getHand().length != 0) {
                    
                    Card takenCard = players[i].getHand()[0];        
                    Card[] newHand = new Card[players[i].getHand().length-1];
                    for (int j = 0; j < newHand.length; j++){
                        newHand[j] = players[i].getHand()[j+1];
                    }
                    players[i].setHand(newHand);
                    if (players[i].getHand().length == 0){
                        players[i].setKeepPlaying(false);
                    }      
                             
                    Card c = takenCard;
                    cardsArray[i] = c;
		}
            }
	    Cards = cardsArray; 
	    System.out.println("---------------------------");
	    System.out.println("******** Cards in play: ********");
	    String attribute1Name = this.deck.getSize();
	    String attribute2Name = this.deck.getSpeed();
	    String attribute3Name = this.deck.getRange();
	    String attribute4Name = this.deck.getFirepower();
	    String attribute5Name = this.deck.getCargo();
	    String attributeNameString = String.format("%20.20s %15.15s %15.15s " + "%15.15s %15.15s %15.15s", "",
				attribute1Name, attribute2Name, attribute3Name, attribute4Name, attribute5Name);
	    System.out.println(attributeNameString);
            for (Card Card : Cards) {
                if (Card != null) {
                    String nameValue = Card.getName();
                    String att1Value = Integer.toString(Card.getSize());
                    String att2Value = Integer.toString(Card.getSpeed());
                    String att3Value = Integer.toString(Card.getRange());
                    String att4Value = Integer.toString(Card.getFirepower());
                    String att5Value = Integer.toString(Card.getCargo());

                    String attValString = String.format("%20.20s %15.15s %15.15s " + "%15.15s %15.15s %15.15s", nameValue,
                                    att1Value, att2Value, att3Value, att4Value, att5Value);

                    System.out.println(attValString);
                }
            }
	    System.out.println("---------------------------");
            String Attribute = "";
            if(Index == 1){Attribute = this.deck.getSize();
            }else if(Index == 2){Attribute = this.deck.getSpeed();
            }else if(Index == 3){Attribute = this.deck.getRange();
            }else if(Index == 4){Attribute = this.deck.getFirepower();
            }else if(Index == 5){Attribute = this.deck.getCargo();}
            System.out.println("Category selected: " + Attribute);
            System.out.println("Values:");
            for (int i = 0; i < this.players.length; i++) {
		Player p = this.players[i];
		if (p.getHand().length > 0) {
		    Card c = p.getHand()[0];
		    System.out.println(p.getName() + ": " + prevValues[i]);
		}
            }
	    System.out.println();
            boolean drawR = false;
	    int maxScore = 50;
	    int[] playerScores = new int[maxScore];
            //System.out.println(Arrays.toString(playerScores));
	    for (int i = 0; i < players.length; i++) {
		if (Cards[i] != null) {
		    Card c = Cards[i];
                    int AttributeR = -1;
                    if(this.Index == 1){AttributeR = c.getSize();
                    }else if(this.Index == 2){AttributeR = c.getSpeed();
                    }else if(this.Index == 3){AttributeR = c.getRange();
                    }else if(this.Index == 4){AttributeR = c.getFirepower();
                    }else if(this.Index == 5){AttributeR = c.getCargo();}
		    int score = AttributeR;
                    if(score == 0){
                        playerScores[score]++;
                    }else{
                        playerScores[score - 1]++;
                    }
                    
                                
		}
            }
	    for (int i = maxScore - 1; i >= 0; i--) {
		if (playerScores[i] == 1) {
		    break;
		} else if (playerScores[i] > 1) {
		    drawR = true;
		    break;
		}
	    }
	    this.draw = drawR; 
            int topS = 0;
	    for (int i = 0; i < this.players.length; i++) {
		if ((players[i].getHand().length + 1) > 0) {
		    try{
			Card c = Cards[i];
                        int AttributeW = -1;
                        if(this.Index == 1){AttributeW = c.getSize();
                        }else if(this.Index == 2){AttributeW = c.getSpeed();
                        }else if(this.Index == 3){AttributeW = c.getRange();
                        }else if(this.Index == 4){AttributeW = c.getFirepower();
                        }else if(this.Index == 5){AttributeW = c.getCargo();
                        }
			if (AttributeW == topS) {
			    winner = null;
			} else if (AttributeW > topS) {
			    topS = AttributeW;
			    winner = players[i];
			}
		    } catch (Exception e){
			System.out.println("" + players[i].getName() + " has no cards left.");
		    }				
		}
            }	   
            if (winner == null) {
                for (Card c : Cards) {if (c != null) {this.pile.giveCard(c);}}
	    } else {		
                for (Card c : Cards) {if (c != null) {winner.giveCard(c);}}
                for (Card c : pile.getCards()) {if (c != null) {winner.giveCard(c);}}
		pile = new CommunalPile();
	    }
	    System.out.println("---------------------------");
	    System.out.println("Player hands post-round: \n");
            for (Player p : this.players) {
                if (p.getHand().length > 0) {
                    System.out.println("---------------------------");
                    System.out.println("Cards in hand belonging to: " + p.getName());
                    String attribute1PHandName = this.deck.getSize();
                    String attribute2PHandName = this.deck.getSpeed();
                    String attribute3PHandName = this.deck.getRange();
                    String attribute4PHandName = this.deck.getFirepower();
                    String attribute5PHandName = this.deck.getCargo();
                    String attributePHandNameString = String.format("%20.20s %15.15s %15.15s " + "%15.15s %15.15s %15.15s", "",
                                    attribute1PHandName, attribute2PHandName, attribute3PHandName, attribute4PHandName, attribute5PHandName);

                    System.out.println(attributePHandNameString);
                    for (Card hand : p.getHand()) {
                        String nameValue = hand.getName();
                        String att1Value = Integer.toString(hand.getSize());
                        String att2Value = Integer.toString(hand.getSpeed());
                        String att3Value = Integer.toString(hand.getRange());
                        String att4Value = Integer.toString(hand.getFirepower());
                        String att5Value = Integer.toString(hand.getCargo());
                        String attValString = String.format("%20.20s %15.15s %15.15s " + "%15.15s %15.15s %15.15s", nameValue,
                                        att1Value, att2Value, att3Value, att4Value, att5Value);
                        System.out.println(attValString);
                    }
                    System.out.println();
                }
            }
	    if (this.pile.getCards().length > 0) {
		System.out.println("---------------------------");
		System.out.println("Common Pile:");
		String attribute1CPileName = this.deck.getSize();
		String attribute2CPileName = this.deck.getSpeed();
		String attribute3CPileName = this.deck.getRange();
		String attribute4CPileName = this.deck.getFirepower();
		String attribute5CPileName = this.deck.getCargo();
		String attributeCPileNameString = String.format("%20.20s %15.15s %15.15s " + "%15.15s %15.15s %15.15s", "",
				attribute1CPileName, attribute2CPileName, attribute3CPileName, attribute4CPileName, attribute5CPileName);

		System.out.println(attributeCPileNameString);
		for (int i = 0; i < this.pile.getCards().length; i++) {
                    String nameValue = this.pile.getCards()[i].getName();
                    String att1Value = Integer.toString(this.pile.getCards()[i].getSize());
                    String att2Value = Integer.toString(this.pile.getCards()[i].getSpeed());
                    String att3Value = Integer.toString(this.pile.getCards()[i].getRange());
                    String att4Value = Integer.toString(this.pile.getCards()[i].getFirepower());
                    String att5Value = Integer.toString(this.pile.getCards()[i].getCargo());

                    String attValString = String.format("%20.20s %15.15s %15.15s " + "%15.15s %15.15s %15.15s", nameValue,
                                    att1Value, att2Value, att3Value, att4Value, att5Value);

                    System.out.println(attValString);
		}
		System.out.println("---------------------------");
	    }
	}
       
}
