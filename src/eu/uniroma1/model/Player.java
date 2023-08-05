package eu.uniroma1.model;

import java.awt.Image;
import java.io.Serializable;

import javax.swing.Icon;
import javax.swing.ImageIcon;

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
		this(name, nickname, avatar, 0, 0);
	}
	
	/**
	 * Player builder.
	 * @param name name of player
	 * @param nickname nickname of player.
	 * @param avatar avatar of player.
	 * @param matchesWon matches won
	 * @param matchesLost matches lost.
	 */
	public Player(String nome, String nickname, ImageIcon avatar, 
			      long matchesWon, long matchesLost)
	{
		this.name = nome;
		this.nickname = nickname;
		this.avatar = avatar;
		this.level = Level.BEGINNER;
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
	 * Set level of player.
	 * @param level level of the player. 
	 */
	public void setLevel(Level level) {
		this.level = level;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public long getPartiteVinte() {
		return matchesWon;
	}

	public void increasePlayerWonGames()
	{
		this.matchesWon++;
	}

	public long getPartitePerse() {
		return matchesLost;
	}

	public void increasePlayerLostGames()
	{
		this.matchesLost++;
	}

	public ImageIcon getAvatar() {
		return avatar;
	}

	public void setAvatar(ImageIcon avatar) {
		this.avatar = avatar;
	}
}
