package eu.uniroma1.model.exceptions;

public class GameNotInProgressException extends Exception
{
	public GameNotInProgressException()
	{
		super("The game is not started yet!");
	}
}
