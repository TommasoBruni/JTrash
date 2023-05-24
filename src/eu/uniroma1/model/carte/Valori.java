package eu.uniroma1.model.carte;

public enum Valori
{
	ASSO("asso"),
	DUE("due"),
	TRE("tre"),
	QUATTRO("tre"),
	CINQUE("tre"),
	SEI("tre"),
	SETTE("tre"),
	OTTO("tre"),
	NOVE("tre"),
	DIECI("tre"),
	JACK("tre"),
	QUEEN("queen"),
	KING("king");

	String nome;
	
	Valori(String nome) 
	{
		this.nome=nome;
	}
	
	public String toString()
	{
		return nome;
	}
}
