package eu.uniroma1.view.button;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;

import eu.uniroma1.controller.Resettable;
import eu.uniroma1.model.cards.Card;
import eu.uniroma1.model.cards.CardColor;
import eu.uniroma1.model.cards.Value;
import eu.uniroma1.view.utils.DeckPosition;

import java.awt.geom.AffineTransform;
import java.awt.Graphics2D;

/**
 * Card button class 
 */
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
	        case ON_THE_LEFT:
				/* REF: https://www.codejava.net/java-se/graphics/how-to-draw-text-vertically-with-graphics2d */
				affineTransform.rotate(-(Math.PI / 2));
				rotatedFont = font.deriveFont(affineTransform);
				g.setFont(rotatedFont);
				g.drawString(currentCard.toString(), 26, 34);
	        	break;
	        case ON_THE_RIGHT:
	        	affineTransform.rotate(Math.PI / 2);
	        	rotatedFont = font.deriveFont(affineTransform);
	        	g.setFont(rotatedFont);
	        	g.drawString(currentCard.toString(), 30, 6);
	        	break;
	        case DOWN:
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
	
	/**
	 * Turns the card face up (if it's not yet).
	 */
	public void turn()
	{
		if (!faceUp)
		{
			setIcon(null);
			faceUp = true;
			repaint();
		}
	}
	
	/**
	 * Switch the current card with the input one.
	 * @param card card to change with.
	 */
	public void changeCard(Card card)
	{
		faceUp = true;
		this.currentCard = card;
		repaint();
	}
	
	/**
	 * Store a card that will be used in the future.
	 * @param futureCard future card.
	 * @return the current card. 
	 */
	public Card configureCardForFuture(Card futureCard)
	{
		this.futureCard = futureCard;
		return currentCard;
	}
	
	/**
	 * Set the configured future card (if any) as current card. 
	 */
	public void setupFutureCard()
	{
		if (futureCard == null)
			return;
		changeCard(futureCard);
		futureCard = null;
	}
	
	/**
	 * Set red the current card (if face down) to give an hint.
	 */
	public void setHintCard()
	{
		if (faceUp)
			return;
		setIconP(fileVerticalCardForSuggestions);
	}
	
	/**
	 * Reset the starting image only if face down.
	 */
	public void restoreCardImage()
	{
		if (faceUp)
			return;
		setIconP(deckPosition == DeckPosition.ON_THE_LEFT || 
				  deckPosition == DeckPosition.ON_THE_RIGHT ?
				  fileHorizontalCard : fileVerticalCard);
	}
	
	/**
	 * Check if the card is face up.
	 * @return boolean to indicate if the card is face up. 
	 */
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
	
	/**
	 * Get the current card.
	 * @return current card. 
	 */
	public Card getCard()
	{
		return currentCard;
	}
	
	/**
	 * Get position in the field (1 to 10).
	 * @return position in the field.
	 */
	public int getPositionInTheField()
	{
		return positionInTheField;
	}
	
	@Override
	public void reset() 
	{
		faceUp = false;
		restoreCardImage();
	}
	
	/**
	 * Directly set the current card
	 * @param card card to set as base card
	 */
	public void setBaseCard(Card card)
	{
		this.currentCard = card;
	}
	
	/**
	 * Card button builder.
	 * @param card to set to the button.
	 */
	public CardButton(Card card)
	{
		this(card, DeckPosition.TOP);
	}
	
	/**
	 * Card button builder.
	 * @param card card to set to the button.
	 * @param deckPosition position of the deck relative to the position of the user.
	 */
	public CardButton(Card card, DeckPosition deckPosition)
	{
		this(card, deckPosition, 0);
	}
	
	/**
	 * Card button builder.
	 * @param card card to set to the button.
	 * @param deckPosition position of the deck relative to the position of the user.
	 * @param positionInTheField position in the game field (1 to 10).
	 */
	public CardButton(Card card, DeckPosition deckPosition, int positionInTheField)
	{
		this.currentCard = card;
		this.deckPosition = deckPosition;
		this.positionInTheField = positionInTheField;
		setIconP(deckPosition == DeckPosition.ON_THE_LEFT || 
				deckPosition == DeckPosition.ON_THE_RIGHT ?
												  fileHorizontalCard : fileVerticalCard);
		setPreferredSize(new Dimension(icon.getIconWidth(), icon.getIconHeight()));
		setMaximumSize(new Dimension(icon.getIconWidth(), icon.getIconHeight()));
		setMinimumSize(new Dimension(icon.getIconWidth(), icon.getIconHeight()));
	}
}
