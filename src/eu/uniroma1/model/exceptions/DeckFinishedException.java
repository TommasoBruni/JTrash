package eu.uniroma1.model.exceptions;

public class DeckFinishedException extends Exception
{
	public DeckFinishedException()
	{
		super("The deck is finished!");
	}
}
