package eu.uniroma1.view.panel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.Border;

import eu.uniroma1.controller.Enableable;
import eu.uniroma1.controller.FieldController;
import eu.uniroma1.controller.MainPlayerController;
import eu.uniroma1.controller.Resettable;
import eu.uniroma1.model.cards.Card;
import eu.uniroma1.model.exceptions.DeckFinishedException;
import eu.uniroma1.model.exceptions.GameNotInProgressException;
import eu.uniroma1.model.exceptions.MoveNotAllowedException;
import eu.uniroma1.view.button.CardButton;


/**
 * Trash panel class
 */
public class TrashPanel extends JPanel implements Enableable, Resettable
{
	private CardButton discardedCardsButton;
	private List<Card> discardedCards;
	private Border compoundBorder;
	private JPanel internalPanel;
	
	/**
	 * Get last card from the list
	 * @return last card of the list 
	 */
	public Card getLastCard()
	{
		if (discardedCards.size() > 0)
			return discardedCards.get(discardedCards.size() - 1);
		return null;
	}
	
	/**
	 * Remove the card from the top of the list 
	 */
	public void removeCardFromTop()
	{
		Card newCard;
		
		discardedCards.remove(discardedCards.size() - 1);
		if (discardedCards.size() > 0)
		{
			newCard = discardedCards.get(discardedCards.size() - 1);
			discardedCardsButton.changeCard(newCard);	
		}
		else
		{
			discardedCardsButton.setVisible(false);
			newCard = null;
		}
		FieldController.getInstance().setLastTrashCard(newCard);
	}
	
	/**
	 * Add the card to the top of the list
	 * @param card card to add to the top of trash
	 */
	public void addCardToTop(Card card)
	{
		FieldController.getInstance().setLastTrashCard(card);
		discardedCards.add(card);
		discardedCardsButton.changeCard(card);
		discardedCardsButton.setVisible(true);
	}
	
	@Override
	public void enableObject() 
	{
		setEnabled(true);
	}

	@Override
	public void disableObject() 
	{
		setEnabled(false);
	}
	
	@Override
	public void reset()
	{
		try 
		{
			discardedCards = new ArrayList<>();
			Card firstCard = FieldController.getInstance().nextCard();
			FieldController.getInstance().setLastTrashCard(firstCard);
			discardedCards.add(firstCard);
			discardedCardsButton.setBaseCard(firstCard);
			discardedCardsButton.turn();
		} 
		catch (GameNotInProgressException | DeckFinishedException e)
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * Trash panel class builder 
	 */
	public TrashPanel()
	{
		discardedCards = new ArrayList<>();
		try 
		{
			Card firstCard = FieldController.getInstance().nextCard();
			
			FieldController.getInstance().setLastTrashCard(firstCard);
			discardedCards.add(firstCard);
			discardedCardsButton = new CardButton(firstCard);
			discardedCardsButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e)
				{
					try 
					{
						FieldController.getInstance().cardSelected(discardedCardsButton.getCard());
					}
					catch (MoveNotAllowedException e1)
					{
						/* Ignore */
					}
				}
			});
			discardedCardsButton.turn();
		} 
		catch (GameNotInProgressException | DeckFinishedException e)
		{
			/* Cannot happen, the game is creating right now */
			e.printStackTrace();
		}
		Border bordoInterno = BorderFactory.createTitledBorder("Trash");
		Border bordoEsterno = BorderFactory.createEmptyBorder(0, 5, 5, 5);
		compoundBorder = BorderFactory.createCompoundBorder(bordoEsterno, bordoInterno);
		
		internalPanel = new JPanel();
		internalPanel.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		
		gbc.weightx = 0.01;
		gbc.weighty = 0.01;
		internalPanel.add(discardedCardsButton, gbc);
		internalPanel.setBackground(new Color(255, 255, 204));
		
		setBorder(compoundBorder);
		setPreferredSize(new Dimension(70, 90));
		add(internalPanel);
		setBackground(new Color(255, 255, 204));
	}
}
