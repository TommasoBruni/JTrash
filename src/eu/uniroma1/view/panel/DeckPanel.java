package eu.uniroma1.view.panel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

import eu.uniroma1.controller.FieldController;
import eu.uniroma1.controller.MainPlayerController;
import eu.uniroma1.controller.Resettable;
import eu.uniroma1.model.carte.Card;
import eu.uniroma1.model.exceptions.DeckFinishedException;
import eu.uniroma1.model.exceptions.GameNotInProgressException;
import eu.uniroma1.model.exceptions.MoveNotAllowedException;
import eu.uniroma1.view.button.CardButton;
import eu.uniroma1.view.utils.AudioManager;
import eu.uniroma1.view.utils.DeckPosition;

import javax.swing.*;

public class DeckPanel extends JPanel implements Observer, Resettable
{
	private CardButton carteDaPescare;
	private TrashPanel trashSpace;
	private CardButton cartaPescata;
	private JPanel cardsContainer;
	private JPanel pickedCardSpace;
	private boolean firstCard;

	@Override
	public void update(Observable o, Object arg) 
	{
		Card selectedCard = (Card)arg;
		Card discardedCard = trashSpace.getLastCard();
		Card pickedCard = cartaPescata.getCarta();
		
		if (selectedCard.equals(discardedCard))
			/* The user selects the card from the trash */
			trashSpace.removeCardFromTop();
		else if(selectedCard.equals(pickedCard))
			/* The user selects the card from the deck */
			cartaPescata.setVisible(false);
	}
	
	public void cardToPickEvent()
	{
		Card carta, oldCard;
		
		AudioManager.getInstance().play(System.getProperty("user.dir").concat("\\resources\\sliding_card.wav"));
		oldCard = cartaPescata.getCarta();
		if (!firstCard)
		{
			try 
			{
				cartaPescata.changeCard(FieldController.getInstance().nextCard());
			}
			catch (GameNotInProgressException | DeckFinishedException e1) 
			{
				carteDaPescare.setVisible(false);
				JOptionPane.showMessageDialog(new JFrame(), "Non ci sono più carte!", "Partita finita!", JOptionPane.OK_OPTION);
				return;
			}
		}
		
		carta = cartaPescata.getCarta();

		if (!FieldController.getInstance().canPickCard())
		{
			/* Fai il restore delle informazioni */
			FieldController.getInstance().backupCard();
			cartaPescata.changeCard(oldCard);
			return;
		}
		
		/* Se è la prima carta basta solo impostare il button visibile
		 * altrimenti bisogna cambiare la carta */
		cartaPescata.setVisible(true);
		
		FieldController.getInstance().setLastCardOfDeck(carta);
		
		firstCard = false;
		
		try
		{
			/* Fa partire il processo del giocatore che ha pescato la carta */
			FieldController.getInstance().cardSelected(carta);
		} 
		catch (MoveNotAllowedException e1)
		{
			/* Non può succedere dato che la canPickCard verifica questa situazione */
		}
	}
	
	@Override
	public void reset() 
	{
		firstCard = true;
		trashSpace.reset();
		try 
		{
			/* Questa non è mai girata */
			carteDaPescare.setBaseCard(FieldController.getInstance().nextCard());
		} 
		catch (GameNotInProgressException | DeckFinishedException e) 
		{
			/* Non può succedere stiamo riavviando la partita */
		}
		try 
		{
			cartaPescata.setBaseCard(FieldController.getInstance().nextCard());
			cartaPescata.gira();
			cartaPescata.setVisible(false);
		} 
		catch (GameNotInProgressException | DeckFinishedException e) 
		{
			/* Non può succedere stiamo riavviando la partita */
		}
	}
	
	public DeckPanel(Observable observable)
	{
		pickedCardSpace = new JPanel();
		observable.addObserver(this);
		firstCard = true;
		try 
		{
			carteDaPescare = new CardButton(FieldController.getInstance().nextCard(), DeckPosition.IN_ALTO);
			FieldController.getInstance().getObservableForTrashUpdating().addObserver(new Observer() {
				@Override
				public void update(Observable o, Object arg) {
					trashSpace.addCardToTop((Card)arg);
					cartaPescata.setVisible(false);
				}
			});
			
			FieldController.getInstance().getObservableForAutoSelectedCards().addObserver(new Observer() {
				@Override
				public void update(Observable o, Object arg) {
					cardToPickEvent();
				}
			});
			trashSpace = new TrashPanel();
			cartaPescata = new CardButton(FieldController.getInstance().nextCard());
			cartaPescata.gira();
			cartaPescata.setVisible(false);
		} 
		catch (GameNotInProgressException | DeckFinishedException e)
		{
			/* Non accadrà mai stiamo creando adesso il campo di gioco */
			e.printStackTrace();
		}
		
		carteDaPescare.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				cardToPickEvent();
			}
		});
		
        //setBorder(new EmptyBorder(10, 10, 10, 10));	
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
		
		gbc.weightx = 0.01;
		gbc.weighty = 0.01;
		gbc.anchor = GridBagConstraints.LINE_END;
        
		cardsContainer = new JPanel(new GridBagLayout());
		cardsContainer.add(carteDaPescare, gbc);
        
		gbc.gridx = 1;
		gbc.gridy = 0;
		
		gbc.weightx = 0.01;
		gbc.weighty = 0.01;
		
		gbc.anchor = GridBagConstraints.LINE_START;
		gbc.insets = new Insets(0, 5, 0, 0);
		
		pickedCardSpace.setPreferredSize(new Dimension(60, 63));
		pickedCardSpace.add(cartaPescata);
		pickedCardSpace.setBackground(new Color(255, 255, 204));
		cardsContainer.add(pickedCardSpace, gbc);
        
		gbc.gridx = 2;
		gbc.gridy = 0;
		
		gbc.weightx = 0.01;
		gbc.weighty = 0.01;
		
		gbc.anchor = GridBagConstraints.LINE_START;
		
		/* Per inserire un po' di spazio tra le carte da pescare, la pescata e quelle scartate */
		gbc.insets = new Insets(0, 30, 0, 0);
		cardsContainer.add(trashSpace, gbc);
		cardsContainer.setBackground(new Color(255, 255, 204));
        
		setPreferredSize(new Dimension(500, 95));
        add(cardsContainer);
        setBackground(new Color(255, 255, 204));
	}
}
