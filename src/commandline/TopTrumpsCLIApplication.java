package commandline;


import TopTrumps.Deck;
import TopTrumps.Card;
import TopTrumps.Player;
import TopTrumps.Game;
import TopTrumps.Round;
import TopTrumps.CommunalPile;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

/**
 * Top Trumps command line application
 */
public class TopTrumpsCLIApplication {

        private final String deck_file; 
        private final int no_cards;
        private final String user_name;
        private final ArrayList<Card> Deck= new ArrayList<Card>();;
	private final Deck deck = new Deck(Deck,"Size","Speed","Range","FirePower","Cargo");  		 
        private Player[] Players; 
        private Player decidingPlayer;
        private Player[] NewPlayers;
	private Game Game;  		 
	private Round Round;		 
	private CommunalPile currentPile;
        private int numPlayers;
	private String prevRoundString;
        private boolean userWantsToQuit; // flag to check whether the user wants to quit the application
        private ArrayList<String> lines = new ArrayList<String>();
        
        //Constructor
        public TopTrumpsCLIApplication(String deck_file, int no_cards, String user_name) {
            this.deck_file = deck_file;
            this.no_cards = no_cards;
            this.user_name = user_name;
        }
        //main 
	public static void main(String[] args) {
            boolean writeGameLogsToFile = false; // Should we write game logs to file?
            if (args[0].equalsIgnoreCase("true")) writeGameLogsToFile=true; // Command line selection
            TopTrumpsCLIApplication TopTrumps = new TopTrumpsCLIApplication("StarCitizenDeck.txt",40,"Human");//The class object is created TopTrumpsCLIApplication initializing the name of the deck, the number of cards that the deck must have and the name of the human who played
		// Loop until the user wants to exit the game
            while (!TopTrumps.userWantsToQuit) {
                // ----------------------------------------------------
		// Add your game logic here based on the requirements
		// ----------------------------------------------------
                TopTrumps.userWantsToQuit=true; 
                TopTrumps.prevRoundString = "";//String variable that is used to show the results of the rounds that are given
                TopTrumps.deck.shuffleDeck(); //Based on the deck that is loaded, all cards are shuffled		
                TopTrumps.Game = new Game(0,0); //An object of the Game class is created     
                /*System.out.println ("Please choose with how many players you want to play between 2 and 5.");
                String entradaTeclado;
                Scanner entradaEscaner = new Scanner (System.in); 
                entradaTeclado = entradaEscaner.nextLine ();
                if(isNumeric(entradaTeclado)){
                    if(entradaTeclado == "1"){
                        System.out.println ("Please choose with how many players you want to play between 2 and 5.");
                        TopTrumps.userWantsToQuit=true; 
                    } else if(Integer.parseInt(entradaTeclado) > 5){
                        System.out.println ("You cannot play more than 5 players");
                        TopTrumps.userWantsToQuit=true; 
                    }else{*/
                        //System.out.println (args[1]+" PASO");
                        TopTrumps.numPlayers = 5; //In this variable it is indicated how many players will be in the game, including the human if this were the option chosen
                        TopTrumps.NewPlayers = new Player[TopTrumps.numPlayers]; //The variable that will handle all the players of the new game is created.
                        if(args.length > 1){ //It is verified if arguments were passed to the execution of the program to know what the game mode will be
                            if(!args[1].equals("-b")){ //If the argument is different from -b then the mode chosen is that a human play against the 4 robots.
                                Player Human = new Player(TopTrumps.user_name); //We create the object to handle the human player
                                TopTrumps.NewPlayers[0] = Human;//We assign the human class to the class that handles all the players in index 0
                                String[] CompPlayerNames = { "BotOne", "BotTwo", "BotThree", "BotFour" };//We create an arrangement of the names of our robots
                                for (int i = 1; i < TopTrumps.numPlayers; i++) { //Here we create one by one the additional players apart from the player one that would be the human
                                    TopTrumps.NewPlayers[i] = new Player(CompPlayerNames[i - 1]);
                                }
                                TopTrumps.Players = TopTrumps.NewPlayers;  

                                Random rand = new Random();//We create the random object of java that will help us so that the system randomly decides which player to start the game
                                int decidingPlayerIndex = rand.nextInt(TopTrumps.Players.length);//We use its nextInt method and we pass the amount of players that will play to give us the index of the player who started the game.
                                TopTrumps.decidingPlayer = TopTrumps.Players[decidingPlayerIndex];
                                String WhoseTurn = String.format("Current player turn: %s%n%n", 
                                                           TopTrumps.decidingPlayer.getName());
                                System.out.println(WhoseTurn);
                                TopTrumps.currentPile = new CommunalPile();
                                for (int i = 0; i < TopTrumps.no_cards; i++) {
                                    Player p = TopTrumps.Players[i % TopTrumps.numPlayers];
                                    p.giveCard(TopTrumps.deck.getDeck().get(i));
                                }
                                String UserCardInfo;
                                Player user = TopTrumps.Players[0];
                                if (user.getHand().length == 0) {
                                    String s = String.format(TopTrumps.user_name + " have no cards left.\n\n");
                                    UserCardInfo = s;
                                }else{
                                    Card UserCurrentCard = user.getHand()[0];
                                    String CardDescription = String.format("%s%n", UserCurrentCard.getName());
                                    String CardAttribute1 = String.format("%s: %s   ", TopTrumps.deck.getSize(),
                                                        UserCurrentCard.getSize());
                                    String CardAttribute2 = String.format("%s: %s   ", TopTrumps.deck.getSpeed(),
                                                        UserCurrentCard.getSpeed());
                                    String CardAttribute3 = String.format("%s: %s   ", TopTrumps.deck.getRange(),
                                                        UserCurrentCard.getRange());
                                    String CardAttribute4 = String.format("%s: %s   ", TopTrumps.deck.getFirepower(),
                                                        UserCurrentCard.getFirepower());
                                    String CardAttribute5 = String.format("%s: %s   %n%n", TopTrumps.deck.getCargo(),
                                                    UserCurrentCard.getCargo());
                                    UserCardInfo = CardDescription + CardAttribute1 + CardAttribute2 
                                                                + CardAttribute3 + CardAttribute4 + CardAttribute5;
                                }
                                System.out.println("Current card: " + UserCardInfo);

                                if (TopTrumps.decidingPlayer.getName().equals(TopTrumps.user_name)) {
                                    TopTrumps.UserPicking(1);
                                } else {
                                    TopTrumps.playRound(0,1);
                                }

                                System.out.println();
                                String attribute1 = TopTrumps.deck.getSize();
                                String attribute2 = TopTrumps.deck.getSpeed();
                                String attribute3 = TopTrumps.deck.getRange();
                                String attribute4 = TopTrumps.deck.getFirepower();
                                String attribute5 = TopTrumps.deck.getCargo();
                                String attributeNameString = String.format("%20.20s %15.15s %15.15s " + "%15.15s %15.15s %15.15s", "",
                                                attribute1, attribute2, attribute3, attribute4, attribute5);
                                System.out.println(attributeNameString);
                                for (int i = 0; i < TopTrumps.no_cards; i++) {
                                    Card CurrentCard = TopTrumps.deck.getDeck().get(i);
                                    String nameValue = CurrentCard.getName();
                                    String attribute1Val = Integer.toString(CurrentCard.getSize());
                                    String attribute2Val = Integer.toString(CurrentCard.getSpeed());
                                    String attribute3Val = Integer.toString(CurrentCard.getRange());
                                    String attribute4Val = Integer.toString(CurrentCard.getFirepower());
                                    String attribute5Val = Integer.toString(CurrentCard.getCargo());

                                    String attValString = String.format("%20.20s %15.15s %15.15s " + "%15.15s %15.15s %15.15s", nameValue,
                                                        attribute1Val, attribute2Val, attribute3Val, attribute4Val, attribute5Val);
                                    System.out.println(attValString);
                                }
                                System.out.println();
                                for (Player P : TopTrumps.Players) {
                                    System.out.println("-------------------------------------");
                                    System.out.println("Cards belonging to: " + P.getName());
                                    String attrCard1 = TopTrumps.deck.getSize();
                                    String attrCard2 = TopTrumps.deck.getSpeed();
                                    String attrCard3 = TopTrumps.deck.getRange();
                                    String attrCard4 = TopTrumps.deck.getFirepower();
                                    String attrCard5 = TopTrumps.deck.getCargo();

                                    String attrCardsNameString = String.format("%20.20s %15.15s %15.15s " + "%15.15s %15.15s %15.15s", "",
                                                    attrCard1, attrCard2, attrCard3, attrCard4, attrCard5);

                                    System.out.println(attrCardsNameString);
                                    for (Card hand : P.getHand()) {
                                        String nameValue = hand.getName();
                                        String attribute1Val = Integer.toString(hand.getSize());
                                        String attribute2Val = Integer.toString(hand.getSpeed());
                                        String attribute3Val = Integer.toString(hand.getRange());
                                        String attribute4Val = Integer.toString(hand.getFirepower());
                                        String attribute5Val = Integer.toString(hand.getCargo());

                                        String attValString = String.format("%20.20s %15.15s %15.15s " + "%15.15s %15.15s %15.15s", nameValue,
                                                                attribute1Val, attribute2Val, attribute3Val, attribute4Val, attribute5Val);

                                        System.out.println(attValString);
                                    }
                                    System.out.println();

                                }
                                TopTrumps.userWantsToQuit=true; 
                            }else{
                                String[] CompPlayerNames = { "BotOne", "BotTwo", "BotThree", "BotFour","BotFive" };
                                for (int i = 0; i < TopTrumps.numPlayers; i++) {
                                    TopTrumps.NewPlayers[i] = new Player(CompPlayerNames[i]);
                                }
                                TopTrumps.Players = TopTrumps.NewPlayers; 

                                Random rand = new Random();
                                int decidingPlayerIndex = rand.nextInt(TopTrumps.Players.length);
                                TopTrumps.decidingPlayer = TopTrumps.Players[decidingPlayerIndex];
                                String WhoseTurn = String.format("Current player turn: %s%n%n", 
                                                           TopTrumps.decidingPlayer.getName());
                                System.out.println(WhoseTurn);
                                TopTrumps.currentPile = new CommunalPile();
                                for (int i = 0; i < TopTrumps.no_cards; i++) {
                                    Player p = TopTrumps.Players[i % TopTrumps.numPlayers];
                                    p.giveCard(TopTrumps.deck.getDeck().get(i));
                                }

                                TopTrumps.playRound(0,2);

                                System.out.println();
                                String attribute1 = TopTrumps.deck.getSize();
                                String attribute2 = TopTrumps.deck.getSpeed();
                                String attribute3 = TopTrumps.deck.getRange();
                                String attribute4 = TopTrumps.deck.getFirepower();
                                String attribute5 = TopTrumps.deck.getCargo();
                                String attributeNameString = String.format("%20.20s %15.15s %15.15s " + "%15.15s %15.15s %15.15s", "",
                                                attribute1, attribute2, attribute3, attribute4, attribute5);
                                System.out.println(attributeNameString);
                                for (int i = 0; i < TopTrumps.no_cards; i++) {
                                    Card CurrentCard = TopTrumps.deck.getDeck().get(i);
                                    String nameValue = CurrentCard.getName();
                                    String attribute1Val = Integer.toString(CurrentCard.getSize());
                                    String attribute2Val = Integer.toString(CurrentCard.getSpeed());
                                    String attribute3Val = Integer.toString(CurrentCard.getRange());
                                    String attribute4Val = Integer.toString(CurrentCard.getFirepower());
                                    String attribute5Val = Integer.toString(CurrentCard.getCargo());

                                    String attValString = String.format("%20.20s %15.15s %15.15s " + "%15.15s %15.15s %15.15s", nameValue,
                                                        attribute1Val, attribute2Val, attribute3Val, attribute4Val, attribute5Val);
                                    System.out.println(attValString);
                                }
                                System.out.println();
                                for (Player P : TopTrumps.Players) {
                                    System.out.println("-------------------------------------");
                                    System.out.println("Cards belonging to: " + P.getName());
                                    String attrCard1 = TopTrumps.deck.getSize();
                                    String attrCard2 = TopTrumps.deck.getSpeed();
                                    String attrCard3 = TopTrumps.deck.getRange();
                                    String attrCard4 = TopTrumps.deck.getFirepower();
                                    String attrCard5 = TopTrumps.deck.getCargo();

                                    String attrCardsNameString = String.format("%20.20s %15.15s %15.15s " + "%15.15s %15.15s %15.15s", "",
                                                    attrCard1, attrCard2, attrCard3, attrCard4, attrCard5);

                                    System.out.println(attrCardsNameString);
                                    for (Card hand : P.getHand()) {
                                        String nameValue = hand.getName();
                                        String attribute1Val = Integer.toString(hand.getSize());
                                        String attribute2Val = Integer.toString(hand.getSpeed());
                                        String attribute3Val = Integer.toString(hand.getRange());
                                        String attribute4Val = Integer.toString(hand.getFirepower());
                                        String attribute5Val = Integer.toString(hand.getCargo());

                                        String attValString = String.format("%20.20s %15.15s %15.15s " + "%15.15s %15.15s %15.15s", nameValue,
                                                                attribute1Val, attribute2Val, attribute3Val, attribute4Val, attribute5Val);

                                        System.out.println(attValString);
                                    }
                                    System.out.println();

                                }
                                TopTrumps.userWantsToQuit=true; 
                            }
                        }else{
                            Player Human = new Player(TopTrumps.user_name);
                            TopTrumps.NewPlayers[0] = Human;
                            String[] CompPlayerNames = { "BotOne", "BotTwo", "BotThree", "BotFour" };
                            for (int i = 1; i < TopTrumps.numPlayers; i++) {
                                TopTrumps.NewPlayers[i] = new Player(CompPlayerNames[i - 1]);
                            }
                            TopTrumps.Players = TopTrumps.NewPlayers;  

                            Random rand = new Random();
                            int decidingPlayerIndex = rand.nextInt(TopTrumps.Players.length);
                            TopTrumps.decidingPlayer = TopTrumps.Players[decidingPlayerIndex];
                            String WhoseTurn = String.format("Current player turn: %s%n%n", 
                                                           TopTrumps.decidingPlayer.getName());
                            System.out.println(WhoseTurn);
                            TopTrumps.currentPile = new CommunalPile();
                            for (int i = 0; i < TopTrumps.no_cards; i++) {
                                Player p = TopTrumps.Players[i % TopTrumps.numPlayers];
                                p.giveCard(TopTrumps.deck.getDeck().get(i));
                            }
                            String UserCardInfo;
                            Player user = TopTrumps.Players[0];
                            if (user.getHand().length == 0) {
                                String s = String.format(TopTrumps.user_name + " have no cards left.\n\n");
                                UserCardInfo = s;
                            }else{
                                Card UserCurrentCard = user.getHand()[0];
                                String CardDescription = String.format("%s%n", UserCurrentCard.getName());
                                String CardAttribute1 = String.format("%s: %s   ", TopTrumps.deck.getSize(),
                                                        UserCurrentCard.getSize());
                                String CardAttribute2 = String.format("%s: %s   ", TopTrumps.deck.getSpeed(),
                                                        UserCurrentCard.getSpeed());
                                String CardAttribute3 = String.format("%s: %s   ", TopTrumps.deck.getRange(),
                                                        UserCurrentCard.getRange());
                                String CardAttribute4 = String.format("%s: %s   ", TopTrumps.deck.getFirepower(),
                                                        UserCurrentCard.getFirepower());
                                String CardAttribute5 = String.format("%s: %s   %n%n", TopTrumps.deck.getCargo(),
                                                    UserCurrentCard.getCargo());
                                UserCardInfo = CardDescription + CardAttribute1 + CardAttribute2 
                                                                + CardAttribute3 + CardAttribute4 + CardAttribute5;
                            }
                            System.out.println("Current card: " + UserCardInfo);

                            if (TopTrumps.decidingPlayer.getName().equals(TopTrumps.user_name)) {
                                TopTrumps.UserPicking(1);
                            } else {
                                TopTrumps.playRound(0,1);
                            }

                            System.out.println();
                            String attribute1 = TopTrumps.deck.getSize();
                            String attribute2 = TopTrumps.deck.getSpeed();
                            String attribute3 = TopTrumps.deck.getRange();
                            String attribute4 = TopTrumps.deck.getFirepower();
                            String attribute5 = TopTrumps.deck.getCargo();
                            String attributeNameString = String.format("%20.20s %15.15s %15.15s " + "%15.15s %15.15s %15.15s", "",
                                                attribute1, attribute2, attribute3, attribute4, attribute5);
                            System.out.println(attributeNameString);
                            for (int i = 0; i < TopTrumps.no_cards; i++) {
                                Card CurrentCard = TopTrumps.deck.getDeck().get(i);
                                String nameValue = CurrentCard.getName();
                                String attribute1Val = Integer.toString(CurrentCard.getSize());
                                String attribute2Val = Integer.toString(CurrentCard.getSpeed());
                                String attribute3Val = Integer.toString(CurrentCard.getRange());
                                String attribute4Val = Integer.toString(CurrentCard.getFirepower());
                                String attribute5Val = Integer.toString(CurrentCard.getCargo());

                                String attValString = String.format("%20.20s %15.15s %15.15s " + "%15.15s %15.15s %15.15s", nameValue,
                                                        attribute1Val, attribute2Val, attribute3Val, attribute4Val, attribute5Val);
                                System.out.println(attValString);
                            }
                            System.out.println();
                            for (Player P : TopTrumps.Players) {
                                System.out.println("-------------------------------------");
                                System.out.println("Cards belonging to: " + P.getName());
                                String attrCard1 = TopTrumps.deck.getSize();
                                String attrCard2 = TopTrumps.deck.getSpeed();
                                String attrCard3 = TopTrumps.deck.getRange();
                                String attrCard4 = TopTrumps.deck.getFirepower();
                                String attrCard5 = TopTrumps.deck.getCargo();

                                String attrCardsNameString = String.format("%20.20s %15.15s %15.15s " + "%15.15s %15.15s %15.15s", "",
                                                    attrCard1, attrCard2, attrCard3, attrCard4, attrCard5);

                                System.out.println(attrCardsNameString);
                                for (Card hand : P.getHand()) {
                                    String nameValue = hand.getName();
                                    String attribute1Val = Integer.toString(hand.getSize());
                                    String attribute2Val = Integer.toString(hand.getSpeed());
                                    String attribute3Val = Integer.toString(hand.getRange());
                                    String attribute4Val = Integer.toString(hand.getFirepower());
                                    String attribute5Val = Integer.toString(hand.getCargo());

                                    String attValString = String.format("%20.20s %15.15s %15.15s " + "%15.15s %15.15s %15.15s", nameValue,
                                                                attribute1Val, attribute2Val, attribute3Val, attribute4Val, attribute5Val);

                                    System.out.println(attValString);
                                }
                                System.out.println();

                            }
                            TopTrumps.userWantsToQuit=true;
                        }
                    /*}
                }else{
                    System.out.println("You must enter a numerical value between the opciones given");
                    TopTrumps.userWantsToQuit=false; 
                }*/
            }
	}
        
