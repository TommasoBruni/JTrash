package eu.uniroma1.controller;

import java.io.*;
import java.util.List;
import java.util.Observable;

import javax.swing.ImageIcon;

import eu.uniroma1.model.Player;

public class PlayerData extends Observable
{
	private Player giocatore;
	private String filename;
	
	public boolean isEmptyData()
	{
		return giocatore == null;
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
	
	private void save()
	{
		try 
		{
		    File myObj = new File(filename); 
		    
		    myObj.delete();
			
			FileOutputStream fos = new FileOutputStream(filename);
			
			ObjectOutputStream os = new ObjectOutputStream(fos);
			
			os.writeObject(giocatore);
			
			os.close();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
	
	public void setFilename(String filename)
	{
		this.filename = filename;
	}
	
	public PlayerData()
	{
	}
	
	private PlayerData(Player player)
	{
		this.giocatore = player;
	}
	
	public void aggiornaDatiGiocatore(String nomeGiocatore, String nickname, ImageIcon avatar)
	{
		if (giocatore == null)
			giocatore = new Player(nomeGiocatore, nickname, avatar);
		else
			giocatore = new Player(nomeGiocatore, nickname, avatar, giocatore.getPartiteVinte(), giocatore.getPartitePerse());
		
		if (filename != null)
			save();
		
		setChanged();
		notifyObservers(giocatore);
	}
	
	public static PlayerData read(String filename)
	{
		FileInputStream fis = null;
		ObjectInputStream is = null;
		
        try
        {
        	fis = new FileInputStream(filename);
        	is = new ObjectInputStream(fis);
        	Player player = (Player)is.readObject();
            
            return new PlayerData(player);             
        }
        catch(ClassNotFoundException e)
        {
             e.printStackTrace();
        }
        catch(IOException ex)
        {
        	/* Nothing to do, return null */
        }
        finally
        {
        	if (is != null)
        	{
				try 
				{
					/* this call also closes the fis */
					is.close();
				} 
				catch (IOException e) 
				{
					/* Nothing to do */
				}
        	}
        }
        return null;
	}
}
