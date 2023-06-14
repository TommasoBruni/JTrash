package eu.uniroma1.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.function.Consumer;

import eu.uniroma1.model.carte.Card;
import eu.uniroma1.model.carte.Deck;
import eu.uniroma1.model.carte.Deck.MazzoDiCarteBuilder;
import eu.uniroma1.model.exceptions.DeckFinishedException;
import eu.uniroma1.model.exceptions.GameNotInProgressException;
import eu.uniroma1.model.exceptions.MoveNotAllowedException;

public class FieldController extends Observable
{
	private static FieldController controller;
	/**
	 * Needs to be notified by one of controller that the turn of
	 * that player is finished 
	 */
	private List<PlayerController> playerControllers;
	private PlayerController currentPlayerController;
	private CardsHandleObservable observableForTrashUpdating;
	private Deck deck;
	private int playerIndex;
	private int enemyIndex;
	
	public void nextTurn() 
	{
		/* E' il turno del prossimo giocatore */
		if (playerIndex > playerControllers.size() - 1)
			playerIndex = 0;
		currentPlayerController = playerControllers.get(playerIndex++);
		currentPlayerController.startTurn();
	}
	
	public EnemyController getNextEnemy()
	{
		if (enemyIndex > playerControllers.size() - 1)
			playerIndex = 1;
		return (EnemyController)playerControllers.get(enemyIndex);
	}
	
	/**
	 * Call this method only when all data are configured 
	 */
	public void startGame()
	{
		playerControllers.removeAll(playerControllers);
		deck = switch(PlayerDataController.getInstance().getNumeroGiocatoriInPartita())
					   {
					        case 2 -> new MazzoDiCarteBuilder()
							.shuffle()
							.build();
					        default-> new MazzoDiCarteBuilder()
					        .join(new MazzoDiCarteBuilder().build())
							.shuffle()
							.build(); 
					   };
					   
		playerControllers.add(MainPlayerController.getInstance());
		/* Crea i nemici */
		switch(PlayerDataController.getInstance().getNumeroGiocatoriInPartita())
		{
			case 4:
				playerControllers.add(new EnemyController());
			case 3:
				playerControllers.add(new EnemyController());
			default:
				/* Solo due giocatori */
				playerControllers.add(new EnemyController());
				break;
		}
		currentPlayerController = playerControllers.get(0);
		currentPlayerController.startTurn();
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
	
	public void cardSelected(Card card) throws MoveNotAllowedException
	{
		currentPlayerController.newCardSelected(card);
	}
	
	public void newCardToTrash(Card card)
	{
		observableForTrashUpdating.setStatusChanged();
		observableForTrashUpdating.notifyObservers(card);
		currentPlayerController.finishTurn();
		nextTurn();
	}
	
	public Observable getObservableForTrashUpdating()
	{
		return observableForTrashUpdating;
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
		observableForTrashUpdating = new CardsHandleObservable();
	}
}
