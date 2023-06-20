package eu.uniroma1.view.panel;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.Observable;
import java.util.Observer;
import java.util.stream.Collectors;

import javax.swing.Timer;
import javax.print.attribute.SupportedValuesAttribute;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.Border;

import eu.uniroma1.controller.FieldController;
import eu.uniroma1.controller.MainPlayerController;
import eu.uniroma1.controller.PlayerController;
import eu.uniroma1.model.*;
import eu.uniroma1.model.carte.Card;
import eu.uniroma1.model.carte.CardColor;
import eu.uniroma1.model.carte.Value;
import eu.uniroma1.model.exceptions.DeckFinishedException;
import eu.uniroma1.model.exceptions.GameNotInProgressException;
import eu.uniroma1.model.exceptions.MoveNotAllowedException;
import eu.uniroma1.view.button.CardButton;
import eu.uniroma1.view.utils.DeckPosition;

public class CardsPanel extends JPanel
{
	private CardButton[] cards;
	private Timer animationTimer;
	private boolean firstTime;
	private int xMovement;
	private int yMovement;
	private DeckPosition relativeDeckPosition;
	private PlayerController playerController;
	
	/*
	@Override
	public void paint(Graphics g)
	{
		// TODO Auto-generated method stub
		super.paint(g);
		Graphics2D graphic2D = (Graphics2D) frameToDraw.getParent().getGraphics();
		
		if (firstTime)
		{
			System.out.println(frameToDraw);
			xMovement = frameToDraw.getCarteDaPescareButton().getBounds().x;
			yMovement = frameToDraw.getCarteDaPescareButton().getBounds().y;
			firstTime = false;
		}
		try
		{
			icon = new ImageIcon(System.getProperty("user.dir").concat("\\resources\\" + fileName));
		} 
		catch (Exception ex) 
		{
		    throw ex;
		}
		//frameToDraw.draw(icon, xMovement, yMovement);
		graphic2D.drawImage(icon.getImage(), xMovement, yMovement, null);
	}
	*/
	
	/*
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		xMovement += 5;
		yMovement += 5;
		repaint();
	}
	
	public void startAnimazione()
	{
		animationTimer = new Timer(50, this);
		animationTimer.restart();
	}
	*/
	
	public void disableAllCards()
	{
		for (CardButton card : cards)
			card.setEnabled(false);
	}
	
	public void enableAllCards()
	{
		for (CardButton card : cards)
			card.setEnabled(true);
	}
	
	public void restoreAllCardImage()
	{
		/* Restore all card images */
		for (CardButton carta : cards)
			carta.restoreCardImage();
	}
	
	public void setupAllFutureCard()
	{
		for (CardButton card : cards)
			card.setupFutureCard();
	}
	
	/**
	 * Return true if a good place is found, otherwise false 
	 */
	private boolean setupCardsForHint(Value value)
	{
		int intValue;
		boolean result = false;
		
		restoreAllCardImage();
		
		try
		{
			intValue = Integer.parseInt(value.toString()) - 1;
		}
		catch(NumberFormatException ex)
		{
			if (value.equals(Value.ASSO))
			{
				intValue = 0;
			}
			else if (value.equals(Value.KING) || 
					 value.equals(Value.JOLLY))
			{
				for (CardButton carta : cards)
				{
					/* Verify that at least one card is face down */
					if (!carta.isFaceUpCard())
						result = true;
					/* No problem, this set the hint just if is face down */
					carta.setHintCard();
				}
				return result;
			}
			else
			{
				return false;
			}
		}
		
		/* If this card is already face up, return false
		 * to indicate that there is no good place to insert
		 * the input card */
		if (cards[intValue].isFaceUpCard())
			return false;
		cards[intValue].setHintCard();
		return true;
	}
	
	public void processForCardExchanging(CardButton c)
	{
		Card newCard, oldCard;
		
		try 
		{
			newCard = playerController.getCardFromDeckTrash(c.getPositionInTheField());
		}
		catch (MoveNotAllowedException e1) 
		{
			return;
		}
		
		if (newCard == null)
		{
			FieldController.getInstance().trashLastSelectedCard();
			return;
		}
		/* Display the card so the user can see it and choose a good position.
		 * The card could automatically go to the trash */
		c.gira();
		/* This means that a good choice is taken */
		setupAllFutureCard();

		/* Take the old one card and try to set hint cards that match this one */
		oldCard = c.configureCardForFuture(newCard);

		if (setupCardsForHint(oldCard.getValore()))
		{
			playerController.newCardSelectedForExchanging(oldCard);
			return;
		}
		/* There is no good place for the old card, so discard it and update the current */
		c.setupFutureCard();
		FieldController.getInstance().newCardToTrash(oldCard);
	}
	
	private int getRightPosBasedOnDeck(Value val)
	{
		int intValue = val.equals(Value.ASSO) ? 0 : Integer.parseInt(val.toString()) - 1;
		
		if (relativeDeckPosition == DeckPosition.IN_BASSO)
			intValue = (intValue + 9) - (intValue * 2);
		else if (relativeDeckPosition == DeckPosition.SULLA_SX)
			intValue = ((intValue + 4) - (intValue * 2)) < 0 ?  ((intValue + 4) - (intValue * 2)) + 10 : ((intValue + 4) - (intValue * 2));
		else if (relativeDeckPosition == DeckPosition.SULLA_DX)
			intValue = (intValue + 5) % 10;
		return intValue;
	}
	
