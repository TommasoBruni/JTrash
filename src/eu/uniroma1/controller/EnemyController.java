package eu.uniroma1.controller;

import java.util.List;
import java.util.Observable;

import eu.uniroma1.model.carte.Card;
import eu.uniroma1.model.carte.Value;
import eu.uniroma1.model.exceptions.DeckFinishedException;
import eu.uniroma1.model.exceptions.GameNotInProgressException;
import eu.uniroma1.model.exceptions.MoveNotAllowedException;

public class EnemyController extends PlayerController
{
	private static final long gameSpeed = 3000;
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
	
	@Override
	public void startTurn() throws GameNotInProgressException, DeckFinishedException
	{
		super.startTurn();
		/* Chiediamo le carte che sono già state scoperte così
		 * da fare la scelta più corretta */
		collectedCardsObservable.setStatusChanged();
		collectedCardsObservable.notifyObservers();
		Card lastTrashCard = FieldController.getInstance().getLastTrashCard();
		boolean trashCardAlreadyPresent = alreadyCollectedCards.stream()
															   .filter((card) -> card.getValore().equals(lastTrashCard.getValore()))
															   .count() > 0;
		if (lastTrashCard != null && !(lastTrashCard.getValore().equals(Value.QUEEN) ||
									   lastTrashCard.getValore().equals(Value.JACK)) 
				                  && (lastTrashCard.getValore().equals(Value.KING) || 
									  lastTrashCard.getValore().equals(Value.JOLLY) ||
									  !trashCardAlreadyPresent))
		{
			/* Pesca la carta dal trash */
			removeFromDeckOrTrash = lastTrashCard;
			/* Notifica il trash di rimuovere la carta da lì */
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
		if (!requestCardFromDeck)
			throw new MoveNotAllowedException();
		removeFromDeckOrTrash = card;
		requestCardFromDeck = false;
		setChanged();
		notifyObservers(lastSelectedCard);
	}
	
	@Override
	public boolean canPeekCard() 
	{
		return super.canPeekCard() && requestCardFromDeck;
	}
}
