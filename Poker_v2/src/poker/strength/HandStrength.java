package poker.strength;

import java.util.ArrayList;

import poker.data.Card;

// Calculates the value of a 5-card poker hand so that multiple hands can be compared against each other.

public class HandStrength {
	
	public final static int[] interValue = {0, 1000000, 2000000, 3000000, 4000000, 5000000, 6000000, 7000000, 8000000};
	
	public static double valueHand(ArrayList<Card> hand) {
		
		if (hand.size() != 5) {
			throw new IllegalArgumentException("Size of hand must be 5");
		}
		
		bubbleSort(hand);
		
		if (isFlush(hand) && isStraight(hand)) {
			return valueStraightFlush(hand);
			
		} else if (is4Kind(hand)) {
			return value4Kind(hand);
			
		} else if (isFullHouse(hand)) {
			return valueFullHouse(hand);
			
		} else if (isFlush(hand)) {
			return valueFlush(hand);
			
		} else if (isStraight(hand)) {
			return valueStraight(hand);
			
		} else if (is3Kind(hand)) {
			return value3Kind(hand);
			
		} else if (is2Pair(hand)) {
			return value2Pair(hand);
			
		} else if(isPair(hand)) {
			return valuePair(hand);
			
		} else {
			return valueHighCard(hand);
		}
	}
	
	// ------------------  Straight Flush  ----------------------
	public static double valueStraightFlush(ArrayList<Card> hand) {

		/** Value = 8000000 
		 * 			+ highest card
		 */
		
		double value;
		
		value = HandStrength.valueHighCard(hand);
		
		return interValue[8] + value;
		
	}
	
	
	// ------------------  Four of a Kind  ----------------------
	public static boolean is4Kind(ArrayList<Card> hand) {
		
		// [a a a a b]     OR      [a b b b b]

		boolean optionOne, optionTwo;
		
		optionOne = hand.get(0).getNum() == hand.get(1).getNum() && 
					hand.get(1).getNum() == hand.get(2).getNum() && 
					hand.get(2).getNum() == hand.get(3).getNum();
				
		optionTwo = hand.get(1).getNum() == hand.get(2).getNum() && 
					hand.get(2).getNum() == hand.get(3).getNum() && 
					hand.get(3).getNum() == hand.get(4).getNum();
				
		return (optionOne || optionTwo);
		
	}
	
	public static double value4Kind(ArrayList<Card> hand) {

		/** Value = 7000000 
		 * 			+ middle card
		 * 
		 * The middle card is always the one in the set of the 4 cards
		 */
		
		double value;
		
		value = hand.get(2).getNum();
		
		return interValue[7] + value;
		
	}
	
	
	// -----------------  Full House  ----------------------
	public static boolean isFullHouse(ArrayList<Card> hand) {

		// [a a a b b]     OR      [a a b b b]

		boolean optionOne, optionTwo;
				
		optionOne = hand.get(0).getNum() == hand.get(1).getNum() && 
					hand.get(1).getNum() == hand.get(2).getNum() && 
					hand.get(3).getNum() == hand.get(4).getNum();
				
		optionTwo = hand.get(0).getNum() == hand.get(1).getNum() && 
					hand.get(2).getNum() == hand.get(3).getNum() && 
					hand.get(3).getNum() == hand.get(4).getNum();
				
		return (optionOne || optionTwo);
				
	}
	
	public static double valueFullHouse(ArrayList<Card> hand) {

		/** Value = 6000000 
		 * 			+ set card
		 * 
		 * The middle card is always the one in the set
		 */
		
		double value;
		
		value = hand.get(2).getNum();
		
		return interValue[6] + value;
		
	}
	
	
	// -----------------  Flush  ----------------------
	public static boolean isFlush(ArrayList<Card> hand) {

		ArrayList<Card> tempHand = hand;
		
		HandStrength.bubbleSortBySuit(tempHand);
		
		// All cards are the same suit
		return (tempHand.get(0).getSuit() == tempHand.get(4).getSuit());
		
	}
	
	public static double valueFlush(ArrayList<Card> hand) {
		
		/** Value = 5000000 
		 * 			+ highest card in flush
		 */
		
		double value;
		
		value = HandStrength.valueHighCard(hand);
		
		return interValue[5] + value;
	
	}
	
