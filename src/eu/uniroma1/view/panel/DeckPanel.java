package eu.uniroma1.view.panel;

import java.awt.BorderLayout;
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

import eu.uniroma1.controller.MainPlayerFieldController;
import eu.uniroma1.model.carte.Card;
import eu.uniroma1.model.exceptions.DeckFinishedException;
import eu.uniroma1.model.exceptions.GameNotInProgressException;
import eu.uniroma1.model.exceptions.MoveNotAllowedException;
import eu.uniroma1.view.button.CardButton;
import eu.uniroma1.view.utils.DeckPosition;

import javax.swing.*;

public class DeckPanel extends JPanel implements Observer
{
	private CardButton carteDaPescare;
	private TrashPanel trashSpace;
	private CardButton cartaPescata;
	private JPanel contenitoreCarte;
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
	
	public DeckPanel(Observable observable)
	{
		pickedCardSpace = new JPanel();
		observable.addObserver(this);
		firstCard = true;
		try 
		{
			carteDaPescare = new CardButton(MainPlayerFieldController.getInstance().nextCard(), DeckPosition.IN_ALTO);
			trashSpace = new TrashPanel(MainPlayerFieldController.getInstance().getObservableForTrashUpdating());
			cartaPescata = new CardButton(MainPlayerFieldController.getInstance().nextCard());
			cartaPescata.gira();
			cartaPescata.setVisible(false);
		} 
		catch (GameNotInProgressException | MoveNotAllowedException | DeckFinishedException e)
		{
			/* Non accadrà mai stiamo creando adesso il campo di gioco */
			e.printStackTrace();
		}
		
		carteDaPescare.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				Card carta;
				
				if (!firstCard)
				{
					try 
					{
						cartaPescata.changeCard(MainPlayerFieldController.getInstance().nextCard());
					}
					catch(MoveNotAllowedException e1)
					{
						return;
					}
					catch (GameNotInProgressException | DeckFinishedException e1) 
					{
						carteDaPescare.setVisible(false);
						JOptionPane.showMessageDialog(new JFrame(), "Non ci sono più carte!", "Partita finita!", JOptionPane.OK_OPTION);
						return;
					}
				}
				
				carta = cartaPescata.getCarta();
				
				try 
				{
					MainPlayerFieldController.getInstance().cardSelectedFromDeck(carta);
				} 
				catch (MoveNotAllowedException e1) 
				{
					return;
				}
				
				/* Se è la prima carta basta solo impostare il button visibile
				 * altrimenti bisogna cambiare la carta */
				cartaPescata.setVisible(true);
				
				firstCard = false;
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
        
		contenitoreCarte = new JPanel(new GridBagLayout());
		contenitoreCarte.add(carteDaPescare, gbc);
        
		gbc.gridx = 1;
		gbc.gridy = 0;
		
		gbc.weightx = 0.01;
		gbc.weighty = 0.01;
		
		gbc.anchor = GridBagConstraints.LINE_START;
		gbc.insets = new Insets(0, 5, 0, 0);
		
		pickedCardSpace.setPreferredSize(new Dimension(60, 63));
		pickedCardSpace.add(cartaPescata);
		contenitoreCarte.add(pickedCardSpace, gbc);
        
		gbc.gridx = 2;
		gbc.gridy = 0;
		
		gbc.weightx = 0.01;
		gbc.weighty = 0.01;
		
		gbc.anchor = GridBagConstraints.LINE_START;
		
		/* Per inserire un po' di spazio tra le carte da pescare, la pescata e quelle scartate */
		gbc.insets = new Insets(0, 30, 0, 0);
		contenitoreCarte.add(trashSpace, gbc);
        
		setPreferredSize(new Dimension(500, 95));
        add(contenitoreCarte);
	}
}
