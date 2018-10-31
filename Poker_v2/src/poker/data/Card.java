package poker.data;

public class Card {
	
	public int suit;
	public int num;
	
	public Card(int suit, int num) {
		this.suit = suit;
		this.num = num;
	}
	
	public int getSuit() {
		return suit;
	}
	
	public int getNum() {
		return num;
	}
	
	@Override
	public String toString() {
		return suit + " " + num;
	}

}
