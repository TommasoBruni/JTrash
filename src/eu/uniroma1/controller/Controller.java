package eu.uniroma1.controller;

import eu.uniroma1.model.Database;

public class Controller 
{
	private int numeroGiocatori;
	private Database databaseGicoatori;
	
	public void aggiornaNumeroGiocatori(int nGiocatori)
	{
		numeroGiocatori = nGiocatori;
	}
	
	public Controller()
	{
		databaseGicoatori = new Database();
	}
}