        public static boolean isNumeric(String cadena){
            boolean result;
            
            try {
                Integer.parseInt(cadena);
                result = true;
            } catch (NumberFormatException excepcion){
                result = false;
            }
            
            return result;
        }
        
        private void UserPicking(int type) {
            System.out.println ("Please choose what attribute you want to take for the round: Size choose 1, Speed choose 2, Range choose 3, FirePower choose 4, Cargo choose 5");
            String entradaTeclado;
            Scanner entradaEscaner = new Scanner (System.in); 
            entradaTeclado = entradaEscaner.nextLine ();
            if(isNumeric(entradaTeclado)){
                switch (entradaTeclado) {
                    case "1":playRound(1,type);break;
                    case "2":playRound(2,type);break;
                    case "3":playRound(3,type);break;
                    case "4":playRound(4,type);break;
                    case "5":playRound(5,type);break;
                    default:
                        System.out.println ("You must choose an attribute based on those available to play");
                        UserPicking(type);
                        break;
                }
            }else{
                System.out.println ("You must place a numeric value between the given values in order to play");
                UserPicking(type);
            }
	}

	private void checkIfGameOver(int type) {
            if(type == 1){
                int pass = 0;
                for (int i = 0; i < this.numPlayers; i++) {
                    Player user = this.Round.getPlayers()[i];
                    int length = user.getHand().length;
                    /*if (user.getHand().length == this.Round.getDeck().getDeck().size()-this.Round.getPile().getCards().length 
                            || user.getHand().length == 0) {*/
                    if(length == 40){
                        String Result = "Lost";
                        if(this.Round.getWinner() != null){
                            if (this.Round.getWinner().getName().equals(this.user_name)) {
                                Result = "Win";
                            }
                            System.out.println ("Game over, you " + Result);
                            this.userWantsToQuit = false;
                        }
                        pass = 1;
                        break;
                    }
                }
                if(pass == 0){
                    if (decidingPlayer.getName().equals(user_name)) {
                        UserPicking(type);
                    } else {
                        playRound(0,type);
                    }
                }
            }else if(type == 2){
                for (int i = 0; i < this.numPlayers; i++) {
                    Player user = this.Round.getPlayers()[i];	
                    if (user.getHand().length == this.Round.getDeck().getDeck().size()-this.Round.getPile().getCards().length 
                            || user.getHand().length == 0) {
                        String Result;
                        if(this.Round.getWinner() != null){
                            if (this.Round.getWinner().getName().equals(user.getName())) {
                                Result = "Win";
                                System.out.println ("Game over, "+ user.getName() +" "+ Result);
                            }
                        }
                        
                        this.userWantsToQuit = false;
                    }else{
                        if (decidingPlayer.getName().equals(user_name)) {
                            UserPicking(type);
                        } else {
                            playRound(0,type);
                        }
                    }
                }
            }
	}

	
	private void playRound(int trumpIndex, int type) {
	    Round CurrRound;
            CurrRound = new Round(Players, decidingPlayer, currentPile, 
                    trumpIndex, deck,
                    no_cards);
	    this.Round = CurrRound;		  
	    this.Round.saveValues();
	    this.Round.startHovering();	      
	    currentPile = Round.getPile(); 
	    if (!this.Round.isDraw()) {
		this.decidingPlayer = this.Round.getWinner();
	    }			  
            String WhoseTurn = String.format("Current player turn: %s%n%n", 
				           this.decidingPlayer.getName());
	    System.out.println (WhoseTurn);
	    System.out.println ("Cards in pile: "+this.Round.getPile().getCards().length);
	    this.prevRoundString = Round.getRoundString(type);
	    String displayText = this.prevRoundString;
	    System.out.println(displayText);
	    if (this.Round.isDraw()) {
		this.Game.setNumDraws(this.Game.getNumDraws()+1);
	    }
	    this.Game.setNumRounds(this.Game.getNumRounds()+1);
            
	    if(type == 1){
                Player user = this.Players[0];
                String UserCardInfo;
                if (user.getHand().length == 0) {
                    String s = String.format(this.user_name + " have no cards left.\n\n");
                    UserCardInfo = s;
                }else{
                    Card UserCurrentCard = user.getHand()[0];
                    String CardDescription = String.format("%s%n", UserCurrentCard.getName());
                    String CardAttribute1 = String.format("%s: %s   ", deck.getSize(),
                                        UserCurrentCard.getSize());
                    String CardAttribute2 = String.format("%s: %s   ", deck.getSpeed(),
                                        UserCurrentCard.getSpeed());
                    String CardAttribute3 = String.format("%s: %s   ", deck.getRange(),
                                        UserCurrentCard.getRange());
                    String CardAttribute4 = String.format("%s: %s   ", deck.getFirepower(),
                                        UserCurrentCard.getFirepower());
                    String CardAttribute5 = String.format("%s: %s   %n%n", deck.getCargo(),
                                        UserCurrentCard.getCargo());
                    UserCardInfo = CardDescription + CardAttribute1 + CardAttribute2 
                                                    + CardAttribute3 + CardAttribute4 + CardAttribute5;
                }

                System.out.println ("Cards left in hand: " + Players[0].getHand().length 
				           + "\nCurrent card: " + UserCardInfo);
                switch (this.Players.length) {
                    case 2:
                        System.out.println ("Bot 1 Cards left in hand:\n" + this.Players[1].getHand().length);
                        break;
                    case 3:
                        System.out.println ("Bot 1 Cards left in hand:\n" + this.Players[1].getHand().length);
                        System.out.println ("Bot 2 Cards left in hand:\n" + this.Players[2].getHand().length);
                        break;
                    case 4:
                        System.out.println ("Bot 1 Cards left in hand:\n" + this.Players[1].getHand().length);
                        System.out.println ("Bot 2 Cards left in hand:\n" + this.Players[2].getHand().length);
                        System.out.println ("Bot 3 Cards left in hand:\n" + this.Players[3].getHand().length);
                        break;
                    case 5:
                        System.out.println ("Bot 1 Cards left in hand:\n" + this.Players[1].getHand().length);
                        System.out.println ("Bot 2 Cards left in hand:\n" + this.Players[2].getHand().length);
                        System.out.println ("Bot 3 Cards left in hand:\n" + this.Players[3].getHand().length);
                        System.out.println ("Bot 4 Cards left in hand:\n" + this.Players[4].getHand().length);
                        break;
                }
            }else if(type == 2){
                switch (this.Players.length) {
                    case 5:
                        System.out.println ("Bot 1 Cards left in hand:\n" + this.Players[0].getHand().length);
                        System.out.println ("Bot 2 Cards left in hand:\n" + this.Players[1].getHand().length);
                        System.out.println ("Bot 3 Cards left in hand:\n" + this.Players[2].getHand().length);
                        System.out.println ("Bot 4 Cards left in hand:\n" + this.Players[3].getHand().length);
                        System.out.println ("Bot 5 Cards left in hand:\n" + this.Players[4].getHand().length);
                        break;
                }
            }
            checkIfGameOver(type);
	}

}
