package eu.uniroma1.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.TimerTask;
import java.util.function.Consumer;

import javax.swing.ImageIcon;
import javax.swing.Timer;

import eu.uniroma1.model.cards.Card;
import eu.uniroma1.model.cards.Deck;
import eu.uniroma1.model.cards.Deck.DeckBuilder;
import eu.uniroma1.model.exceptions.DeckFinishedException;
import eu.uniroma1.model.exceptions.GameNotInProgressException;
import eu.uniroma1.model.exceptions.MoveNotAllowedException;

/**
 * Field controller class 
 */
public class FieldController extends Observable implements Resettable
{
	private static FieldController controller;
	private List<PlayerController> playerControllers;
	private PlayerController currentPlayerController;
	private GenericObservable observableForTrashUpdating;
	private GenericObservable observableForAutoSelectedCards;
	/**
	 * Needs to update trash or deck 
	 */
	private GenericObservable observableForReplacingCards;
	private GenericObservable observableForGameFinish;
	private Deck deck;
	private Card lastTrashCard;
	private Card lastCardOfDeck;
	private int playerIndex;
	private int enemyIndex;
	private int nPlayersInGame;
	private List<ImageIcon> enemiesIcon;
	private java.util.Timer nextTurnTimer;
	private Restartable itemToRestart;
	private static final int nextPlayerSpeed = 800;
	
	/**
	 * To notify the next controller to start the turn. 
	 */
	public void nextTurn() 
	{
		/* Is the next player turn */
		if (playerIndex > nPlayersInGame - 1)
			playerIndex = 0;
		currentPlayerController = playerControllers.get(playerIndex++);
		try 
		{
			currentPlayerController.startTurn();
		}
		catch (GameNotInProgressException | DeckFinishedException e) 
		{
			setChanged();
			notifyObservers();
		}
	}
	
	/**
	 * Get next instance of enemy.
	 * @return new enemy. 
	 */
	public EnemyController getNextEnemy()
	{
		if (enemyIndex > nPlayersInGame - 1)
			enemyIndex = 1;
		for (; enemyIndex < nPlayersInGame; enemyIndex++)
		{
			if (!playerControllers.get(enemyIndex).getIsEnabled())
			{
				playerControllers.get(enemyIndex).enableObject();
				return (EnemyController)playerControllers.get(enemyIndex++);
			}
		}
		return null;
	}
	
	/**
	 * Return the last trashed card.
	 * @return last trashed card.
	 */
	public Card getLastTrashCard()
	{
		return lastTrashCard;
	}
	
	/**
	 * Set last trashed card.
	 * @param lastTrashCard last trashed card.
	 */
	public void setLastTrashCard(Card lastTrashCard)
	{
		this.lastTrashCard = lastTrashCard;
	}
	
	/**
	 * Set last card of the deck.
	 * @param lastCardOfDeck last card of deck.
	 */
	public void setLastCardOfDeck(Card lastCardOfDeck)
	{
		this.lastCardOfDeck = lastCardOfDeck;
	}
	
	/**
	 * Initialize and shuffle the deck. 
	 */
	public void initializeDeck()
	{
		deck = switch(nPlayersInGame)
				   {
				        case 2 -> new DeckBuilder()
						.shuffle()
						.build();
				        default-> new DeckBuilder()
				        .join(new DeckBuilder().build())
						.shuffle()
						.build(); 
				   };
	}
	
	/**
	 * Start the game notifying the next turn.
	 * Call this method only when all data are configured 
	 */
	public void startGame()
	{
		nextTurn();
	}
	
	/**
	 * Initialize all data necessary to start the game. 
	 */
	public void initializeComponents()
	{
		int i;
		PlayerController current;
		
		initializeDeck();
		
		if (playerControllers.size() == 0)
			playerControllers.add(MainPlayerController.getInstance());
		
		for (i = 1; i < nPlayersInGame; i++)
		{
			if (i + 1 > playerControllers.size())
			{
				current = new EnemyController(enemiesIcon.get(i - 1));
				playerControllers.add(current);
			}
			else
			{
				current = playerControllers.get(i);
				current.playerData.updatePlayerData(enemiesIcon.get(i - 1).getDescription(),
														 enemiesIcon.get(i - 1).getDescription(),
						                                 enemiesIcon.get(i - 1));
				current.enableObject();
			}
		}
		
		for (; i < playerControllers.size(); i++)
			playerControllers.get(i).disableObject();
	}
	
	@Override
	public void reset() 
	{
		enemyIndex = 1;
		playerIndex = 0;
	}
	
	/**
	 * Returns the next card of the deck if present.
	 * @return next card of deck.
	 * @throws GameNotInProgressException if the game is not started yet, or it is just finished.
	 * @throws DeckFinishedException if there are no more cards in the deck.
	 * @throws MoveNotAllowedException this move is not allowed for the current state
	 */
	public Card nextCard() throws GameNotInProgressException, DeckFinishedException
	{
		Card carta;
		
		if (deck == null)
			throw new GameNotInProgressException();
		try
		{
			carta = deck.nextCard();
		}
		catch(DeckFinishedException ex)
		{
			/* Game finished */
			deck = null;
			throw ex;
		}
		return carta;
	}
	
	/**
	 * Method to come back to the previous card in the deck. 
	 */
	public void backupCard()
	{
		deck.backupCard();
	}
	
