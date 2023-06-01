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
	private PosizioneDelMazzo posizioneDelMazzo;
	
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
	        Font font = new Font("Arial", Font.BOLD, 20);
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
				g.drawString(valoreCarta, 26, 34);
	        	break;
	        case SULLA_DX:
	        	affineTransform.rotate(Math.PI / 2);
	        	rotatedFont = font.deriveFont(affineTransform);
	        	g.setFont(rotatedFont);
	        	g.drawString(valoreCarta, 30, 6);
	        	break;
	        case IN_BASSO:
	        	affineTransform.rotate(Math.PI);
	        	rotatedFont = font.deriveFont(affineTransform);
	        	g.setFont(rotatedFont);
	        	g.drawString(valoreCarta, 33, 28);
	        	break;
			default:
		        g.setFont(font);
		        g.drawString(valoreCarta, 5, 25);
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

	public ButtonCarta(String valoreCarta, PosizioneDelMazzo posizioneDelMazzo)
	{
		super();
		this.valoreCarta = valoreCarta;
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
