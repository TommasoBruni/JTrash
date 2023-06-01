package eu.uniroma1.model.eccezioni;

public class MazzoFinitoException extends Exception
{
	public MazzoFinitoException()
	{
		super("Il mazzo è finito!");
	}
}