	// -----------------  Straight  ----------------------
	public static boolean isStraight(ArrayList<Card> hand) {

		// Check straight with hands with Aces
		if (hand.get(4).getNum() == 14) {
			
			boolean optionOne, optionTwo;
			
			optionOne = hand.get(0).getNum() == 2 &&
						hand.get(1).getNum() == 3 &&
						hand.get(2).getNum() == 4 &&
						hand.get(3).getNum() == 5;
			
			optionTwo = hand.get(0).getNum() == 10 &&
						hand.get(1).getNum() == 11 &&
						hand.get(2).getNum() == 12 &&
						hand.get(3).getNum() == 13;
			
			return (optionOne || optionTwo);
			
		} else { //General Case
			
			int tempNum = hand.get(0).getNum() + 1;
			
			for (int i = 1; i < 5; i++) {
				
				if (hand.get(i).getNum() != tempNum) {
					return false;
				}
				
				tempNum++;
			}
			
			return true;
		}
	}
	
	public static double valueStraight(ArrayList<Card> hand) {

		/** Value = 4000000 
		 * 			+ highest card in straight
		 */
		
		double value;
		
		value = HandStrength.valueHighCard(hand);
		
		return interValue[4] + value;
		
	}
	
	
	// ----------------  Three of a Kind  ----------------------
	public static boolean is3Kind(ArrayList<Card> hand) {
		
		// [a a a b c]  OR  [a b b b c]  OR  [ a b c c c]
		
		boolean optionOne, optionTwo, optionThree;
		
		// Make sr
		if (HandStrength.is4Kind(hand) || HandStrength.isFullHouse(hand)) {
			return false;
		}
		
		optionOne = hand.get(0).getNum() == hand.get(1).getNum() && 
					hand.get(1).getNum() == hand.get(2).getNum();
		
		optionTwo = hand.get(1).getNum() == hand.get(2).getNum() && 
				    hand.get(2).getNum() == hand.get(3).getNum();
		
		optionThree = hand.get(2).getNum() == hand.get(3).getNum() && 
					  hand.get(3).getNum() == hand.get(4).getNum();
		
		return (optionOne || optionTwo || optionThree);
	}
	public static double value3Kind(ArrayList<Card> hand) {

		/** Value = 3000000 
		 * 			+ 3 of a Kind Card
		 * 
		 * The middle card is always the one in the set
		 */
		
		double value;
		
		value = hand.get(2).getNum();
		
		return interValue[3] + value;
		
	}
	
	
	// -----------------  Two Pair  ----------------------
	public static boolean is2Pair(ArrayList<Card> hand) {

		// [a a b b c]  OR  [a b b c c]  OR  [ a a b c c]
		
		boolean optionOne, optionTwo, optionThree;
				
		// Make sure it is not one of these hands
		if (HandStrength.is4Kind(hand) || HandStrength.isFullHouse(hand) || HandStrength.is3Kind(hand)) {
			return false;
		}
				
		optionOne = hand.get(0).getNum() == hand.get(1).getNum() && 
					hand.get(2).getNum() == hand.get(3).getNum();
				
		optionTwo = hand.get(1).getNum() == hand.get(2).getNum() && 
					hand.get(3).getNum() == hand.get(4).getNum();
				
		optionThree = hand.get(1).getNum() == hand.get(2).getNum() && 
					  hand.get(3).getNum() == hand.get(4).getNum();
				
		return (optionOne || optionTwo || optionThree);
				
	}
	public static double value2Pair(ArrayList<Card> hand) {

		double value;
		
		/** Value = 2000000 
		 * 			+ 14^2 * Highest Paired Card
		 * 			+ 14 * Lowest Paired Card
		 * 			+ Non-Paired Card
		 */
		
		if (hand.get(0).getNum() == hand.get(1).getNum() && hand.get(2).getNum() == hand.get(3).getNum()) {             // a a b b c
			
			value = Math.pow(14, 2) * hand.get(2).getNum() 
					+ 14 * hand.get(0).getNum() 
					+ hand.get(4).getNum();
			
		} else if (hand.get(0).getNum() == hand.get(1).getNum() && hand.get(3).getNum() == hand.get(4).getNum()) {      // a a b c c
			
			value = Math.pow(14, 2) * hand.get(3).getNum() 
					+ 14 * hand.get(0).getNum() 
					+ hand.get(2).getNum();
			
		} else {																										// a b b c c
			
			value = Math.pow(14, 2) * hand.get(3).getNum() 
					+ 14 * hand.get(1).getNum() 
					+ hand.get(0).getNum();
			
		}
		
		return interValue[2] + value;
		
	}
	
	
	// ----------------  One Pair  ----------------------
	public static boolean isPair(ArrayList<Card> hand) {

		boolean optionOne, optionTwo, optionThree, optionFour;
		
		if (HandStrength.is4Kind(hand) || HandStrength.isFullHouse(hand) || HandStrength.is3Kind(hand) || HandStrength.is2Pair(hand)) {
			return false;
		}
		
		optionOne = hand.get(0).getNum() == hand.get(1).getNum();
		optionTwo = hand.get(1).getNum() == hand.get(2).getNum();
		optionThree = hand.get(2).getNum() == hand.get(3).getNum();
		optionFour = hand.get(3).getNum() == hand.get(4).getNum();
		
		return (optionOne || optionTwo || optionThree || optionFour);
	
	}
	
