package eu.uniroma1.view.panel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

import eu.uniroma1.controller.FieldController;
import eu.uniroma1.controller.MainPlayerController;
import eu.uniroma1.controller.Resettable;
import eu.uniroma1.model.cards.Card;
import eu.uniroma1.model.exceptions.DeckFinishedException;
import eu.uniroma1.model.exceptions.GameNotInProgressException;
import eu.uniroma1.model.exceptions.MoveNotAllowedException;
import eu.uniroma1.view.button.CardButton;
import eu.uniroma1.view.utils.AudioManager;
import eu.uniroma1.view.utils.DeckPosition;

import javax.swing.*;

/**
 * Deck panel class 
 */
public class DeckPanel extends JPanel implements Observer, Resettable
{
	private CardButton cardToPickButton;
	private TrashPanel trashSpace;
	private CardButton pickedCardButton;
	private JPanel cardsContainer;
	private JPanel pickedCardSpace;
	private boolean firstCard;

	@Override
	public void update(Observable o, Object arg) 
	{
		Card selectedCard = (Card)arg;
		Card discardedCard = trashSpace.getLastCard();
		Card pickedCard = pickedCardButton.getCard();
		
		if (selectedCard.equals(discardedCard))
			/* The user selects the card from the trash */
			trashSpace.removeCardFromTop();
		else if(selectedCard.equals(pickedCard))
			/* The user selects the card from the deck */
			pickedCardButton.setVisible(false);
	}
	
	/**
	 * Indicate to the deck that there is a request to pick the card 
	 */
	private void cardToPickEvent()
	{
		Card card, oldCard;
		
		AudioManager.getInstance().play(System.getProperty("user.dir").concat("\\resources\\sliding_card.wav"));
		oldCard = pickedCardButton.getCard();
		if (!firstCard)
		{
			try 
			{
				pickedCardButton.changeCard(FieldController.getInstance().nextCard());
			}
			catch (GameNotInProgressException | DeckFinishedException e1) 
			{
				cardToPickButton.setVisible(false);
				JOptionPane.showMessageDialog(new JFrame(), "No more cards!", "Game finished!", JOptionPane.OK_OPTION);
				return;
			}
		}
		
		card = pickedCardButton.getCard();

		if (!FieldController.getInstance().canPickCard())
		{
			/* Restore the information */
			FieldController.getInstance().backupCard();
			pickedCardButton.changeCard(oldCard);
			return;
		}
		
		/* If it is the first card it is only necessary the button visible otherwise it is needed change the card */
		pickedCardButton.setVisible(true);
		
		firstCard = false;
		
		try
		{
			/* Start the process of the player that picked the card */
			FieldController.getInstance().cardSelected(card);
		} 
		catch (MoveNotAllowedException e1)
		{
			/* This cannot happen, canPickCard check this situation */
		}
	}
	
	@Override
	public void reset() 
	{
		firstCard = true;
		trashSpace.reset();
		try 
		{
			/* This is never face up */
			cardToPickButton.setBaseCard(FieldController.getInstance().nextCard());
		} 
		catch (GameNotInProgressException | DeckFinishedException e) 
		{
			/* Cannot happen, the game is restarting */
		}
		try 
		{
			pickedCardButton.setBaseCard(FieldController.getInstance().nextCard());
			pickedCardButton.turn();
			pickedCardButton.setVisible(false);
		} 
		catch (GameNotInProgressException | DeckFinishedException e) 
		{
			/* Cannot happen, the game is restarting */
		}
	}
	
	/**
	 * Deck panel class builder
	 * @param observable observable to get notifications
	 */
	public DeckPanel(Observable observable)
	{
		pickedCardSpace = new JPanel();
		observable.addObserver(this);
		firstCard = true;
		try 
		{
			cardToPickButton = new CardButton(FieldController.getInstance().nextCard(), DeckPosition.TOP);
			FieldController.getInstance().getObservableForTrashUpdating().addObserver(new Observer() {
				@Override
				public void update(Observable o, Object arg) {
					trashSpace.addCardToTop((Card)arg);
					pickedCardButton.setVisible(false);
				}
			});
			
			FieldController.getInstance().getObservableForAutoSelectedCards().addObserver(new Observer() {
				@Override
				public void update(Observable o, Object arg) {
					cardToPickEvent();
				}
			});
			trashSpace = new TrashPanel();
			pickedCardButton = new CardButton(FieldController.getInstance().nextCard());
			pickedCardButton.turn();
			pickedCardButton.setVisible(false);
		} 
		catch (GameNotInProgressException | DeckFinishedException e)
		{
			/* Cannot happen, the game is creating */
			e.printStackTrace();
		}
		
		cardToPickButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				cardToPickEvent();
			}
		});
		
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
		
		gbc.weightx = 0.01;
		gbc.weighty = 0.01;
		gbc.anchor = GridBagConstraints.LINE_END;
        
		cardsContainer = new JPanel(new GridBagLayout());
		cardsContainer.add(cardToPickButton, gbc);
        
		gbc.gridx = 1;
		gbc.gridy = 0;
		
		gbc.weightx = 0.01;
		gbc.weighty = 0.01;
		
		gbc.anchor = GridBagConstraints.LINE_START;
		gbc.insets = new Insets(0, 5, 0, 0);
		
		pickedCardSpace.setPreferredSize(new Dimension(60, 63));
		pickedCardSpace.add(pickedCardButton);
		pickedCardSpace.setBackground(new Color(255, 255, 204));
		cardsContainer.add(pickedCardSpace, gbc);
        
		gbc.gridx = 2;
		gbc.gridy = 0;
		
		gbc.weightx = 0.01;
		gbc.weighty = 0.01;
		
		gbc.anchor = GridBagConstraints.LINE_START;
		
		/* To insert a bit of space between the cards to pick, the picked card and the discarded cards */
		gbc.insets = new Insets(0, 30, 0, 0);
		cardsContainer.add(trashSpace, gbc);
		cardsContainer.setBackground(new Color(255, 255, 204));
        
		setPreferredSize(new Dimension(500, 95));
        add(cardsContainer);
        setBackground(new Color(255, 255, 204));
	}
}
