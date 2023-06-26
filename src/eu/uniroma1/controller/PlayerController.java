package eu.uniroma1.controller;

import java.util.List;
import java.util.Observable;
import java.util.function.Consumer;

import eu.uniroma1.model.carte.Card;
import eu.uniroma1.model.carte.Value;
import eu.uniroma1.model.exceptions.DeckFinishedException;
import eu.uniroma1.model.exceptions.GameNotInProgressException;
import eu.uniroma1.model.exceptions.MoveNotAllowedException;

public abstract class PlayerController extends Observable
{
	protected PlayerState playerState;
	protected Card lastSelectedCard;
	protected GenericObservable collectedCardsObservable;
	protected List<Card> alreadyCollectedCards;
	protected PlayerData playerData;
	
	public abstract void operationWithSelectedCard(Card card) throws MoveNotAllowedException;
	
	public void startTurn() throws GameNotInProgressException, DeckFinishedException 
	{
		playerState = PlayerState.TURN_STARTED;
	}
	
	public void finishTurn() 
	{
		playerState = PlayerState.TURN_IS_OVER;
	}
	
	public void newCardSelectedForExchanging(Card card)
	{
		/* Ignore if the turn is over */
		lastSelectedCard = card;
		playerState = PlayerState.EXCHANGING;
	}
	
	public void alreadyCollectedCard(List<Card> collectedCards)
	{
		alreadyCollectedCards = collectedCards;
	}
	
	public void trashLastSelectedCard()
	{
		FieldController.getInstance().newCardToTrash(lastSelectedCard);
		lastSelectedCard = null;
	}
	
	public PlayerData getPlayerData()
	{
		return playerData;
	}
	
	private boolean goodCard(int position)
	{
		return lastSelectedCard.getValore().toString().equals("" + (position)) || 
			   lastSelectedCard.getValore().equals(Value.KING) ||
			   lastSelectedCard.getValore().equals(Value.JOLLY) ||
			   (lastSelectedCard.getValore().equals(Value.ASSO) && position == 1);
	}
	
	/**
	 * With the given position checks if the last card selected is
	 * good one for replacing.
	 * If yes return the last card selected otherwise null.
	 * @param position of the current client
	 * @return {@link Card} or null if the position doesn't match the value
	 * of the last card
	 * @throws MoveNotAllowedException if the current state does not allow this move
	 */
	public Card getCardFromDeckTrash(int position) throws MoveNotAllowedException
	{
		if (playerState != PlayerState.PICKED_CARD &&
			playerState != PlayerState.EXCHANGING)
			throw new MoveNotAllowedException();
		if (lastSelectedCard == null || !goodCard(position + 1))
			return null;
		Card result = lastSelectedCard;
		
		FieldController.getInstance().notifyForReplacing(result);
		
		lastSelectedCard = null;
		playerState = PlayerState.TURN_IS_OVER;
		return result;
	}
	
	public GenericObservable getCollectedCardsObservable()
	{
		return collectedCardsObservable;
	}
	
	public void newCardSelected(Card carta) throws MoveNotAllowedException
	{
		if (playerState != PlayerState.TURN_STARTED)
			throw new MoveNotAllowedException();
		lastSelectedCard = carta;
		
		operationWithSelectedCard(lastSelectedCard);
		
		playerState = PlayerState.PICKED_CARD;
	}
	
	public boolean canPeekCard()
	{
		return playerState == playerState.TURN_STARTED;
	}
	
	public boolean isMain()
	{
		return false;
	}
	
	public PlayerController()
	{
		playerState = PlayerState.TURN_IS_OVER;
		collectedCardsObservable = new GenericObservable();
		playerData = new PlayerData();
	}
}
