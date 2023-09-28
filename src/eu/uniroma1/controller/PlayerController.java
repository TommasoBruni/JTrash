package eu.uniroma1.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.function.Consumer;

import eu.uniroma1.model.cards.Card;
import eu.uniroma1.model.cards.Value;
import eu.uniroma1.model.exceptions.DeckFinishedException;
import eu.uniroma1.model.exceptions.GameNotInProgressException;
import eu.uniroma1.model.exceptions.MoveNotAllowedException;

/**
 * Base class of players controller. 
 */
public abstract class PlayerController extends Observable implements Resettable, Restartable, Enableable
{
	protected PlayerState playerState;
	protected Card lastSelectedCard;
	protected GenericObservable collectedCardsObservable;
	protected List<Card> alreadyCollectedCards;
	protected PlayerData playerData;
	private int id;
	private static int counter;
	private boolean isEnabled;
	private int cardsInHand;
	private static int cardsForPlayer = 10;
	
	/**
	 * Customized operation that a controller does with the selected card.
	 * @param card needed to do the operations.
	 * @throws MoveNotAllowedException if the current state does not allow this move
	 */
	public abstract void operationWithSelectedCard(Card card) throws MoveNotAllowedException;
	
	/**
	 * Start turn method that needs to indicate that starts the controller turn.
	 * @throws GameNotInProgressException if the game is not started yet.
	 * @throws DeckFinishedException if the deck has no more cards.
	 **/
	public void startTurn() throws GameNotInProgressException, DeckFinishedException 
	{
		playerState = PlayerState.TURN_STARTED;
	}
	
	/**
	 * Finish turn method that needs to indicate that finishes the controller turn.
	 **/
	public void finishTurn() 
	{
		playerState = PlayerState.TURN_IS_OVER;
	}
	
	@Override
	public void enableObject() 
	{
		isEnabled = true;
	}
	
	@Override
	public void disableObject() 
	{
		isEnabled = false;
	}
	
	/**
	 * Get if the object is enabled
	 **/
	public boolean getIsEnabled()
	{
		return isEnabled;
	}
	
	@Override
	public void reset() 
	{
		finishTurn();
		alreadyCollectedCards = null;
	}
	
	/**
	 * Method used to setup the card obtained after the first card selected.
	 * Basically one of the card in the table.
	 * @param card to setup.
	 **/
	public void newCardSelectedForExchanging(Card card)
	{
		/* Ignore if the turn is over */
		lastSelectedCard = card;
		playerState = PlayerState.EXCHANGING;
	}
	
	/**
	 * Method used to set the cards already collected.
	 */
	public void setAlreadyCollectedCards(List<Card> collectedCards)
	{
		alreadyCollectedCards = collectedCards;
	}
	
	/**
	 * Method to indicate to trash the last selected card 
	 */
	public void trashLastSelectedCard()
	{
		FieldController.getInstance().newCardToTrash(lastSelectedCard);
		lastSelectedCard = null;
	}
	
	/**
	 * Get player data.
	 * @return player data.
	 */
	public PlayerData getPlayerData()
	{
		return playerData;
	}
	
	/**
	 * Check if the given position is good for the last selected card.
	 * @param position position to analyze.
	 * @return result of the check.
	 */
	private boolean goodCard(int position)
	{
		return lastSelectedCard.getValue().toString().equals("" + (position)) || 
			   lastSelectedCard.getValue().equals(Value.KING) ||
			   lastSelectedCard.getValue().equals(Value.JOLLY) ||
			   (lastSelectedCard.getValue().equals(Value.ACE) && position == 1);
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
	
	/**
	 * Returns the observable for the already collected cards.
	 * @return observable of the already collected cards. 
	 */
	public GenericObservable getCollectedCardsObservable()
	{
		return collectedCardsObservable;
	}
	
	/**
	 * Setup the last card selected.
	 * @param card last selected card.
	 * @throws MoveNotAllowedException if the state is different by {@link PlayerState.TURN_STARTED}
	 */
	public void newCardSelected(Card card) throws MoveNotAllowedException
	{
		if (playerState != PlayerState.TURN_STARTED)
			throw new MoveNotAllowedException();
		lastSelectedCard = card;
		
		operationWithSelectedCard(lastSelectedCard);
		
		playerState = PlayerState.PICKED_CARD;
	}
	
	/**
	 * To know if it's possible to pick a card.
	 * @return result of the check. 
	 */
	public boolean canPickCard()
	{
		return playerState == PlayerState.TURN_STARTED;
	}
	
	/**
	 * Get the number of cards in hand.
	 * @return number of cards in hand. 
	 */
	public int getCardsInHand() 
	{
		return cardsInHand;
	}

	/**
	 * Set the number of cards in hand.
	 * @return number of cards in hand.
	 */
	public void setCardsInHand(int nCardsInHand)
	{
		this.cardsInHand = nCardsInHand;
	}
	
	@Override
	public boolean equals(Object obj) 
	{
		if (!(obj instanceof PlayerController))
			return false;
		PlayerController controller = (PlayerController)obj;
		return controller.id == id;
	}
	
	@Override
	public void restart()
	{
		playerState = PlayerState.TURN_IS_OVER;
		cardsInHand = 2;
	}
	
	/**
	 * PlayerController builder
	 */
	public PlayerController()
	{
		id = counter++;
		playerState = PlayerState.TURN_IS_OVER;
		collectedCardsObservable = new GenericObservable();
		playerData = new PlayerData();
		cardsInHand = cardsForPlayer;
	}
}
