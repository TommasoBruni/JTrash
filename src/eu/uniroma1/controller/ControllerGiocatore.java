package eu.uniroma1.controller;

import java.util.List;
import java.util.Observable;

import javax.swing.ImageIcon;

import eu.uniroma1.model.Giocatore;

public class ControllerGiocatore extends Observable
{
	private Giocatore giocatore;
	private int numeroGiocatoriInPartita;
	private static ControllerGiocatore controllerGiocatori;
	
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
	 * Ritorna il nome dell'ultimo giocatore, ovvero quello che sta giocando
	 * @return Nome ultimo giocatore 
	 */
	public String getNomeGiocatore()
	{
		return giocatore.getNome();
	}
	
	public String getNicknameGiocatore()
	{
		return giocatore.getNickname();
	}
	
	public ImageIcon getAvatarGiocatore()
	{
		return giocatore.getAvatar();
	}
	
	public long getPartiteGiocateGiocatore()
	{
		return giocatore.getPartiteVinte() + giocatore.getPartitePerse();
	}
	
	public long getPartiteVinteGiocatore()
	{
		return giocatore.getPartiteVinte();
	}
	
	public long getPartitePerseGiocatore()
	{
		return giocatore.getPartitePerse();
	}
	
	public void aumentaPartiteVinteGiocatore()
	{
		giocatore.incrementaPartiteVinte();
	}
	
	public void aumentaPartitePerseGiocatore()
	{
		giocatore.incrementaPartitePerse();
	}
	
	public void aggiornaDatiGiocatore(String nomeGiocatore, String nickname, ImageIcon avatar)
	{
		giocatore = new Giocatore(nomeGiocatore, nickname, avatar);
		
		setChanged();
		notifyObservers(giocatore);
	}
	
	public static ControllerGiocatore getInstance()
	{
		if (controllerGiocatori == null)
			controllerGiocatori = new ControllerGiocatore();
		return controllerGiocatori;
	}
	
	private ControllerGiocatore()
	{
	}
}
