package eu.uniroma1.controller;

import java.io.*;
import java.util.List;
import java.util.Observable;

import javax.swing.ImageIcon;

import eu.uniroma1.model.Level;
import eu.uniroma1.model.Player;

/**
 * Player data class
 */
public class PlayerData extends Observable
{
	private Player player;
	private String filename;
	
	/**
	 * Check if the data are consistent.
	 * @return result of the check. 
	 */
	public boolean isEmptyData()
	{
		return player == null;
	}
	
	/**
	 * Get name of the player.
	 * @return get name of the player. 
	 */
	public String getPlayerName()
	{
		return player.getName();
	}
	
	/**
	 * Get level of the player.
	 * @return {@link Level} of the player. 
	 */
	public Level getLevel()
	{
		return player.getLevel();
	}
	
	/**
	 * Get nickname of the player.
	 * @return nickname of the player. 
	 */
	public String getPlayerNickname()
	{
		return player.getNickname();
	}
	
	/**
	 * Get avatar of the player.
	 * @return avatar of the player. 
	 */
	public ImageIcon getPlayerAvatar()
	{
		return player.getAvatar();
	}
	
	/**
	 * Get the amount of games played by the player.
	 * @return games played by the player.
	 */
	public long getPlayedGames()
	{
		return player.getWonMatches() + player.getPartitePerse();
	}
	
	/**
	 * Get the games won by the player.
	 * @return games won by the player. 
	 */
	public long getWonGames()
	{
		return player.getWonMatches();
	}
	
	/**
	 * Get the games lost by the player.
	 * @return games lost by the player.
	 */
	public long getLostGames()
	{
		return player.getPartitePerse();
	}
	
	/**
	 * Increase player won games.
	 */
	public void increasePlayerWonGames()
	{
		player.increasePlayerWonGames();
	}
	
	/**
	 * Increase player lost games. 
	 */
	public void increasePlayerLostGames()
	{
		player.increasePlayerLostGames();
	}
	
	private void save()
	{
		ObjectOutputStream os = null;
		try 
		{
		    File myObj = new File(filename); 
		    
		    myObj.delete();
			
			FileOutputStream fos = new FileOutputStream(filename);
			
			os = new ObjectOutputStream(fos);
			
			os.writeObject(player);
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		finally
		{
			if (os != null)
			{
				try 
				{
					os.close();
				} 
				catch (IOException e) 
				{
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * Set filename where to save player data.
	 * @param filename where to save player data.
	 */
	public void setFilename(String filename)
	{
		this.filename = filename;
	}
	
	/**
	 * Player data builder.
	 */
	public PlayerData()
	{
	}
	
	private PlayerData(Player player)
	{
		this.player = player;
	}
	
	/**
	 * Update player data.
	 * @param name name of the player
	 * @param nickname nickname of the player
	 * @param avatar avatar of the player
	 */
	public void updatePlayerData(String name, String nickname, ImageIcon avatar)
	{
		if (player == null)
			player = new Player(name, nickname, avatar);
		else
			player = new Player(name, nickname, avatar, player.getWonMatches(), player.getPartitePerse());
		
		if (filename != null)
			save();
		
		setChanged();
		notifyObservers(player);
	}
	
	/**
	 * Method to read the serialized player data.
	 * @return player data built from the file.
	 */
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
