package eu.uniroma1.controller;

import java.util.List;

import javax.swing.Icon;

import eu.uniroma1.model.Database;
import eu.uniroma1.model.Giocatore;

public class Controller 
{
	private int numeroGiocatori;
	private Database databaseGicoatori;
	
	/**
	 * Aggiorna i giocatori che attualmente stanno giocando (2, 3 o 4) 
	 * @param nGiocatori numero di giocatori attualmente in gioco
	 */
	public void aggiornaNumeroGiocatori(int nGiocatori)
	{
		numeroGiocatori = nGiocatori;
	}
	
	/**
	 * Aggiunge il giocatore alla lista dei giocatori che hanno giocato finora
	 * @param nomeGiocatore nome giocatore 
	 * @param nickname nickname giocatore
	 * @param avatar avatar giocatore
	 */
	public void aggiungiGiocatore(String nomeGiocatore, String nickname, Icon avatar)
	{
		databaseGicoatori.add(new Giocatore(nomeGiocatore, nickname, avatar));
	}
	
	public List<Giocatore> getListaGiocatori()
	{
		return databaseGicoatori.getGiocatori();
	}
	
	public Controller()
	{
		databaseGicoatori = new Database();
	}
}
