package eu.uniroma1.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.TimerTask;
import java.util.function.Consumer;

import javax.swing.ImageIcon;
import javax.swing.Timer;

import eu.uniroma1.model.carte.Card;
import eu.uniroma1.model.carte.Deck;
import eu.uniroma1.model.carte.Deck.MazzoDiCarteBuilder;
import eu.uniroma1.model.exceptions.DeckFinishedException;
import eu.uniroma1.model.exceptions.GameNotInProgressException;
import eu.uniroma1.model.exceptions.MoveNotAllowedException;

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
	private int numeroGiocatoriInPartita;
	private List<ImageIcon> enemiesIcon;
	private java.util.Timer nextTurnTimer;
	private Restartable itemToRestart;
	private static final int nextPlayerSpeed = 1000;
	
	public void nextTurn() 
	{
		/* E' il turno del prossimo giocatore */
		if (playerIndex > playerControllers.size() - 1)
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
	
	public EnemyController getNextEnemy()
	{
		if (enemyIndex > playerControllers.size() - 1)
			enemyIndex = 1;
		return (EnemyController)playerControllers.get(enemyIndex++);
	}
	
	public Card getLastTrashCard()
	{
		return lastTrashCard;
	}
	
	public void setLastTrashCard(Card lastTrashCard)
	{
		this.lastTrashCard = lastTrashCard;
	}
	
	public void setLastCardOfDeck(Card lastCardOfDeck)
	{
		this.lastCardOfDeck = lastCardOfDeck;
	}
	
	public void initializeDeck()
	{
		deck = switch(numeroGiocatoriInPartita)
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
	 * Call this method only when all data are configured 
	 */
	public void startGame()
	{
		int i = 0;
		
		playerControllers.removeAll(playerControllers);
		initializeDeck();
					   
		playerControllers.add(MainPlayerController.getInstance());
		/* Crea i nemici */
		switch(numeroGiocatoriInPartita)
		{
			case 4:
				playerControllers.add(new EnemyController(enemiesIcon.get(i++)));
			case 3:
				playerControllers.add(new EnemyController(enemiesIcon.get(i++)));
			default:
				/* Solo due giocatori */
				playerControllers.add(new EnemyController(enemiesIcon.get(i++)));
				break;
		}
		nextTurn();
	}
	
	@Override
	public void reset() 
	{
		initializeDeck();
		enemyIndex = 1;
		playerIndex = 0;
	}
	
	/**
	 * Ritorna la carta successiva nel mazzo se presente
	 * @return carta successiva nel mazzo
	 * @throws GameNotInProgressException se la partita non è iniziata oppure è appena finita
	 * @throws DeckFinishedException se non ci sono più carte nel mazzo 
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
			/* Partita finita. */
			deck = null;
			throw ex;
		}
		return carta;
	}
	
	public void backupCard()
	{
		deck.backupCard();
	}
	
	public void cardSelected(Card card) throws MoveNotAllowedException
	{
		currentPlayerController.newCardSelected(card);
	}
	
	public boolean canPeekCard(Card card)
	{
		return currentPlayerController.canPeekCard();
	}
	
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
	
	public void notifyForReplacing(Card card)
	{
		observableForReplacingCards.setStatusChanged();
		observableForReplacingCards.notifyObservers(card);
	}
	
	public void notifyForAutoSelecting()
	{
		observableForAutoSelectedCards.setStatusChanged();
		observableForAutoSelectedCards.notifyObservers();
	}
	
	public void trashLastSelectedCard()
	{
		currentPlayerController.trashLastSelectedCard();
	}
	
	public Observable getObservableForReplacingCards()
	{
		return observableForReplacingCards;
	}
	
	public Observable getObservableForTrashUpdating()
	{
		return observableForTrashUpdating;
	}
	
	public GenericObservable getObservableForGameFinish()
	{
		return observableForGameFinish;
	}
	
	public GenericObservable getObservableForAutoSelectedCards() 
	{
		return observableForAutoSelectedCards;
	}
	
	public void gameFinished(PlayerController victoryPlayer)
	{
		nextTurnTimer.cancel();
		observableForGameFinish.setStatusChanged();
		observableForGameFinish.notifyObservers(victoryPlayer);
		
		victoryPlayer.setCardsInHand(victoryPlayer.getCardsInHand() - 1);
		reset();
		
		itemToRestart.restart();
	}
	
	public List<ImageIcon> getEnemiesIcon() 
	{
		return enemiesIcon;
	}

	public void setEnemiesIcon(List<ImageIcon> enemiesIcon)
	{
		this.enemiesIcon = enemiesIcon;
	}
	
	/**
	 * Aggiorna i giocatori che attualmente stanno giocando (2, 3 o 4) 
	 * @param nGiocatori numero di giocatori attualmente in gioco
	 */
	public void updateNumberOfPlayers(int nGiocatori)
	{
		numeroGiocatoriInPartita = nGiocatori;
	}
	
	public Restartable getItemToRestart()
	{
		return itemToRestart;
	}

	public void setItemToRestart(Restartable itemToRestart) 
	{
		this.itemToRestart = itemToRestart;
	}
	
	/**
	 * Restituisce il numero di giocatori in partita
	 * @return numero giocatori attualmente in partita 
	 */
	public int getNumberOfPlayingPlayers()
	{
		return numeroGiocatoriInPartita;
	}
	
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
