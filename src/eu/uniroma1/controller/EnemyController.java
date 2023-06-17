package eu.uniroma1.controller;

import java.util.Observable;

import eu.uniroma1.model.carte.Card;
import eu.uniroma1.model.exceptions.DeckFinishedException;
import eu.uniroma1.model.exceptions.GameNotInProgressException;
import eu.uniroma1.model.exceptions.MoveNotAllowedException;

public class EnemyController extends PlayerController
{
	private static final long gameSpeed = 1000;
	private Card removeFromDeckOrTrash;
	
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
	
	@Override
	public void startTurn() throws GameNotInProgressException, DeckFinishedException
	{
		super.startTurn();
		
		Card lastTrashCard = FieldController.getInstance().getLastTrashCard();
		
		setChanged();
		if (lastTrashCard == null)
		{
			lastSelectedCard = FieldController.getInstance().getLastCardOfDeck();
			removeFromDeckOrTrash = lastSelectedCard;
			FieldController.getInstance().notifyForReplacing(lastSelectedCard);
			notifyObservers(lastSelectedCard);
		}
		else
		{
			removeFromDeckOrTrash = lastTrashCard;
			FieldController.getInstance().notifyForReplacing(lastTrashCard);
			notifyObservers(lastTrashCard);
		}
	}
	
	@Override
	public void newCardSelectedForExchanging(Card card) 
	{
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
		/* Non permettere mai all'utente di premere quando è il turno di un nemico */
		throw new MoveNotAllowedException();
	}
}
