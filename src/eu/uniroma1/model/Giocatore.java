package eu.uniroma1.model;

import javax.swing.Icon;

public class Giocatore 
{
	private Livello livello;
	private String nome;
	private String nickname;
	private long partiteVinte;
	private long partitePerse;
	private Icon avatar;
	
	public Giocatore(String nome, String nickname, Icon avatar)
	{
		this.nome = nome;
		this.nickname = nickname;
		this.avatar = avatar;
		livello = Livello.PRINCIPIANTE;
	}
}
