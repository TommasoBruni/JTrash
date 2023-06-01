package eu.uniroma1.model.carte;

/**
 * Enumerato dei semi 
 */
public enum Seme
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
	Seme(String simbolo)
	{
		this.simbolo = simbolo;
	}
	
	/**
	 * Costruttore enumerato seme
	 */
	Seme()
	{
		this("");
	}
}
