package eu.uniroma1.model.cards;

/**
 * Card value enumerate
 */
public enum Value
{
	ACE("A"),
	TWO("2"),
	THREE("3"),
	FOUR("4"),
	FIVE("5"),
	SIX("6"),
	SEVEN("7"),
	EIGHT("8"),
	NINE("9"),
	TEN("10"),
	JACK("J"),
	QUEEN("Q"),
	KING("K"),
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
