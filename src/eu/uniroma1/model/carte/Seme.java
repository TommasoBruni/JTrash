package eu.uniroma1.model.carte;

/**
 * Enumerato dei semi 
 */
public enum Seme
{
	CUORI(), 
	QUADRI("quadri", "♦"), 
	FIORI("fiori", "♣"), 
	PICCHE("picche", "♠");
	
	@Override
	public String toString() 
	{
		return nome;
	}
	
	/**
	 * Nome del seme
	 */
	String nome;
	
	/**
	 * Simbolo del seme 
	 */
	String simbolo;

	/**
	 * Costruttore enumerato seme
	 */
	Seme(String nome, String simbolo)
	{
		this.nome=nome;
		this.simbolo = simbolo;
	}
	
	/**
	 * Costruttore enumerato seme
	 */
	Seme()
	{
		this("cuori", "♥");
	}
}
