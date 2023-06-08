package eu.uniroma1.model.carte;

/**
 * Enumerato dei semi 
 */
public enum Suit
{
	NESSUNO(),
	CUORI("♥"), 
	QUADRI("♦"), 
	FIORI("♣"), 
	PICCHE("♠");
	
	@Override
	public String toString() 
	{
		return simbolo;
	}
	
	/**
	 * Simbolo del seme 
	 */
	String simbolo;

	/**
	 * Costruttore enumerato seme
	 */
	Suit(String simbolo)
	{
		this.simbolo = simbolo;
	}
	
	/**
	 * Costruttore enumerato seme
	 */
	Suit()
	{
		this("");
	}
}
