package eu.uniroma1.model.exceptions;

public class GameNotInProgressException extends Exception
{
	public GameNotInProgressException()
	{
		super("La partita non è ancora iniziata!");
	}
}
