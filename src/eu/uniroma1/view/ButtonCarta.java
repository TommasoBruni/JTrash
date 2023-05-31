package eu.uniroma1.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.geom.AffineTransform;
import java.awt.Graphics2D;

public class ButtonCarta extends JButton
{
	private ImageIcon icon;
	private String valoreCarta;
	private static final String fileCartaGiocoVerticale = "carta_da_gioco_verticale.jpg";
	private static final String fileCartaGiocoOrizzontale = "carta_da_gioco_orizzontale.jpg";
	private boolean isScoperta;
	private boolean isHorizontal;
	
	@Override
	public void paint(Graphics g) 
	{
		super.paint(g);
		if (isScoperta)
		{
			g.setColor(Color.WHITE);
			g.fillRect(0, 0, getWidth(), getHeight());
	        g.setColor(Color.BLACK);
	        g.drawRect(0, 0, getWidth() - 1, getHeight() - 1);
	        
	        if (valoreCarta.startsWith("♦") || valoreCarta.startsWith("♥"))
	        	g.setColor(Color.RED);
	        Font font = new Font("Arial", Font.BOLD, 24);
	        
			if (isHorizontal)
			{
				AffineTransform affineTransform = new AffineTransform();
				affineTransform.rotate(155);
				//affineTransform.rotate(150);
				//affineTransform.rotate(Math.toRadians(330), 5, 25);
				Font rotatedFont = font.deriveFont(affineTransform);
				g.setFont(rotatedFont);
				g.drawString(valoreCarta, 27, 30);
			}
			else
			{
		        g.setFont(font);
		        g.drawString(valoreCarta, 5, 25);
			}
		}
	}
	
	public void gira()
	{
		if (!isScoperta)
		{
			setIcon(null);
			isScoperta = true;
		}
		else
		{
			setIcon(icon);
			isScoperta = false;
		}
		
		repaint();
	}

	public ButtonCarta(boolean isHorizontal, String valoreCarta)
	{
		super();
		this.valoreCarta = valoreCarta;
		this.isHorizontal = isHorizontal;
		try
		{
			/* Leggo l'immagine salvata nella directory "resources" dentro il progetto */
			icon = new ImageIcon(System.getProperty("user.dir").
						concat("\\resources\\" + (isHorizontal ? fileCartaGiocoOrizzontale : fileCartaGiocoVerticale)));
		} 
		catch (Exception ex) 
		{
			/* Lanciare un'altra eccezione */
		    throw ex;
		}
		
		setIcon(icon);
		setPreferredSize(new Dimension(icon.getIconWidth(), icon.getIconHeight()));
		setMaximumSize(new Dimension(icon.getIconWidth(), icon.getIconHeight()));
		setMinimumSize(new Dimension(icon.getIconWidth(), icon.getIconHeight()));
		setEnabled(true);
	}
}
