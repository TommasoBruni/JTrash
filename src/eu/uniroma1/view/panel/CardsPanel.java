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

public class CardsPanel extends JPanel implements Resettable
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
		if (isVictory())
			FieldController.getInstance().gameFinished(playerController);
		else
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
	
	private boolean isVictory()
	{
		for (CardButton card : cards)
		{
			if (!card.isFaceUpCard())
				return false;
		}
		FieldController.getInstance().gameFinished(playerController);
		return true;
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
					
					if (!isVictory())
					{
						oldCard = cardButton.configureCardForFuture(card);
						
						playerController.newCardSelectedForExchanging(oldCard);
					}
					return;
				}
			}
			delayEnemyOperation();
			FieldController.getInstance().newCardToTrash(card);
			FieldController.getInstance().gameFinished(playerController);
		}
		else if (card.getValore().equals(Value.JACK) || 
				 card.getValore().equals(Value.QUEEN))
		{
			delayEnemyOperation();
			FieldController.getInstance().newCardToTrash(card);
		}
		else
		{
			int intValue = getRightPosBasedOnDeck(card.getValore());
			
			if (cards[intValue].isFaceUpCard())
			{
				delayEnemyOperation();
				FieldController.getInstance().newCardToTrash(card);
				return;
			}
			
			cards[intValue].gira();
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
		
		if (relativeDeckPosition == DeckPosition.IN_BASSO)
		{
			for (i = cards.length - 1; i >= 0; i--)
			{
				if (cards[i].isFaceUpCard())
					outputList.add(cards[i].getCarta());
				else
					outputList.add(null);
			}
		}
		else if (relativeDeckPosition == DeckPosition.SULLA_SX)
		{
			for (i = cards.length / 2 - 1; i >= 0; i--)
			{
				if (cards[i].isFaceUpCard())
					outputList.add(cards[i].getCarta());
				else
					outputList.add(null);
			}
			for (i = cards.length - 1; i >= cards.length / 2; i--)
			{
				if (cards[i].isFaceUpCard())
					outputList.add(cards[i].getCarta());
				else
					outputList.add(null);
			}
		}
		else if (relativeDeckPosition == DeckPosition.SULLA_DX)
		{
			for (i = cards.length / 2; i < cards.length; i++)
			{
				if (cards[i].isFaceUpCard())
					outputList.add(cards[i].getCarta());
				else
					outputList.add(null);
			}
			
			for (i = 0; i < cards.length / 2; i++)
			{
				if (cards[i].isFaceUpCard())
					outputList.add(cards[i].getCarta());
				else
					outputList.add(null);
			}
		}
		else
		{
			outputList = Arrays.stream(cards)
							   .map((cardButton) -> cardButton.isFaceUpCard() ? cardButton.getCarta() : null)
							   .collect(Collectors.toList());
		}
		return outputList;
	}
	
	public void updatePlayerController(PlayerController playerController)
	{
		this.playerController = playerController;
		initializeControllerEvents();
	}
	
	private void setupCards() throws GameNotInProgressException, DeckFinishedException
	{
		boolean isHorizontal = (this.relativeDeckPosition == DeckPosition.SULLA_DX || this.relativeDeckPosition == DeckPosition.SULLA_SX);
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
		minCard = cards.length < 5 ? cards.length : 5;
		
		for (i = 0; i < minCard; i++)
		{
			cards[i] = new CardButton(FieldController.getInstance().nextCard(), this.relativeDeckPosition, i);
			if (playerController.isMain())
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
		
		for (j = 0; j + i < (cards.length - minCard) + 5; j++)
		{
			cards[j + i] = new CardButton(FieldController.getInstance().nextCard(), this.relativeDeckPosition, i + j);
			if (playerController.isMain())
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
		for (CardButton cardButton : cards)
		{
			cardButton.reset();
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
			if (playerController.isMain())
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
	
	public CardsPanel(DeckPosition deckPosition, PlayerController playerController) throws GameNotInProgressException, DeckFinishedException, MoveNotAllowedException
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
