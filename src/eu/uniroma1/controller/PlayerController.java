package eu.uniroma1.controller;

import java.util.Observable;
import java.util.function.Consumer;

import eu.uniroma1.model.carte.Card;
import eu.uniroma1.model.exceptions.MoveNotAllowedException;

public abstract class PlayerController extends Observable
{
	protected PlayerState playerState;
	protected Card lastSelectedCard;
	
	public abstract void operationWithSelectedCard(Card card);
	
	public void startTurn() 
	{
		playerState = PlayerState.TURN_STARTED;
	}
	
	public void finishTurn() 
	{
		/* Serve a notificare al fieldController che il turno di questo giocatore è finito.
		 * Questo viene chiamato dalla view quando ha finito di fare tutti gli spostamenti
		 * delle carte. */
		setChanged();
		notifyObservers();
	}
	
	public void newCardSelected(Card carta) throws MoveNotAllowedException
	{
		if (playerState != PlayerState.TURN_STARTED)
			throw new MoveNotAllowedException();
		lastSelectedCard = carta;
		
		operationWithSelectedCard(lastSelectedCard);
		
		playerState = PlayerState.PICKED_CARD;
	}
	
	public PlayerController()
	{
		playerState = PlayerState.TURN_IS_OVER;
	}
}
