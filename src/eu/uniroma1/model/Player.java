package eu.uniroma1.model;

import java.awt.Image;

import javax.swing.Icon;
import javax.swing.ImageIcon;

public class Player 
{
	private static int idCounter;
	private int id;
	private Level livello;
	private String nome;
	private String nickname;
	private long partiteVinte;
	private long partitePerse;
	private ImageIcon avatar;
	
	public Player(String nome, String nickname, ImageIcon avatar)
	{
		this(nome, nickname, avatar, 0, 0);
	}
	
	public Player(String nome, String nickname, ImageIcon avatar, 
			      long partiteVinte, long partitePerse)
	{
		this.nome = nome;
		this.nickname = nickname;
		this.avatar = avatar;
		this.livello = Level.PRINCIPIANTE;
		this.partiteVinte = partiteVinte;
		this.partitePerse = partitePerse;
		id = idCounter++;
	}
	
	public int getId() {
		return id;
	}

	public Level getLivello() {
		return livello;
	}

	public void setLivello(Level livello) {
		this.livello = livello;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public long getPartiteVinte() {
		return partiteVinte;
	}

	public void incrementaPartiteVinte()
	{
		this.partiteVinte++;
	}

	public long getPartitePerse() {
		return partitePerse;
	}

	public void incrementaPartitePerse()
	{
		this.partitePerse++;
	}

	public ImageIcon getAvatar() {
		return avatar;
	}

	public void setAvatar(ImageIcon avatar) {
		this.avatar = avatar;
	}
}
