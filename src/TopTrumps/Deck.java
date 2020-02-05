package TopTrumps;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Deck {
	private ArrayList<String> lines = new ArrayList<String>();
	private ArrayList<Card> deck = new ArrayList<Card>();
        private String size;
	private String speed;
	private String firepower;
	private String cargo;
	private String range;

        public Deck(ArrayList<Card> deck,String size, String speed, String firepower, String cargo, String range) {
            this.size = size;
            this.speed = speed;
            this.firepower = firepower;
            this.cargo = cargo;
            this.range = range;
            try (BufferedReader br = new BufferedReader(new FileReader("StarCitizenDeck.txt")))	{
                String sCurrentLine;
                while ((sCurrentLine = br.readLine()) != null) {
                    lines.add(sCurrentLine);
                }
            }catch (IOException e)	{
                e.printStackTrace();
            }
            boolean firstRun = true;
            System.out.println("Unshuffled deck:");
            System.out.println();
            for (String line : lines) {
                if (firstRun) {
                    firstRun = false;
                    continue;
                }
                System.out.println(line);
                this.deck.add(new Card(line));
            } 
        }
	
        public void shuffleDeck() {
            int packLength = this.deck.size();
            int[] undeskOfArray = new int[packLength];
            for (int i = 0; i < packLength; i++) {undeskOfArray[i] = i;}

            for (int i = 0; i < packLength; i++) {
                int rand = i + (int) (Math.random() * (packLength - i));
                int randomElement = undeskOfArray[rand];
                undeskOfArray[rand] = undeskOfArray[i];
                undeskOfArray[i] = randomElement;
            }
            int[] packOfIntArray = undeskOfArray;
            ArrayList<Card> packOfCards = new ArrayList<Card>();
            for (int i = 0; i < packLength; i++) {
                packOfCards.add(this.deck.get(undeskOfArray[i]));
            }
            this.deck = packOfCards;
            boolean firstRun = true;
            System.out.println();
            System.out.println("Shuffled deck:");
            System.out.println();
            for (Card line : this.deck ) {
                if (firstRun) {
                    firstRun = false;
                    continue;
                }           
                System.out.println(line.getName()+" "+line.getSize()+" "+line.getSpeed()+" "+line.getRange()+" "+line.getFirepower()+" "+line.getCargo());
            } 
            System.out.println();
        }
        
       
	public String getSize() {
		return size;
	}
        
	public void setSize(String size) {
		this.size = size;
	}

	public String getFirepower() {
		return firepower;
	}

	public void setFirepower(String firepower) {
		this.firepower = firepower;
	}

	public String getSpeed() {
		return speed;
	}

	public void setSpeed(String speed) {
		this.speed = speed;
	}

	public String getCargo() {
		return cargo;
	}

	public void setCargo(String cargo) {
		this.cargo = cargo;
	}

	public String getRange() {
		return range;
	}

	public void setRange(String range) {
		this.range = range;
	}

        public ArrayList<Card> getDeck() {
            return deck;
        }

        public void setDeck(ArrayList<Card> deck) {
            this.deck = deck;
        }

    
        
        
        
        
}