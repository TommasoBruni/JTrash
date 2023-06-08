package eu.uniroma1.model.exceptions;

public class PartitaNonInCorsoException extends Exception
{
	public PartitaNonInCorsoException()
	{
		super("La partita non è ancora iniziata!");
	}
}