	public void enemyOperation(Card card)
	{
		Card oldCard;
		
		setupAllFutureCard();
		if (card.getValore().equals(Value.KING) ||
			card.getValore().equals(Value.JOLLY))
		{
			for (CardButton cardButton : cards)
			{
				if (!cardButton.isFaceUpCard())
				{
					cardButton.gira();
					
					oldCard = cardButton.configureCardForFuture(card);
					
					playerController.newCardSelectedForExchanging(oldCard);
					return;
				}
			}
			FieldController.getInstance().newCardToTrash(card);
			//TODO: notifica vittoria non ci sono più carte a faccia in giù
		}
		else if (card.getValore().equals(Value.JACK) || 
				 card.getValore().equals(Value.QUEEN))
		{
			FieldController.getInstance().newCardToTrash(card);
		}
		else
		{
			int intValue = getRightPosBasedOnDeck(card.getValore());
			
			if (cards[intValue].isFaceUpCard())
			{
				FieldController.getInstance().newCardToTrash(card);
				return;
			}
			
			cards[intValue].gira();
			oldCard = cards[intValue].configureCardForFuture(card);
			playerController.newCardSelectedForExchanging(oldCard);
		}
	}
	
	public CardsPanel(DeckPosition deckPosition, boolean isEnemy, PlayerController playerController) throws GameNotInProgressException, DeckFinishedException, MoveNotAllowedException
	{
		//animationTimer = new Timer();
		int i, j;
		boolean isHorizontal = (deckPosition == DeckPosition.SULLA_DX || deckPosition == DeckPosition.SULLA_SX);
		JPanel pannelloCarteSuperiori = new JPanel();
		JPanel pannelloCarteInferiori = new JPanel();
		GridBagConstraints gbcTraPanelInfESup = new GridBagConstraints();
		GridBagConstraints gbcPerCarte = new GridBagConstraints();
		this.relativeDeckPosition = deckPosition;
		
		if (playerController != null)
		{
			if (!isEnemy)
			{
				playerController.addObserver(new Observer() {
					
					@Override
					public void update(Observable o, Object arg) {
						if (!setupCardsForHint(((Card)arg).getValore()))
							FieldController.getInstance().trashLastSelectedCard();
					}
				});
			}
			else
			{
				playerController.addObserver(new Observer() {
					
					@Override
					public void update(Observable o, Object arg) {
						enemyOperation((Card)arg);
					}
				});
			}
		}
		this.playerController = playerController;
		
		firstTime = true;
		
		pannelloCarteSuperiori.setLayout(new GridBagLayout());
		pannelloCarteInferiori.setLayout(new GridBagLayout());
		setLayout(new GridBagLayout());
		
		cards = new CardButton[10];
		
		for (i = 0; i < cards.length / 2; i++)
		{
			cards[i] = new CardButton(FieldController.getInstance().nextCard(), deckPosition, i);
			if (!isEnemy)
				cards[i].addActionListener(new ActionListener() {	
					@Override
					public void actionPerformed(ActionEvent e) 
					{
						processForCardExchanging((CardButton)e.getSource());
					}
				});
			
			if (isHorizontal)
			{
				gbcPerCarte.gridx = 0;
				gbcPerCarte.gridy = i;
				gbcPerCarte.insets = new Insets(0, 0, 15, 0);
			}
			else
			{
				gbcPerCarte.gridx = i;
				gbcPerCarte.gridy = 0;
				gbcPerCarte.insets = new Insets(0, 0, 0, 15);
			}
			
			pannelloCarteSuperiori.add(cards[i], gbcPerCarte);
		}
		
		for (j = 0; j + i < cards.length; j++)
		{
			cards[j + i] = new CardButton(FieldController.getInstance().nextCard(), deckPosition, i + j);
			if (!isEnemy)
				cards[j + i].addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) 
					{
						processForCardExchanging((CardButton)e.getSource());
					}
				});
			if (isHorizontal)
			{
				gbcPerCarte.gridx = 0;
				gbcPerCarte.gridy = j;
				gbcPerCarte.insets = new Insets(0, 0, 15, 0);
			}
			else
			{
				gbcPerCarte.gridx = j;
				gbcPerCarte.gridy = 0;
				gbcPerCarte.insets = new Insets(0, 0, 0, 15);
			}
			pannelloCarteInferiori.add(cards[j + i], gbcPerCarte);
		}
		
		if (isHorizontal)
			gbcTraPanelInfESup.insets = new Insets(0, 0, 0, 5);
		else
			gbcTraPanelInfESup.insets = new Insets(0, 0, 5, 0);
		
		if (!isHorizontal)
		{
			gbcTraPanelInfESup.gridx = 1;
			gbcTraPanelInfESup.gridy = 0;
			gbcTraPanelInfESup.weightx = 0.1;
			gbcTraPanelInfESup.weighty = 0.1;
		}
		else
		{
			gbcTraPanelInfESup.gridx = 0;
			gbcTraPanelInfESup.gridy = 0;
			gbcTraPanelInfESup.weightx = 0.1;
			gbcTraPanelInfESup.weighty = 0.1;
		}
		
		add(pannelloCarteSuperiori, gbcTraPanelInfESup);
		gbcTraPanelInfESup.weightx = 1;
		gbcTraPanelInfESup.weighty = 1;
		if (isHorizontal)
		{
		    gbcTraPanelInfESup.gridx = 1;
		    gbcTraPanelInfESup.gridy = 0;
		}
		else
		{
		    gbcTraPanelInfESup.gridx = 1;
		    gbcTraPanelInfESup.gridy = 1;
		}
		add(pannelloCarteInferiori, gbcTraPanelInfESup);
	}
}
