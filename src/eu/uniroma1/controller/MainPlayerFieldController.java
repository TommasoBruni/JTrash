package eu.uniroma1.controller;

import java.util.List;
import java.util.Observable;

import javax.swing.ImageIcon;

import eu.uniroma1.model.Player;
import eu.uniroma1.model.carte.*;
import eu.uniroma1.model.carte.Deck.MazzoDiCarteBuilder;
import eu.uniroma1.model.exceptions.DeckFinishedException;
import eu.uniroma1.model.exceptions.GameNotInProgressException;
import eu.uniroma1.model.exceptions.MoveNotAllowedException;

public class MainPlayerFieldController
{
	private enum MainPlayerState
	{
		TURN_STARTED,
		PICK_FROM_DISCARDED,
		PICK_NEW_CARD_FROM_DECK,
		TURN_IS_OVER
	}
	
	private static MainPlayerFieldController controller;
	private Deck deck;
	private Card lastSelectedCard;
	private CardsHandleObservable observableForHint;
	private CardsHandleObservable observableForReplacingCards;
	private MainPlayerState playerState;
	
	public void startGame()
	{
		deck = switch(PlayersController.getInstance().getNumeroGiocatoriInPartita())
					   {
					        case 2 -> new MazzoDiCarteBuilder()
							.shuffle()
							.build();
					        default-> new MazzoDiCarteBuilder()
					        .join(new MazzoDiCarteBuilder().build())
							.shuffle()
							.build(); 
					   };
	}
	
	/**
	 * Ritorna la carta successiva nel mazzo se presente
	 * @return carta successiva nel mazzo
	 * @throws GameNotInProgressException se la partita non è iniziata oppure è appena finita
	 * @throws DeckFinishedException se non ci sono più carte nel mazzo 
	 * @throws MoveNotAllowedException this move is not allowed for the current state
	 */
	public Card nextCard() throws GameNotInProgressException, DeckFinishedException, MoveNotAllowedException
	{
		Card carta;
		
		if (playerState != MainPlayerState.TURN_STARTED)
			throw new MoveNotAllowedException();
		if (deck == null)
			throw new GameNotInProgressException();
		try
		{
			carta = deck.nextCard();
		}
		catch(DeckFinishedException ex)
		{
			/* Partita finita. */
			deck = null;
			throw ex;
		}
		return carta;
	}
	
	public void cardSelectedFromDeck(Card carta) throws MoveNotAllowedException
	{
		if (playerState != MainPlayerState.TURN_STARTED)
			throw new MoveNotAllowedException();
		lastSelectedCard = carta;
		observableForHint.setStatusChanged();
		observableForHint.notifyObservers(lastSelectedCard);
		playerState = MainPlayerState.PICK_NEW_CARD_FROM_DECK;
	}
	
	public void cardSelectedFromTrash(Card card) throws MoveNotAllowedException
	{
		cardSelectedFromDeck(card);
		playerState = MainPlayerState.PICK_FROM_DISCARDED;
	}
	
	public Observable getObservableForHintCard()
	{
		return observableForHint;
	}
	
	public Observable getObservableForReplacingCards()
	{
		return observableForReplacingCards;
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
		if (playerState != MainPlayerState.PICK_FROM_DISCARDED && 
			playerState != MainPlayerState.PICK_NEW_CARD_FROM_DECK)
			throw new MoveNotAllowedException();
		if (lastSelectedCard == null || !goodCard(position + 1))
			return null;
		Card result = lastSelectedCard;
		
		observableForReplacingCards.setStatusChanged();
		observableForReplacingCards.notifyObservers(result);
		
		lastSelectedCard = null;
		playerState = MainPlayerState.TURN_IS_OVER;
		return result;
	}
	
	public static MainPlayerFieldController getInstance()
	{
		if (controller == null)
			controller = new MainPlayerFieldController();
		return controller;
	}
	
	private MainPlayerFieldController()
	{
		observableForHint = new CardsHandleObservable();
		observableForReplacingCards = new CardsHandleObservable();
		playerState = MainPlayerState.TURN_STARTED;
	}
}
