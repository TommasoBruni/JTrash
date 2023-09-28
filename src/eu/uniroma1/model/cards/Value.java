package eu.uniroma1.model.cards;

/**
 * Card value enumerate
 */
public enum Value
{
	/**
	 * Ace value
	 */
	ACE("A"),
	/**
	 * Two value
	 */
	TWO("2"),
	/**
	 * Three value
	 */
	THREE("3"),
	/**
	 * Four value
	 */
	FOUR("4"),
	/**
	 * Five value
	 */
	FIVE("5"),
	/**
	 * Six value
	 */
	SIX("6"),
	/**
	 * Seven value
	 */
	SEVEN("7"),
	/**
	 * Eight value
	 */
	EIGHT("8"),
	/**
	 * Nine value
	 */
	NINE("9"),
	/**
	 * Ten value
	 */
	TEN("10"),
	/**
	 * Jack value
	 */
	JACK("J"),
	/**
	 * Queen value
	 */
	QUEEN("Q"),
	/**
	 * King value
	 */
	KING("K"),
	/**
	 * Jolly value
	 */
	JOLLY("Jolly");

	String name;
	
	@Override
	public String toString()
	{
		return name;
	}
	
	/**
	 * Card value builder 
	 */
	Value(String name) 
	{
		this.name = name;
	}
}
