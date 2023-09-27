package eu.uniroma1.controller;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.ImageIcon;

import eu.uniroma1.model.Player;
import eu.uniroma1.model.cards.*;
import eu.uniroma1.model.cards.Deck.DeckBuilder;
import eu.uniroma1.model.exceptions.DeckFinishedException;
import eu.uniroma1.model.exceptions.GameNotInProgressException;
import eu.uniroma1.model.exceptions.MoveNotAllowedException;

/**
 * Main player controller class 
 */
public class MainPlayerController extends PlayerController
{	
	private static MainPlayerController controller;
	private GenericObservable observableForHint;
	private static final String fileName = "my_account.ser";
	
	@Override
	public void operationWithSelectedCard(Card card)
	{
		observableForHint.setStatusChanged();
		observableForHint.notifyObservers(card);
	}
	
	@Override
	public synchronized void addObserver(Observer o) 
	{
		observableForHint.addObserver(o);
	}
	
	/**
	 * Method to get the main player controller instance.
	 * @return main player controller. 
	 */
	public static MainPlayerController getInstance()
	{
		PlayerData playerData;
		
		if (controller == null)
		{
			controller = new MainPlayerController();
			
			playerData = PlayerData.read(fileName);
			if (playerData != null)
				controller.playerData = playerData;
			
			controller.playerData.setFilename(fileName);
		}
		return controller;
	}
	
	private MainPlayerController()
	{
		observableForHint = new GenericObservable();
	}
}
