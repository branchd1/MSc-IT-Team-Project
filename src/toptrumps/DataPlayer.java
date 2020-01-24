package toptrumps;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 * 
 * This class represents a player
 * 
 * @author Team TRY-CATCH - Arnold Umakhihe 2445734U Team
 *
 */
public class DataPlayer {
	/**
	 * enum to represents the different types of player
	 * @author salistechltd
	 */
	enum PlayerType {
		AI, HUMAN
	}

	/** static int to keep track of AI player count */
	private static int ARTIFICIAL_INTELLIGENCE_ID = 1;

	/** DataCard array representing the deck of cards */
	private ArrayList<DataCard> cardDeck = new ArrayList<DataCard>();

	/** PlayerType enum representing the type of player - human or AI */
	private PlayerType type;

	/** integer representing the player's current score */
	private int score;

	/** String representing the player's name */
	private String name;

	/**
	 * creates a new player
	 * @param type PlayerType enum representing the type of player
	 * @param numberOfArtificialIntelligencePlayers represents the number of AI players
	 */
	public DataPlayer(PlayerType type, int numberOfArtificialIntelligencePlayers) {
		this.type = type; //set type
		this.score = 0;
		
		// name players based on type
		if (this.type == PlayerType.HUMAN) {
			this.name = "You";
		} else if (this.type == PlayerType.AI) {
			this.name = "AI Player " + ARTIFICIAL_INTELLIGENCE_ID++; // name AI players using static ID based on the number
			this.resetArtificialIntelligenceId(numberOfArtificialIntelligencePlayers);
		}
	}
	
	/**
	 * reset static ID used to name AI players
	 * @param numberOfArtificialIntelligencePlayers number of AI players
	 */
	private void resetArtificialIntelligenceId(int numberOfArtificialIntelligencePlayers){
		// if ID is higher than no of AI players, reset back to 1
		if(DataPlayer.ARTIFICIAL_INTELLIGENCE_ID>numberOfArtificialIntelligencePlayers) {
			DataPlayer.ARTIFICIAL_INTELLIGENCE_ID = 1;
		}
	}
	
//	ArrayList<DataCard> createRandomDeck(ArrayList<DataCard> deck) {
//		ArrayList<DataCard> completeDeck = deck;
//		ArrayList<DataCard> cardDeck = new ArrayList<DataCard>(Collections.nCopies(7, null));
//		ArrayList<Integer> randomNumbers = new ArrayList<Integer>();
//
//		Random random = new Random();
//		for(int i=0;i<cardDeck.size();i++) {
//			int randomNumber;
//			do {
//				randomNumber = random.nextInt(completeDeck.size());
//			} while(randomNumbers.contains(randomNumber));
//			
//			cardDeck.remove(i);
//			cardDeck.add(i, completeDeck.get(randomNumber));
//			completeDeck.remove(randomNumber);
//			
//			randomNumbers.add(randomNumber);
//		}
//		
//		return cardDeck;
//	}
	
	/**
	 * 
	 * @param newCards
	 */
	protected void addCardsToDeck(ArrayList<DataCard> newCards) {
		for(DataCard card : newCards) {
			this.cardDeck.add(card);
		}
	}
	
	void addCardToDeck(DataCard newCard) {
		this.cardDeck.add(newCard);
	}
	
	void removeCardsFromDeck(ArrayList<DataCard> cardsToRemove) {
		for(DataCard card : cardsToRemove) {
			this.cardDeck.remove(card);
		}
	}
	
	void removeCardFromDeck(DataCard cardToRemove) {
		this.cardDeck.remove(cardToRemove);
	}
	
	void removeTopCardFromDeck() {
		this.cardDeck.remove(0);
	}
	
	void setDeck(ArrayList<DataCard> deck) {
		this.cardDeck = deck;
	}

	// GETTER METHODS START

	int getScore() {
		return this.score;
	}

	PlayerType getType() {
		return this.type;
	}
	
	String getTypeAsString() {
		if(this.type == PlayerType.AI) {
			return "ai";
		} else if (this.type == PlayerType.HUMAN) {
			return "human";
		} else {
			return null;
		}
	}

	String getName() {
		return this.name;
	}
	
	ArrayList<DataCard> getDeck() {
		return this.cardDeck;
	}
	
	int getNumberOfCardsInDeck() {
		return this.cardDeck.size();
	}

	// GETTER METHODS END

	void incrementScore() {
		this.score += 1;
	}

}
