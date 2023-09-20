package eu.uniroma1.controller;

import java.util.List;
import java.util.Observable;

import javax.swing.ImageIcon;

import eu.uniroma1.model.carte.Card;
import eu.uniroma1.model.carte.Value;
import eu.uniroma1.model.exceptions.DeckFinishedException;
import eu.uniroma1.model.exceptions.GameNotInProgressException;
import eu.uniroma1.model.exceptions.MoveNotAllowedException;

/**
 * Enemy controller class 
 */
public class EnemyController extends PlayerController
{
	private static final long gameSpeed = 750;
	private Card removeFromDeckOrTrash;
	private boolean requestCardFromDeck;
	
	private void delayGame()
	{
		try
		{
			Thread.sleep(gameSpeed);	
		}
		catch (Exception e) 
		{
			/* Ignore */
		}
	}
	
	private int cardToInt(Card card)
	{
		return card.getValue().equals(Value.ACE) ? 0 : Integer.parseInt(card.getValue().toString()) - 1;
	}
	
	private boolean isAlreadyPresent(Card card)
	{
		Card current;
		
		/* Jolly and king are always accepted. */
		if (card.getValue().equals(Value.KING) || card.getValue().equals(Value.JOLLY))
			return false;
		
		for (int i = 0; i < alreadyCollectedCards.size(); i++)
		{
			current = alreadyCollectedCards.get(i);
			if (current == null)
				continue;
			/* 
			 * NOTA:
			 * Qualcosa c'è in questa posizione vediamo se è uguale
			 * alla carta da controllare oppure è un king o un jolly
			 * (nel caso in questa posizione ci fosse un jolly o un re il
			 * controllo di uguaglianza fallirebbe ma vediamo se la carta
			 * da controllare è uguale alla posizione corrente) */
			if (current.getValue().equals(card.getValue()) || 
				cardToInt(card) == i)
				return true;
		}
		return false;
	}
	
	@Override
	public void startTurn() throws GameNotInProgressException, DeckFinishedException
	{
		super.startTurn();
		/* Ask for already collected cards.*/
		collectedCardsObservable.setStatusChanged();
		collectedCardsObservable.notifyObservers();
		Card lastTrashCard = FieldController.getInstance().getLastTrashCard();
		
		if (lastTrashCard != null && !(lastTrashCard.getValue().equals(Value.QUEEN) ||
									   lastTrashCard.getValue().equals(Value.JACK)) 
				                  && (lastTrashCard.getValue().equals(Value.KING) || 
									  lastTrashCard.getValue().equals(Value.JOLLY) ||
									  !isAlreadyPresent(lastTrashCard)))
		{
			/* Pick card from deck */
			removeFromDeckOrTrash = lastTrashCard;
			/* Notify trash to remove the card from it. */
			FieldController.getInstance().notifyForReplacing(lastTrashCard);
			setChanged();
			notifyObservers(lastTrashCard);
		}
		else
		{
			requestCardFromDeck = true;
			FieldController.getInstance().notifyForAutoSelecting();
		}
	}
	
	@Override
	public void newCardSelectedForExchanging(Card card) 
	{
		/*
		 * It's called when the first card (from trash or deck) is already picked,
		 * so this method is called for the old cards that were in the position of the new
		 * picked card.
		 */
		super.newCardSelectedForExchanging(card);
		delayGame();
		/* In order to remove the card from the trash or deck */
		FieldController.getInstance().notifyForReplacing(removeFromDeckOrTrash);
		setChanged();
		notifyObservers(card);
	}
	
	@Override
	public void operationWithSelectedCard(Card card) throws MoveNotAllowedException 
	{
		if (!requestCardFromDeck)
			throw new MoveNotAllowedException();
		removeFromDeckOrTrash = card;
		requestCardFromDeck = false;
		setChanged();
		notifyObservers(lastSelectedCard);
	}
	
	@Override
	public boolean canPickCard() 
	{
		return super.canPickCard() && requestCardFromDeck;
	}
	
	@Override
	public void reset()
	{
		super.reset();
		removeFromDeckOrTrash = null;
		requestCardFromDeck = false;
	}
	
	/**
	 * Enemy controller builder
	 * @param imageIcon icon to setup to the enemy. 
	 */
	public EnemyController(ImageIcon imageIcon)
	{
		playerData.updatePlayerData(imageIcon.getDescription(), imageIcon.getDescription(), imageIcon);
	}
}