	/**
	 * Last card selected.
	 * @param card last card selected.
	 * @throws MoveNotAllowedException this move is not allowed for the current state
	 */
	public void cardSelected(Card card) throws MoveNotAllowedException
	{
		currentPlayerController.newCardSelected(card);
	}
	
	/**
	 * Check if the current controller can pick a card. 
	 * @return result of the check.
	 */
	public boolean canPickCard()
	{
		return currentPlayerController.canPickCard();
	}
	
	/**
	 * To notify that there is a new card to trash.
	 * @param card card to trash.
	 */
	public void newCardToTrash(Card card)
	{
		observableForTrashUpdating.setStatusChanged();
		observableForTrashUpdating.notifyObservers(card);
		currentPlayerController.finishTurn();
		
		nextTurnTimer.schedule(new TimerTask() {
			@Override
			public void run() 
			{
				nextTurn();
			}
		}, nextPlayerSpeed);
	}
	
	/**
	 * Needs to notify to the observers to replace the current card from the stack.
	 * @param card to replace.
	 */
	public void notifyForReplacing(Card card)
	{
		observableForReplacingCards.setStatusChanged();
		observableForReplacingCards.notifyObservers(card);
	}
	
	/**
	 * Notifies observers to move as if the user has made a choice. 
	 */
	public void notifyForAutoSelecting()
	{
		observableForAutoSelectedCards.setStatusChanged();
		observableForAutoSelectedCards.notifyObservers();
	}
	
	/**
	 * Notify the current controller to trash the last selected card.
	 */
	public void trashLastSelectedCard()
	{
		currentPlayerController.trashLastSelectedCard();
	}
	
	/**
	 * Get observable for replacing cards events.
	 * @return observable for replacing cards.
	 */
	public Observable getObservableForReplacingCards()
	{
		return observableForReplacingCards;
	}
	
	/**
	 * Get observable for trash updating.
	 * @return the observable helpful to notify the trash about updates. 
	 */
	public Observable getObservableForTrashUpdating()
	{
		return observableForTrashUpdating;
	}
	
	/**
	 * Get observable to notify the end of the game.
	 * @return observable for the end of the game.
	 */
	public GenericObservable getObservableForGameFinish()
	{
		return observableForGameFinish;
	}
	
	/**
	 * Get observable for auto selected cards.
	 * @return observable for auto selected cards. 
	 */
	public GenericObservable getObservableForAutoSelectedCards() 
	{
		return observableForAutoSelectedCards;
	}
	
	/**
	 * Method to indicate the end of the game.
	 * @param victory player controller. 
	 */
	public void gameFinished(PlayerController victoryPlayer)
	{
		PlayerData playerData;
		
		playerControllers.forEach((playerController) -> playerController.reset());
		playerControllers.forEach((playerController) ->
								  { 
										if (playerController.equals(victoryPlayer))
											playerController.setCardsInHand(playerController.getCardsInHand() - 1);
								  });
		reset();
		
		observableForGameFinish.setStatusChanged();
		observableForGameFinish.notifyObservers(victoryPlayer);
		
		if (victoryPlayer.getCardsInHand() == 0)
		{
			playerData = MainPlayerController.getInstance().getPlayerData();
			if (victoryPlayer.equals(MainPlayerController.getInstance()))
				playerData.increasePlayerWonGames();
			else
				playerData.increasePlayerLostGames();
			
			/* This is to update won and lost games */
			playerData.updatePlayerData(playerData.getPlayerName(), playerData.getPlayerNickname(), playerData.getPlayerAvatar());
			playerControllers.forEach(controller -> controller.restart());
		}
		itemToRestart.restart();
	}
	
	/**
	 * Get the list of enemies icons.
	 * @return list of enemies icons. 
	 */
	public List<ImageIcon> getEnemiesIcon() 
	{
		return enemiesIcon;
	}

	/**
	 * Set list of enemies icons.
	 * @param list of enemies icons.
	 */
	public void setEnemiesIcon(List<ImageIcon> enemiesIcon)
	{
		this.enemiesIcon = enemiesIcon;
	}
	
	/**
	 * Updates current playing players (2, 3 or 4).
	 * @param nPlayers number of players currently in the game
	 */
	public void updateNumberOfPlayers(int nPlayers)
	{
		nPlayersInGame = nPlayers;
	}
	
	/**
	 * Returns number of players currently in the game.
	 * @return number of players currently in the game
	 */
	public int getNumberOfPlayingPlayers()
	{
		return nPlayersInGame;
	}

	/**
	 * Set item that needs to be restarted.
	 */
	public void setItemToRestart(Restartable itemToRestart) 
	{
		this.itemToRestart = itemToRestart;
	}
	
	/**
	 * Method to get the instance of field controller (it is a singleton).
	 * @return field controller. 
	 */
	public static FieldController getInstance()
	{
		if (controller == null)
			controller = new FieldController();
		return controller;
	}
	
	private FieldController()
	{
		enemyIndex = 1;
		playerControllers = new ArrayList<>();
		observableForTrashUpdating = new GenericObservable();
		observableForReplacingCards = new GenericObservable();
		observableForAutoSelectedCards = new GenericObservable();
		observableForGameFinish = new GenericObservable();
		nextTurnTimer = new java.util.Timer();
	}
}
