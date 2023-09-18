package eu.uniroma1.view.button;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;

import eu.uniroma1.controller.Resettable;
import eu.uniroma1.model.carte.Card;
import eu.uniroma1.model.carte.CardColor;
import eu.uniroma1.model.carte.Value;
import eu.uniroma1.view.utils.DeckPosition;

import java.awt.geom.AffineTransform;
import java.awt.Graphics2D;

public class CardButton extends JButton implements Resettable
{
	private ImageIcon icon;
	private Card currentCard;
	private Card futureCard;
	private static final String fileVerticalCard = "carta_da_gioco_verticale.jpg";
	private static final String fileHorizontalCard = "carta_da_gioco_orizzontale.jpg";
	private static final String fileVerticalCardForSuggestions = "carta_da_gioco_verticale_per_suggerimento.jpg";
	private boolean faceUp;
	private DeckPosition deckPosition;
	private int positionInTheField;
	
	@Override
	public void paint(Graphics g) 
	{
		super.paint(g);
		if (faceUp)
		{
			/* Set white colour*/
			g.setColor(Color.WHITE);
			/* Paint whole button with white */
			g.fillRect(0, 0, getWidth(), getHeight());
			/* Set black colour */
	        g.setColor(Color.BLACK);
	        /* Set border to black */
	        g.drawRect(0, 0, getWidth() - 1, getHeight() - 1);
	        
	        /* If it is a red card set red as colour */
	        if (currentCard.getColour() == CardColor.RED)
	        	g.setColor(Color.RED);
	        Font font;
	        if (currentCard == Card.BLACK_JOLLY || currentCard == Card.RED_JOLLY)
	        	font = new Font("Arial", Font.BOLD, 12);
	        else
	        	font = new Font("Arial", Font.BOLD, 18);
	        AffineTransform affineTransform = new AffineTransform();
	        Font rotatedFont;
	        
	        switch(deckPosition)
	        {
	        case SULLA_SX:
				/* REF: https://www.codejava.net/java-se/graphics/how-to-draw-text-vertically-with-graphics2d */
				affineTransform.rotate(-(Math.PI / 2));
				rotatedFont = font.deriveFont(affineTransform);
				g.setFont(rotatedFont);
				g.drawString(currentCard.toString(), 26, 34);
	        	break;
	        case SULLA_DX:
	        	affineTransform.rotate(Math.PI / 2);
	        	rotatedFont = font.deriveFont(affineTransform);
	        	g.setFont(rotatedFont);
	        	g.drawString(currentCard.toString(), 30, 6);
	        	break;
	        case IN_BASSO:
	        	affineTransform.rotate(Math.PI);
	        	rotatedFont = font.deriveFont(affineTransform);
	        	g.setFont(rotatedFont);
	        	g.drawString(currentCard.toString(), 33, 28);
	        	break;
			default:
		        g.setFont(font);
		        g.drawString(currentCard.toString(), 5, 25);
				break;
	        }
		}
	}
	
	public void turn()
	{
		if (!faceUp)
		{
			setIcon(null);
			faceUp = true;
			repaint();
		}
	}
	
	public void changeCard(Card card)
	{
		faceUp = true;
		this.currentCard = card;
		repaint();
	}
	
	public Card configureCardForFuture(Card futureCard)
	{
		this.futureCard = futureCard;
		return currentCard;
	}
	
	public void setupFutureCard()
	{
		if (futureCard == null)
			return;
		changeCard(futureCard);
		futureCard = null;
	}
	
	public void setHintCard()
	{
		if (faceUp)
			return;
		setIconP(fileVerticalCardForSuggestions);
	}
	
	/**
	 * Reset the starting image only if turned
	 */
	public void restoreCardImage()
	{
		if (faceUp)
			return;
		setIconP(deckPosition == DeckPosition.SULLA_SX || 
				  deckPosition == DeckPosition.SULLA_DX ?
				  fileHorizontalCard : fileVerticalCard);
	}
	
	public boolean isFaceUpCard()
	{
		return faceUp;
	}
	
	private void setIconP(String s)
	{
		try
		{
			/* Read the image*/
			icon = new ImageIcon(System.getProperty("user.dir").
						concat("\\resources\\" + s));
		} 
		catch (Exception ex) 
		{
			/* Throw the exception */
		    throw ex;
		}
		
		setIcon(icon);
	}
	
	public Card getCard()
	{
		return currentCard;
	}
	
	public int getPositionInTheField()
	{
		return positionInTheField;
	}
	
	public CardButton(Card carta)
	{
		this(carta, DeckPosition.IN_ALTO);
	}
	
	public CardButton(Card carta, DeckPosition deckPosition)
	{
		this(carta, deckPosition, 0);
	}
	
	@Override
	public void reset() 
	{
		faceUp = false;
		restoreCardImage();
	}
	
	public void setBaseCard(Card card)
	{
		this.currentCard = card;
	}
	
	public CardButton(Card card, DeckPosition deckPosition, int positionInTheField)
	{
		this.currentCard = card;
		this.deckPosition = deckPosition;
		this.positionInTheField = positionInTheField;
		setIconP(deckPosition == DeckPosition.SULLA_SX || 
				deckPosition == DeckPosition.SULLA_DX ?
												  fileHorizontalCard : fileVerticalCard);
		setPreferredSize(new Dimension(icon.getIconWidth(), icon.getIconHeight()));
		setMaximumSize(new Dimension(icon.getIconWidth(), icon.getIconHeight()));
		setMinimumSize(new Dimension(icon.getIconWidth(), icon.getIconHeight()));
	}
}
