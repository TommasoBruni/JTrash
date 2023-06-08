package eu.uniroma1.model.carte;

public enum Value
{
	ASSO("A"),
	DUE("2"),
	TRE("3"),
	QUATTRO("4"),
	CINQUE("5"),
	SEI("6"),
	SETTE("7"),
	OTTO("8"),
	NOVE("9"),
	DIECI("10"),
	JACK("J"),
	QUEEN("Q"),
	KING("K"),
	JOLLY("Jolly");

	String nome;
	
	public String toString()
	{
		return nome;
	}
	
	Value(String nome) 
	{
		this.nome=nome;
	}
}
