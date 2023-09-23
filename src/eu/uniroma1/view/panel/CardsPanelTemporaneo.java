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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
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
import eu.uniroma1.controller.Resettable;
import eu.uniroma1.controller.Restartable;
import eu.uniroma1.model.*;
import eu.uniroma1.model.carte.Card;
import eu.uniroma1.model.carte.CardColor;
import eu.uniroma1.model.carte.Value;
import eu.uniroma1.model.exceptions.DeckFinishedException;
import eu.uniroma1.model.exceptions.GameNotInProgressException;
import eu.uniroma1.model.exceptions.MoveNotAllowedException;
import eu.uniroma1.view.button.CardButton;
import eu.uniroma1.view.utils.DeckPosition;

public class CardsPanelTemporaneo extends JPanel implements Resettable
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
	
	private void delayEnemyOperation()
	{
		try 
		{
			Thread.sleep(1000);
		}
		catch (InterruptedException e) 
		{
			e.printStackTrace();
		}
	}
	
	private void restoreAllCardImage()
	{
		/* Restore all card images */
		for (CardButton carta : cards)
			carta.restoreCardImage();
	}
	
	private void setupAllFutureCard()
	{
		for (CardButton card : cards)
			card.setupFutureCard();
	}
	
	/**
	 * Return true if a good place is found, otherwise false 
	 */
	private boolean setupCardsForHint(Card card)
	{
		Value value = card.getValue();
		int intValue;
		boolean result = false;
		
		restoreAllCardImage();
		
		try
		{
			intValue = Integer.parseInt(value.toString()) - 1;
		}
		catch(NumberFormatException ex)
		{
			if (value.equals(Value.ACE))
			{
				intValue = 0;
			}
			else if (value.equals(Value.KING) || 
					 value.equals(Value.JOLLY))
			{
				for (CardButton carta : cards)
				{
					/* Verify that at least one card is face down */
					if (!carta.isFaceUpCard() && carta.isVisible())
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
		
		/* If this card is already face up or is not visible, return false
		 * to indicate that there is no good place to insert
		 * the input card */
		if (intValue >= cards.length || cards[intValue].isFaceUpCard() ||
		    !cards[intValue].isVisible())
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
		c.turn();
		/* This means that a good choice is taken */
		setupAllFutureCard();

		/* Take the old one card and try to set hint cards that match this one */
		oldCard = c.configureCardForFuture(newCard);

		if (setupCardsForHint(oldCard))
		{
			playerController.newCardSelectedForExchanging(oldCard);
			return;
		}
		/* There is no good place for the old card, so discard it and update the current */
		c.setupFutureCard();
		if (!isVictory())
			FieldController.getInstance().newCardToTrash(oldCard);			
	}
	
	private int getRightPosBasedOnDeck(Value val)
	{
		int intValue = val.equals(Value.ACE) ? 0 : Integer.parseInt(val.toString()) - 1;
		
		return getRightPosBasedOnDeck(intValue);
	}
	
	private int getRightPosBasedOnDeck(int intValue)
	{
		if (relativeDeckPosition == DeckPosition.DOWN || relativeDeckPosition == DeckPosition.ON_THE_RIGHT)
			intValue = intValue > 1 ? 9 : intValue == 0 ? 1 : 0; 
		else if (relativeDeckPosition == DeckPosition.ON_THE_LEFT)
			intValue = intValue > 1 ? 9 : intValue;
		return intValue;
	}
	
	private boolean isVictory()
	{
		for (CardButton card : cards)
		{
			if (!card.isFaceUpCard() && card.isVisible())
				return false;
		}
		FieldController.getInstance().gameFinished(playerController);
		return true;
	}
	
	public void enemyCardToTrash(Card card)
	{
		delayEnemyOperation();
		FieldController.getInstance().newCardToTrash(card);
	}
	
	public void enemyOperation(Card card)
	{
		Card oldCard;
		
		setupAllFutureCard();
		if (card.getValue().equals(Value.KING) ||
			card.getValue().equals(Value.JOLLY))
		{
			for (CardButton cardButton : cards)
			{
				if (!cardButton.isFaceUpCard() && cardButton.isVisible())
				{
					cardButton.turn();
					
					if (!isVictory())
					{
						oldCard = cardButton.configureCardForFuture(card);
						
						playerController.newCardSelectedForExchanging(oldCard);
					}
					return;
				}
			}
			enemyCardToTrash(card);
			FieldController.getInstance().gameFinished(playerController);
		}
		else if (card.getValue().equals(Value.JACK) || 
				 card.getValue().equals(Value.QUEEN))
		{
			enemyCardToTrash(card);
		}
		else
		{
			int intValue = getRightPosBasedOnDeck(card.getValue());
			
			if (intValue >= cards.length || cards[intValue].isFaceUpCard() || !cards[intValue].isVisible())
			{
				enemyCardToTrash(card);
				return;
			}
			
			cards[intValue].turn();
			if (!isVictory())
			{
				oldCard = cards[intValue].configureCardForFuture(card);
				playerController.newCardSelectedForExchanging(oldCard);
			}
		}
	}
	
	
	private List<Card> cardAlreadyCollected()
	{
		List<Card> outputList = new ArrayList<>();
		int i;
		
		switch(relativeDeckPosition)
		{
		case DOWN:
				for (i = cards.length - 1; i >= 0; i--)
				{
					if (cards[i].isFaceUpCard())
						outputList.add(cards[i].getCard());
					else if (!cards[i].isVisible())
						outputList.add(Card.BLACK_JOLLY);
					else
						outputList.add(null);
				}
			break;
		case ON_THE_LEFT:
				for (i = cards.length / 2 - 1; i >= 0; i--)
				{
					if (cards[i].isFaceUpCard())
						outputList.add(cards[i].getCard());
					else if (!cards[i].isVisible())
						outputList.add(Card.BLACK_JOLLY);
					else
						outputList.add(null);
				}
				for (i = cards.length - 1; i >= cards.length / 2; i--)
				{
					if (cards[i].isFaceUpCard())
						outputList.add(cards[i].getCard());
					else if (!cards[i].isVisible())
						outputList.add(Card.BLACK_JOLLY);
					else
						outputList.add(null);
				}
			break;
		case ON_THE_RIGHT:
					for (i = cards.length / 2; i < cards.length; i++)
					{
						if (cards[i].isFaceUpCard())
							outputList.add(cards[i].getCard());
						else if (!cards[i].isVisible())
							outputList.add(Card.BLACK_JOLLY);
						else
							outputList.add(null);
					}
					
					for (i = 0; i < cards.length / 2; i++)
					{
						if (cards[i].isFaceUpCard())
							outputList.add(cards[i].getCard());
						else if (!cards[i].isVisible())
							outputList.add(Card.BLACK_JOLLY);
						else
							outputList.add(null);
					}
			break;
		default:
				outputList = Arrays.stream(cards)
				   .map((cardButton) -> cardButton.isFaceUpCard() ? cardButton.getCard() : cardButton.isVisible() ? null : Card.BLACK_JOLLY)
				   .collect(Collectors.toList());
			break;
		}
		return outputList;
	}
	
	private void setupCards() throws GameNotInProgressException, DeckFinishedException
	{
		boolean isHorizontal = (this.relativeDeckPosition == DeckPosition.ON_THE_RIGHT || this.relativeDeckPosition == DeckPosition.ON_THE_LEFT);
		int i, j, minCard;
		JPanel pannelloCarteSuperiori = new JPanel();
		JPanel pannelloCarteInferiori = new JPanel();
		GridBagConstraints gbcTraPanelInfESup = new GridBagConstraints();
		GridBagConstraints gbcPerCarte = new GridBagConstraints();
		
		pannelloCarteSuperiori.setLayout(new GridBagLayout());
		pannelloCarteInferiori.setLayout(new GridBagLayout());
		pannelloCarteInferiori.setBackground(new Color(255, 255, 204));
		pannelloCarteSuperiori.setBackground(new Color(255, 255, 204));
		setLayout(new GridBagLayout());
		
		cards = new CardButton[playerController.getCardsInHand()];
		minCard = 1;
		
		for (i = 0; i < minCard; i++)
		{
			cards[i] = new CardButton(FieldController.getInstance().nextCard(), this.relativeDeckPosition, i);
			if (playerController instanceof MainPlayerController)
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
		
		for (j = 0; j + i < 2; j++)
		{
			cards[j + i] = new CardButton(FieldController.getInstance().nextCard(), this.relativeDeckPosition, i + j);
			if (playerController instanceof MainPlayerController)
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
	
	@Override
	public void reset() 
	{
		CardButton cardButton;
		
		for (int i = 0; i < cards.length; i++)
		{
			cardButton = cards[i];
			
			cardButton.reset();
			
			if (getRightPosBasedOnDeck(i) > playerController.getCardsInHand() - 1)
			{
				cardButton.setVisible(false);
				continue;
			}
			
			cardButton.setVisible(true);
			
			try 
			{
				cardButton.setBaseCard(FieldController.getInstance().nextCard());
			} 
			catch (GameNotInProgressException | DeckFinishedException e)
			{
				/* Non può accadere, stiamo riavviando la partita */
			}
		}
	}
	
	public void initializeControllerEvents()
	{
		if (playerController != null)
		{
			if (playerController instanceof MainPlayerController)
			{
				playerController.addObserver(new Observer() {
					
					@Override
					public void update(Observable o, Object arg) {
						if (!setupCardsForHint((Card)arg))
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
				playerController.getCollectedCardsObservable().addObserver(new Observer()
				{
					
					@Override
					public void update(Observable o, Object arg)
					{
						playerController.alreadyCollectedCard(cardAlreadyCollected());
					}
				});
			}
		}
	}
	
	public CardsPanelTemporaneo(DeckPosition deckPosition, PlayerController playerController) throws GameNotInProgressException, DeckFinishedException, MoveNotAllowedException
	{
		//animationTimer = new Timer();
		this.relativeDeckPosition = deckPosition;
		setBackground(new Color(255, 255, 204));
		this.playerController = playerController;
		initializeControllerEvents();
		
		firstTime = true;
		setupCards();
	}
}