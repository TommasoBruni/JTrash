package eu.uniroma1.model;

import java.awt.Image;
import java.io.Serializable;

import javax.swing.Icon;
import javax.swing.ImageIcon;

/**
 * Player class 
 */
public class Player implements Serializable
{
	/**
	 * Serial version UID
	 */
	private static final long serialVersionUID = 1L;
	private Level level;
	private String name;
	private String nickname;
	private long matchesWon;
	private long matchesLost;
	private ImageIcon avatar;
	
	/**
	 * Player builder.
	 * @param name name of player
	 * @param nickname nickname of player.
	 * @param avatar avatar of player.
	 */
	public Player(String name, String nickname, ImageIcon avatar)
	{
		this(name, nickname, avatar, 0, 0, Level.BEGINNER);
	}
	
	/**
	 * Player builder.
	 * @param name name of player
	 * @param nickname nickname of player.
	 * @param avatar avatar of player.
	 * @param matchesWon matches won
	 * @param matchesLost matches lost.
	 * @param level level of the player
	 */
	public Player(String name, String nickname, ImageIcon avatar, 
			      long matchesWon, long matchesLost, Level level)
	{
		this.name = name;
		this.nickname = nickname;
		this.avatar = avatar;
		this.level = level;
		this.matchesWon = matchesWon;
		this.matchesLost = matchesLost;
	}

	/**
	 * Get level of player.
	 * @return {@link Level} level of the player. 
	 */
	public Level getLevel() 
	{
		return level;
	}

	/**
	 * Upgrade level of the player 
	 */
	public void upgradeLevel()
	{
		if (level == Level.BEGINNER)
			level = Level.INTERMEDIATE;
		else if (level == Level.INTERMEDIATE)
			level = Level.ADVANCED;
	}

	/**
	 * Get name of the player
	 * @return name of the player
	 */
	public String getName() 
	{
		return name;
	}

	/**
	 * Set name of the player
	 * @param name new name of the player 
	 */
	public void setName(String name) 
	{
		this.name = name;
	}

	/**
	 * Get nickname of the player.
	 * @return nickname of the player
	 */
	public String getNickname() 
	{
		return nickname;
	}

	/**
	 * Set nickname of the player
	 * @param nickname new nickname of the player
	 */
	public void setNickname(String nickname)
	{
		this.nickname = nickname;
	}

	/**
	 * Get won matches of the player
	 * @return number of won matches of the player 
	 */
	public long getWonMatches() 
	{
		return matchesWon;
	}

	/**
	 * Increase the number of won matches of the player
	 */
	public void increasePlayerWonGames()
	{
		this.matchesWon++;
	}

	/**
	 * Get lost matches of the player
	 * @return the number of lost matches of the player 
	 */
	public long getLostMatches()
	{
		return matchesLost;
	}

	/**
	 * Increase the number of lost matches of the player
	 */
	public void increasePlayerLostGames()
	{
		this.matchesLost++;
	}

	/**
	 * Get avatar of the player
	 * @return the avatar of the player
	 */
	public ImageIcon getAvatar()
	{
		return avatar;
	}

	/**
	 * Set avatar of the player
	 * @param avatar new avatar of the player
	 */
	public void setAvatar(ImageIcon avatar) 
	{
		this.avatar = avatar;
	}
}
