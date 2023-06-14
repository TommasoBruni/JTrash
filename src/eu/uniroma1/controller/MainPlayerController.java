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
	
	@Override
	public void operationWithSelectedCard(Card card)
	{
		observableForHint.setStatusChanged();
		observableForHint.notifyObservers(card);
	}
	
	public Observable getObservableForHintCard()
	{
		return observableForHint;
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
		playerState = PlayerState.TURN_IS_OVER;
	}
}
