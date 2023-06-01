package eu.uniroma1.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;

import eu.uniroma1.model.carte.Carte;
import eu.uniroma1.model.carte.Colore;

import java.awt.geom.AffineTransform;
import java.awt.Graphics2D;

public class ButtonCarta extends JButton
{
	private ImageIcon icon;
	private Carte carta;
	private static final String fileCartaGiocoVerticale = "carta_da_gioco_verticale.jpg";
	private static final String fileCartaGiocoOrizzontale = "carta_da_gioco_orizzontale.jpg";
	private boolean isScoperta;
	private PosizioneDelMazzo posizioneDelMazzo;
	
	@Override
	public void paint(Graphics g) 
	{
		super.paint(g);
		if (isScoperta)
		{
			/* Imposta il colore bianco */
			g.setColor(Color.WHITE);
			/* Colora tutto il button di bianco */
			g.fillRect(0, 0, getWidth(), getHeight());
			/* Imposta il colore nero */
	        g.setColor(Color.BLACK);
	        /* Colora il "bordo" di nero */
	        g.drawRect(0, 0, getWidth() - 1, getHeight() - 1);
	        
	        /* Se fosse una carta rossa imposta il rosso come colore */
	        if (carta.getColore() == Colore.ROSSO)
	        	g.setColor(Color.RED);
	        Font font;
	        if (carta == Carte.JOLLY_NERO || carta == Carte.JOLLY_ROSSO)
	        	font = new Font("Arial", Font.BOLD, 12);
	        else
	        	font = new Font("Arial", Font.BOLD, 20);
	        AffineTransform affineTransform = new AffineTransform();
	        Font rotatedFont;
	        
	        switch(posizioneDelMazzo)
	        {
	        case SULLA_SX:
				// questo funzionicchia --> affineTransform.rotate(158);
				//affineTransform.rotate(160, 5, 25);
				/* REF: https://www.codejava.net/java-se/graphics/how-to-draw-text-vertically-with-graphics2d */
				affineTransform.rotate(-(Math.PI / 2));
				//affineTransform.rotate(Math.toRadians(330), 5, 25);
				rotatedFont = font.deriveFont(affineTransform);
				g.setFont(rotatedFont);
				//insieme a funzionicchia --> g.drawString(valoreCarta, 27, 30);
				g.drawString(carta.toString(), 26, 34);
	        	break;
	        case SULLA_DX:
	        	affineTransform.rotate(Math.PI / 2);
	        	rotatedFont = font.deriveFont(affineTransform);
	        	g.setFont(rotatedFont);
	        	g.drawString(carta.toString(), 30, 6);
	        	break;
	        case IN_BASSO:
	        	affineTransform.rotate(Math.PI);
	        	rotatedFont = font.deriveFont(affineTransform);
	        	g.setFont(rotatedFont);
	        	g.drawString(carta.toString(), 33, 28);
	        	break;
			default:
		        g.setFont(font);
		        g.drawString(carta.toString(), 5, 25);
				break;
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

	public ButtonCarta(Carte carta, PosizioneDelMazzo posizioneDelMazzo)
	{
		super();
		this.carta = carta;
		this.posizioneDelMazzo = posizioneDelMazzo;
		try
		{
			/* Leggo l'immagine salvata nella directory "resources" dentro il progetto */
			icon = new ImageIcon(System.getProperty("user.dir").
						concat("\\resources\\" + (posizioneDelMazzo == PosizioneDelMazzo.SULLA_SX || 
												  posizioneDelMazzo == PosizioneDelMazzo.SULLA_DX ?
												  fileCartaGiocoOrizzontale : fileCartaGiocoVerticale)));
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
	}
}
