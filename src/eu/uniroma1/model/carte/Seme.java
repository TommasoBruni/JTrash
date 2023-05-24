package eu.uniroma1.model.carte;

/**
 * Enumerato dei semi 
 */
public enum Seme
{
	CUORI(), 
	QUADRI("quadri"), 
	FIORI("fiori"), 
	PICCHE("picche");
	
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
	 * Costruttore enumerato seme
	 */
	Seme(String nome)
	{
		this.nome=nome;
	}
	
	/**
	 * Costruttore enumerato seme
	 */
	Seme()
	{
		this("cuori");
	}
}
