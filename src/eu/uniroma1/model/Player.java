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
	
	public Player(String nome, String nickname, ImageIcon avatar)
	{
		this(nome, nickname, avatar, 0, 0);
	}
	
	public Player(String nome, String nickname, ImageIcon avatar, 
			      long partiteVinte, long partitePerse)
	{
		this.name = nome;
		this.nickname = nickname;
		this.avatar = avatar;
		this.level = Level.PRINCIPIANTE;
		this.matchesWon = partiteVinte;
		this.matchesLost = partitePerse;
	}

	public Level getLevel() {
		return level;
	}

	public void setLevel(Level level) {
		this.level = level;
	}

	public String getNome() {
		return name;
	}

	public void setNome(String nome) {
		this.name = nome;
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

	public void incrementaPartiteVinte()
	{
		this.matchesWon++;
	}

	public long getPartitePerse() {
		return matchesLost;
	}

	public void incrementaPartitePerse()
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
