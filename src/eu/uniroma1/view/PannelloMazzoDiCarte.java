package eu.uniroma1.view;

import java.awt.Dimension;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

public class PannelloMazzoDiCarte extends JPanel
{
	private JButton carteDaPescare;
	private JButton carteScartate;
	
	public PannelloMazzoDiCarte()
	{
		ImageIcon icon;
		carteDaPescare = new JButton();
		
		try
		{
			/* Leggo l'immagine salvata nella directory "resources" dentro il progetto */
			icon = new ImageIcon(System.getProperty("user.dir").concat("\\resources\\carta_da_gioco.jpg"));
		} 
		catch (Exception ex) 
		{
			/* Lanciare un'altra eccezione */
		    throw ex;
		}
		carteDaPescare.setIcon(icon);
		carteDaPescare.setPreferredSize(new Dimension(30, 40));
		add(carteDaPescare);
	}
}
