package eu.uniroma1.model.exceptions;

/**
 * Deck finished exception class
 */
public class DeckFinishedException extends Exception
{
	/**
	 * Deck finished exception builder 
	 */
	public DeckFinishedException()
	{
		super("The deck is finished!");
	}
}
