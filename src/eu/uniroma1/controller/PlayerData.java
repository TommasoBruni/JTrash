package eu.uniroma1.controller;

import java.util.List;
import java.util.Observable;

import javax.swing.ImageIcon;

import eu.uniroma1.model.Player;

public class PlayerData extends Observable
{
	private Player giocatore;
	
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
		if (giocatore == null)
			giocatore = new Player(nomeGiocatore, nickname, avatar);
		else
			giocatore = new Player(nomeGiocatore, nickname, avatar, giocatore.getPartiteVinte(), giocatore.getPartitePerse());
		
		setChanged();
		notifyObservers(giocatore);
	}
}
