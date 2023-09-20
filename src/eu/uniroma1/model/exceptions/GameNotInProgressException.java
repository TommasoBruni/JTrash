package eu.uniroma1.model.exceptions;

/**
 * Game not in progress exception class
 */
public class GameNotInProgressException extends Exception
{
	/**
	 * Game not in progress exception class builder 
	 */
	public GameNotInProgressException()
	{
		super("The game is not started yet!");
	}
}
