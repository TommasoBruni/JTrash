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
import eu.uniroma1.model.cards.Card;
import eu.uniroma1.model.cards.CardColor;
import eu.uniroma1.model.cards.Value;
import eu.uniroma1.model.exceptions.DeckFinishedException;
import eu.uniroma1.model.exceptions.GameNotInProgressException;
import eu.uniroma1.model.exceptions.MoveNotAllowedException;
import eu.uniroma1.view.button.CardButton;
import eu.uniroma1.view.utils.DeckPosition;

/**
 * Cards panel class 
 */
public class CardsPanel extends JPanel implements Resettable
{
	private CardButton[] cards;
	private DeckPosition relativeDeckPosition;
	private PlayerController playerController;
	private long enemyDelayMs = 1000;
	
	private void delayEnemyOperation()
	{
		try 
		{
			Thread.sleep(enemyDelayMs);
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
	
	private boolean setupCardsForHint(Card card)
	{
		Value value = card.getValue();
		int intValue;
		boolean result = false;
		
		/* Return true if a good place is found, otherwise false*/
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
		if (cards[intValue].isFaceUpCard() || !cards[intValue].isVisible())
			return false;
		cards[intValue].setHintCard();
		return true;
	}
	
	private void enemyCardToTrash(Card card)
	{
		delayEnemyOperation();
		FieldController.getInstance().newCardToTrash(card);
	}
	
	private void enemyOperation(Card card)
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
			
			if (cards[intValue].isFaceUpCard() || !cards[intValue].isVisible())
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
		return;
	}
	
	private void processForCardExchanging(CardButton c)
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
		if (relativeDeckPosition == DeckPosition.DOWN)
			intValue = (intValue + 9) - (intValue * 2);
		else if (relativeDeckPosition == DeckPosition.ON_THE_LEFT)
			intValue = ((intValue + 4) - (intValue * 2)) < 0 ?  ((intValue + 4) - (intValue * 2)) + 10 : ((intValue + 4) - (intValue * 2));
		else if (relativeDeckPosition == DeckPosition.ON_THE_RIGHT)
			intValue = (intValue + 5) % 10;
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
	
	private List<Card> cardsAlreadyCollected()
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
	
	private void horizontalSetup() throws GameNotInProgressException, DeckFinishedException
	{
		int j, i, minCard;
		JPanel panelTopCards = new JPanel();
		JPanel panelDownCards = new JPanel();
		GridBagConstraints gbcBetweenTopDown = new GridBagConstraints();
		GridBagConstraints gbcForCards = new GridBagConstraints();
		
		panelTopCards.setLayout(new GridBagLayout());
		panelDownCards.setLayout(new GridBagLayout());
		panelDownCards.setBackground(new Color(255, 255, 204));
		panelTopCards.setBackground(new Color(255, 255, 204));
		setLayout(new GridBagLayout());
		
		cards = new CardButton[playerController.getCardsInHand()];
		minCard = cards.length < 5 ? cards.length : 5;
		
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
			
			gbcForCards.gridx = 0;
			gbcForCards.gridy = i;
			gbcForCards.insets = new Insets(0, 0, 15, 0);
			
			panelTopCards.add(cards[i], gbcForCards);
		}
		
		for (j = 0; j + i < (cards.length - minCard) + 5; j++)
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

			gbcForCards.gridx = 0;
			gbcForCards.gridy = j;
			gbcForCards.insets = new Insets(0, 0, 15, 0);
			panelDownCards.add(cards[j + i], gbcForCards);
		}
		
		gbcBetweenTopDown.insets = new Insets(0, 0, 0, 5);
		
		gbcBetweenTopDown.gridx = 0;
		gbcBetweenTopDown.gridy = 0;
		gbcBetweenTopDown.weightx = 0.1;
		gbcBetweenTopDown.weighty = 0.1;
		
		add(panelTopCards, gbcBetweenTopDown);
		gbcBetweenTopDown.weightx = 1;
		gbcBetweenTopDown.weighty = 1;
		
	    gbcBetweenTopDown.gridx = 1;
	    gbcBetweenTopDown.gridy = 0;
		add(panelDownCards, gbcBetweenTopDown);
	}
	
	private void verticalSetup() throws GameNotInProgressException, DeckFinishedException
	{
		int i, j, minCard;
		JPanel topCardsPanel = new JPanel();
		JPanel belowCardsPanel = new JPanel();
		GridBagConstraints gbcBetweenTopDown = new GridBagConstraints();
		GridBagConstraints gbcForCards = new GridBagConstraints();
		
		topCardsPanel.setLayout(new GridBagLayout());
		belowCardsPanel.setLayout(new GridBagLayout());
		belowCardsPanel.setBackground(new Color(255, 255, 204));
		topCardsPanel.setBackground(new Color(255, 255, 204));
		setLayout(new GridBagLayout());
		
		cards = new CardButton[playerController.getCardsInHand()];
		minCard = cards.length < 5 ? cards.length : 5;
		
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
			
			gbcForCards.gridx = i;
			gbcForCards.gridy = 0;
			gbcForCards.insets = new Insets(0, 0, 0, 15);
			
			topCardsPanel.add(cards[i], gbcForCards);
		}
		
		for (j = 0; j + i < (cards.length - minCard) + 5; j++)
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


			gbcForCards.gridx = j;
			gbcForCards.gridy = 0;
			gbcForCards.insets = new Insets(0, 0, 0, 15);
			belowCardsPanel.add(cards[j + i], gbcForCards);
		}
		
		gbcBetweenTopDown.insets = new Insets(0, 0, 5, 0);
		
		gbcBetweenTopDown.gridx = 1;
		gbcBetweenTopDown.gridy = 0;
		gbcBetweenTopDown.weightx = 0.1;
		gbcBetweenTopDown.weighty = 0.1;
		
		add(topCardsPanel, gbcBetweenTopDown);
		gbcBetweenTopDown.weightx = 1;
		gbcBetweenTopDown.weighty = 1;


		gbcBetweenTopDown.gridx = 1;
		gbcBetweenTopDown.gridy = 1;
	    
		add(belowCardsPanel, gbcBetweenTopDown);
	}
	
	private void setupCards() throws GameNotInProgressException, DeckFinishedException
	{
		boolean isHorizontal = (this.relativeDeckPosition == DeckPosition.ON_THE_RIGHT || this.relativeDeckPosition == DeckPosition.ON_THE_LEFT);

		if (isHorizontal)
			horizontalSetup();
		else
			verticalSetup();
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
				/* This cannot happen, the game is restarting */
			}
		}
	}
	
	
	private void initializeControllerEvents()
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
						playerController.setAlreadyCollectedCards(cardsAlreadyCollected());
					}
				});
			}
		}
	}
	
	/**
	 * Cards panel class builder
	 * @param deckPosition deck position relative to the user position
	 * @param playerController player controller
	 * @throws GameNotInProgressException if the game is not started
	 * @throws DeckFinishedException if there are no more cards
	 * @throws MoveNotAllowedException if a move is done without the permission 
	 */
	public CardsPanel(DeckPosition deckPosition, PlayerController playerController) throws GameNotInProgressException, DeckFinishedException, MoveNotAllowedException
	{
		//animationTimer = new Timer();
		this.relativeDeckPosition = deckPosition;
		setBackground(new Color(255, 255, 204));
		this.playerController = playerController;
		initializeControllerEvents();

		setupCards();
	}
}
