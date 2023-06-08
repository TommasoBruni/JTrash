package eu.uniroma1.controller;

import java.util.List;
import java.util.Observable;

import javax.swing.ImageIcon;

import eu.uniroma1.model.Player;
import eu.uniroma1.model.carte.*;
import eu.uniroma1.model.carte.Deck.MazzoDiCarteBuilder;
import eu.uniroma1.model.exceptions.DeckFinishedException;
import eu.uniroma1.model.exceptions.GameNotInProgressException;

public class MainPlayerFieldController
{
	private static MainPlayerFieldController controller;
	private Deck deck;
	private Card lastSelectedCard;
	private CardsHandleObservable observableForHint;
	private CardsHandleObservable observableForReplacingCards;
	
	public void startGame()
	{
		deck = switch(PlayersController.getInstance().getNumeroGiocatoriInPartita())
					   {
					        case 2 -> new MazzoDiCarteBuilder()
							.mischia()
							.build();
					        default-> new MazzoDiCarteBuilder()
					        .combina(new MazzoDiCarteBuilder().build())
							.mischia()
							.build(); 
					   };
	}
	
	/**
	 * Ritorna la carta successiva nel mazzo se presente
	 * @return carta successiva nel mazzo
	 * @throws GameNotInProgressException se la partita non è iniziata oppure è appena finita
	 * @throws DeckFinishedException se non ci sono più carte nel mazzo 
	 */
	public Card prossimaCarta() throws GameNotInProgressException, DeckFinishedException
	{
		Card carta;
		
		if (deck == null)
			throw new GameNotInProgressException();
		try
		{
			carta = deck.prossimaCarta();
		}
		catch(DeckFinishedException ex)
		{
			/* Partita finita. */
			deck = null;
			throw ex;
		}
		return carta;
	}
	
	public void lastSelectedCard(Card carta)
	{
		lastSelectedCard = carta;
		observableForHint.setStatusChanged();
		observableForHint.notifyObservers(lastSelectedCard);
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
	 * @return {@link Card} card or null if the position doesn't match the value
	 * of the last card
	 */
	public Card getCardForReplacing(int position)
	{
		if (lastSelectedCard == null || !goodCard(position + 1))
			return null;
		Card result = lastSelectedCard;
		
		observableForReplacingCards.setStatusChanged();
		observableForReplacingCards.notifyObservers(result);
		
		lastSelectedCard = null;
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
	}
}
