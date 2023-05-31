package eu.uniroma1.model;

import java.awt.Image;

import javax.swing.Icon;
import javax.swing.ImageIcon;

public class Giocatore 
{
	private Livello livello;
	private String nome;
	private String nickname;
	private long partiteVinte;
	private long partitePerse;
	private ImageIcon avatar;
	
	public Giocatore(String nome, String nickname, ImageIcon avatar)
	{
		this.nome = nome;
		this.nickname = nickname;
		this.avatar = avatar;
		this.livello = Livello.PRINCIPIANTE;
	}

	public Livello getLivello() {
		return livello;
	}

	public void setLivello(Livello livello) {
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

	public void setPartiteVinte(long partiteVinte) {
		this.partiteVinte = partiteVinte;
	}

	public long getPartitePerse() {
		return partitePerse;
	}

	public void setPartitePerse(long partitePerse) {
		this.partitePerse = partitePerse;
	}

	public ImageIcon getAvatar() {
		return avatar;
	}

	public void setAvatar(ImageIcon avatar) {
		this.avatar = avatar;
	}
}
