package eu.uniroma1.controller;

import java.util.List;

import javax.swing.Icon;

import eu.uniroma1.model.Database;
import eu.uniroma1.model.Giocatore;

public class Controller 
{
	private int numeroGiocatoriInPartita;
	private Database databaseGiocatori;
	
	/**
	 * Aggiorna i giocatori che attualmente stanno giocando (2, 3 o 4) 
	 * @param nGiocatori numero di giocatori attualmente in gioco
	 */
	public void aggiornaNumeroGiocatori(int nGiocatori)
	{
		numeroGiocatoriInPartita = nGiocatori;
	}
	
	/**
	 * Restituisce il numero di giocatori in partita
	 * @return numero giocatori attualmente in partita 
	 */
	public int getNumeroGiocatoriInPartita()
	{
		return numeroGiocatoriInPartita;
	}
	
	/**
	 * Aggiunge il giocatore alla lista dei giocatori che hanno giocato finora
	 * @param nomeGiocatore nome giocatore 
	 * @param nickname nickname giocatore
	 * @param avatar avatar giocatore
	 */
	public void aggiungiGiocatore(String nomeGiocatore, String nickname, Icon avatar)
	{
		databaseGiocatori.add(new Giocatore(nomeGiocatore, nickname, avatar));
	}
	
	/**
	 * Ritorna il nome dell'ultimo giocatore, ovvero quello che sta giocando
	 * @return Nome ultimo giocatore 
	 */
	public String getNomeUltimoGiocatore()
	{
		return databaseGiocatori.getGiocatori().get(databaseGiocatori.getGiocatori().size() - 1).getNome();
	}
	
	public List<Giocatore> getListaGiocatori()
	{
		return databaseGiocatori.getGiocatori();
	}
	
	public Controller()
	{
		databaseGiocatori = new Database();
	}
}