	public static double valuePair(ArrayList<Card> hand) {
		
		double value;
		
		/** Value = 1000000 
		 * 			+ 14^3 * Paired card
		 * 			+ 14^2 * Highest Card
		 * 			+ 14 * Middle card
		 * 			+ lowest card
		 */
		
		if (hand.get(0).getNum() == hand.get(1).getNum()) {				// * * a b c
			
			value = Math.pow(14, 3) * hand.get(0).getNum() +
					Math.pow(14, 2) * hand.get(4).getNum() +
					14 * hand.get(3).getNum() +
					hand.get(2).getNum();
					
		} else if (hand.get(1).getNum() == hand.get(2).getNum()) {		// a * * b c
			
			value = Math.pow(14, 3) * hand.get(1).getNum() +
					Math.pow(14, 2) * hand.get(4).getNum() +
					14 * hand.get(3).getNum() +
					hand.get(0).getNum();
			
		} else if (hand.get(2).getNum() == hand.get(3).getNum()) {		// a b * * c
			
			value = Math.pow(14, 3) * hand.get(2).getNum() +
					Math.pow(14, 2) * hand.get(4).getNum() +
					14 * hand.get(1).getNum() +
					hand.get(0).getNum();
			
		} else {														// a b c * *
			
			value = Math.pow(14, 3) * hand.get(3).getNum() +
					Math.pow(14, 2) * hand.get(2).getNum() +
					14 * hand.get(1).getNum() +
					hand.get(0).getNum();
			
		}
		
		return interValue[1] + value;
		
	}
	
	// -------------  High Card  ----------------------
	public static double valueHighCard(ArrayList<Card> hand) {
		
		double value;
		
		/** Value = lowest card 
		 * 			+ ( 14 * 4th highest card) 
		 * 			+ ( 14^2 * 3rd highest card) 
		 * 			+ ( 14^3 * 2nd highest card) 
		 * 			+ ( 14^4 * highest card)
		 * 
		 */
		
		value = hand.get(0).getNum() + (14 * hand.get(1).getNum()) + (Math.pow(14, 2) * hand.get(2).getNum())
				+ (Math.pow(14, 3) * hand.get(3).getNum()) + (Math.pow(14, 4) * hand.get(4).getNum());
		
		return interValue[0] + value;
		
	}
	
	
	// --------------------- Sorting -------------------------
	public static void bubbleSort(ArrayList<Card> array) {
		
		int n = array.size();
		int k;
		
		for (int m = n; m >= 0; m--) {
			
			for (int i = 0; i < n - 1; i++) {
				
				k = i + 1;
				
				if (array.get(i).getNum() > array.get(k).getNum()) {
					swapNumbers(i, k, array);
				}
			}
		}
	}
	
	public static void bubbleSortBySuit(ArrayList<Card> array) {
		
		int n = array.size();
		int k;
		
		for (int m = n; m >= 0; m--) {
			
			for (int i = 0; i < n - 1; i++) {
				
				k = i + 1;
				
				if (array.get(i).getSuit() > array.get(k).getSuit()) {
					swapNumbers(i, k, array);
				}
			}
		}
	}
	
	public static void swapNumbers(int i, int j, ArrayList<Card> array) {
		Card temp;
		temp = array.get(i);
		array.set(i, array.get(j));
		array.set(j, temp);
	}

}
