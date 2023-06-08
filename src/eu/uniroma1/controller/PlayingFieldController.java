package eu.uniroma1.controller;

import java.util.List;
import java.util.Observable;

import javax.swing.ImageIcon;

import eu.uniroma1.model.Giocatore;
import eu.uniroma1.model.carte.*;
import eu.uniroma1.model.carte.MazzoDiCarte.MazzoDiCarteBuilder;
import eu.uniroma1.model.eccezioni.MazzoFinitoException;
import eu.uniroma1.model.eccezioni.PartitaNonInCorsoException;

public class PlayingFieldController
{
	private static PlayingFieldController controller;
	private MazzoDiCarte deck;
	private Carte lastSelectedCard;
	private ObservableForHintCard observableForHint;
	
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
	 * @throws PartitaNonInCorsoException se la partita non è iniziata oppure è appena finita
	 * @throws MazzoFinitoException se non ci sono più carte nel mazzo 
	 */
	public Carte prossimaCarta() throws PartitaNonInCorsoException, MazzoFinitoException
	{
		Carte carta;
		
		if (deck == null)
			throw new PartitaNonInCorsoException();
		try
		{
			carta = deck.prossimaCarta();
		}
		catch(MazzoFinitoException ex)
		{
			/* Partita finita. */
			deck = null;
			throw ex;
		}
		return carta;
	}
	
	public void lastSelectedCard(Carte carta)
	{
		lastSelectedCard = carta;
		observableForHint.setStatusChanged();
		observableForHint.notifyObservers(lastSelectedCard);
	}
	
	public Observable getObservableForHintCard()
	{
		return observableForHint;
	}
	
	private boolean goodCard(int position)
	{
		return lastSelectedCard.getValore().toString().equals("" + (position)) || 
			   lastSelectedCard.getValore().toString().equals(Valori.KING.toString()) ||
			   lastSelectedCard.getValore().toString().equals(Valori.JOLLY.toString()) ||
			   (lastSelectedCard.getValore().toString().equals(Valori.ASSO.toString()) && position == 1);
	}
	
	/**
	 * With the given position checks if the last card selected is
	 * good one for replacing.
	 * If yes return the last card selected otherwise null.
	 * @param position of the current client
	 * @return {@link Carte} card or null if the position doesn't match the value
	 * of the last card
	 */
	public Carte getCardForReplacing(int position)
	{
		if (lastSelectedCard == null || !goodCard(position + 1))
			return null;
		Carte result = lastSelectedCard;
		
		lastSelectedCard = null;
		return result;
	}
	
	public static PlayingFieldController getInstance()
	{
		if (controller == null)
			controller = new PlayingFieldController();
		return controller;
	}
	
	private PlayingFieldController()
	{
		observableForHint = new ObservableForHintCard();
	}
}
