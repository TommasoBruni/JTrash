package eu.uniroma1.model;

import java.util.ArrayList;
import java.util.List;

public class Database 
{
	private List<Giocatore> giocatori;
	
	public void add(Giocatore giocatore)
	{
		giocatori.add(giocatore);
	}
	
	public void modificaDatiUltimoGiocatore(Giocatore newGiocatore)
	{
		giocatori.remove(giocatori.size() - 1);
		giocatori.add(newGiocatore);
	}
	
	public List<Giocatore> getGiocatori()
	{
		return giocatori;
	}
	
	public Database()
	{
		giocatori = new ArrayList<>();
	}
}
