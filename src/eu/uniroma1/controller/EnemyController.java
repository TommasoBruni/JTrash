package eu.uniroma1.controller;

import java.util.Observable;

import eu.uniroma1.model.carte.Card;
import eu.uniroma1.model.exceptions.DeckFinishedException;
import eu.uniroma1.model.exceptions.GameNotInProgressException;
import eu.uniroma1.model.exceptions.MoveNotAllowedException;

public class EnemyController extends PlayerController
{
	private static final long gameSpeed = 2000;
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
			/* Notifica il trash di rimuovere la carta da lì */
			FieldController.getInstance().notifyForReplacing(lastTrashCard);
			notifyObservers(lastTrashCard);
		}
	}
	
	/**
	 * Viene chiamata quando la prima carta (da trash o dal deck) è già stata pescata,
	 * quindi viene chiamato questo metodo per le vecchie carte che erano nella posizione
	 * della nuova carta pescata. 
	 */
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
