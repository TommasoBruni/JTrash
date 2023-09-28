package eu.uniroma1.model.cards;

/**
 * Suits enumerate
 */
public enum Suit
{
	/**
	 * No suit 
	 */
	NOTHING(),
	/**
	 * Hearts suit 
	 */
	HEARTS("♥"), 
	/**
	 * Diamonds suit 
	 */
	DIAMONDS("♦"),
	/**
	 * Clubs suit 
	 */
	CLUBS("♣"),
	/**
	 * Spades suit
	 */
	SPADES("♠");
	
	@Override
	public String toString() 
	{
		return symbol;
	}
	
	/**
	 * Symbol of the suit
	 */
	String symbol;

	/**
	 * Builder of the enumerate
	 */
	Suit(String symbol)
	{
		this.symbol = symbol;
	}
	
	/**
	 * Builder of the enumerate
	 */
	Suit()
	{
		this("");
	}
}
