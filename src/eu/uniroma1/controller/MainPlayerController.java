package eu.uniroma1.controller;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.ImageIcon;

import eu.uniroma1.model.Player;
import eu.uniroma1.model.carte.*;
import eu.uniroma1.model.carte.Deck.MazzoDiCarteBuilder;
import eu.uniroma1.model.exceptions.DeckFinishedException;
import eu.uniroma1.model.exceptions.GameNotInProgressException;
import eu.uniroma1.model.exceptions.MoveNotAllowedException;

public class MainPlayerController extends PlayerController
{	
	private static MainPlayerController controller;
	private CardsHandleObservable observableForHint;
	private CardsHandleObservable observableForReplacingCards;
	private CardsHandleObservable observableForTrashUpdating;
	
	@Override
	public void operationWithSelectedCard(Card card) 
	{
		observableForHint.setStatusChanged();
		observableForHint.notifyObservers(card);
	}
	
	public void cardSelectedForExchanging(Card card)
	{
		/* Ignore if the turn is over */
		lastSelectedCard = card;
		playerState = PlayerState.EXCHANGING;
	}
	
	public void newCardToTrash(Card card)
	{
		observableForTrashUpdating.setStatusChanged();
		observableForTrashUpdating.notifyObservers(card);
		finishTurn();
	}
	
	public Observable getObservableForHintCard()
	{
		return observableForHint;
	}
	
	public Observable getObservableForReplacingCards()
	{
		return observableForReplacingCards;
	}
	
	public Observable getObservableForTrashUpdating()
	{
		return observableForTrashUpdating;
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
	public Card getCardForReplacing(int position) throws MoveNotAllowedException
	{
		if (playerState != PlayerState.PICKED_CARD &&
			playerState != PlayerState.EXCHANGING)
			throw new MoveNotAllowedException();
		if (lastSelectedCard == null || !goodCard(position + 1))
			return null;
		Card result = lastSelectedCard;
		
		observableForReplacingCards.setStatusChanged();
		observableForReplacingCards.notifyObservers(result);
		
		lastSelectedCard = null;
		playerState = PlayerState.TURN_IS_OVER;
		return result;
	}
	
	public static MainPlayerController getInstance()
	{
		if (controller == null)
			controller = new MainPlayerController();
		return controller;
	}
	
	private MainPlayerController()
	{
		observableForHint = new CardsHandleObservable();
		observableForReplacingCards = new CardsHandleObservable();
		observableForTrashUpdating = new CardsHandleObservable();
		playerState = PlayerState.TURN_IS_OVER;
	}
}
