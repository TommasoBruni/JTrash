package eu.uniroma1.view.border;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Insets;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.border.Border;

import eu.uniroma1.model.Giocatore;

public class BordoPannelloGiocatore implements javax.swing.border.Border
{
	private Border bordoInterno;
	private Border bordoEsterno;
	private Border bordoComposto;
	
	@Override
	public void paintBorder(Component c, Graphics g, int x, int y, int width, int height)
	{
		bordoComposto.paintBorder(c, g, x, y, width, height);
	}

	@Override
	public Insets getBorderInsets(Component c) 
	{
		return bordoComposto.getBorderInsets(c);
	}

	@Override
	public boolean isBorderOpaque() 
	{
		return bordoComposto.isBorderOpaque();
	}
		
	public BordoPannelloGiocatore(String nomeGiocatore)
	{
		bordoInterno = BorderFactory.createTitledBorder(nomeGiocatore);
		bordoEsterno = BorderFactory.createEmptyBorder(0, 0, 0, 0);
		bordoComposto = BorderFactory.createCompoundBorder(bordoEsterno, bordoInterno);
	}
}
