package TopTrumps;

/**
 */
public class Card {
    
	// Instance variables
    //attributes 
	private String name;
	private int size;
	private int speed;
	private int firepower;
	private int cargo;
	private int range;
	
	
	//constructor 
	public Card(String n, int c , int sz, int f, int sp, int r) {
		this.name = n;
		this.cargo = c;
		this.size = sz;
		this.firepower = f;
		this.speed = sp;
		this.range = r;
	}
	
	public Card(String fullLine)	{
		  String[] splitLine = fullLine.split(" ");
		  this.name = splitLine[0];
		  this.cargo = Integer.parseInt(splitLine[1]);
		  this.size = Integer.parseInt(splitLine[2]);
		  this.firepower = Integer.parseInt(splitLine[3]);
		  this.speed = Integer.parseInt(splitLine[4]);
		  this.range = Integer.parseInt(splitLine[5]);

	}
	
	//getters and setters 
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getSize() {
		return size;
	}
	public int setSize() {
		return size;
	}

	public int getFirepower() {
		return firepower;
	}

	public void setFirepower(int firepower) {
		this.firepower = firepower;
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public int getCargo() {
		return cargo;
	}

	public void setCargo(int cargo) {
		this.cargo = cargo;
	}

	public int getRange() {
		return range;
	}

	public void setRange(int range) {
		this.range = range;
	}
 
}
