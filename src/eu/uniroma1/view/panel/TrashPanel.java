package eu.uniroma1.view.panel;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.Border;

import eu.uniroma1.controller.PlayingFieldController;
import eu.uniroma1.model.carte.Card;
import eu.uniroma1.model.exceptions.DeckFinishedException;
import eu.uniroma1.model.exceptions.GameNotInProgressException;
import eu.uniroma1.view.button.CardButton;
import eu.uniroma1.view.utils.interfaces.Closeable;

public class TrashPanel extends JPanel implements Closeable
{
	private CardButton carteScartate;
	private List<Card> discardedCards;
	private Border compoundBorder;
	private JPanel internalPanel;
	
	public Card getLastCard()
	{
		if (discardedCards.size() > 0)
			return discardedCards.get(discardedCards.size() - 1);
		return null;
	}
	
	public void removeCardFromTop()
	{
		discardedCards.remove(discardedCards.size() - 1);
		if (discardedCards.size() > 0)
			carteScartate.changeCard(discardedCards.get(discardedCards.size() - 1));
		else
			carteScartate.setVisible(false);
	}
	
	@Override
	public void enableComponent() 
	{
		setEnabled(true);
	}

	@Override
	public void disableComponent() 
	{
		setEnabled(false);
	}
	
	public TrashPanel()
	{
		discardedCards = new ArrayList<>();
		try 
		{
			Card firstCard = PlayingFieldController.getInstance().prossimaCarta();
			
			discardedCards.add(firstCard);
			carteScartate = new CardButton(firstCard);
			carteScartate.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e)
				{
					PlayingFieldController.getInstance().lastSelectedCard(carteScartate.getCarta());
				}
			});
			carteScartate.gira();
		} 
		catch (GameNotInProgressException | DeckFinishedException e)
		{
			/* Non succederà mai, stiamo creando ora il campo di gioco */
		}
		Border bordoInterno = BorderFactory.createTitledBorder("Trash");
		Border bordoEsterno = BorderFactory.createEmptyBorder(0, 5, 5, 5);
		compoundBorder = BorderFactory.createCompoundBorder(bordoEsterno, bordoInterno);
		
		internalPanel = new JPanel();
		internalPanel.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		
		gbc.weightx = 0.01;
		gbc.weighty = 0.01;
		internalPanel.add(carteScartate, gbc);
		
		setBorder(compoundBorder);
		setPreferredSize(new Dimension(70, 90));
		add(internalPanel);
		/*
		setBorder(compoundBorder);
		setPreferredSize(new Dimension(70, 85));
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        
		gbc.gridx = 0;
		gbc.gridy = 0;
		
		gbc.weightx = 0.01;
		gbc.weighty = 0.01;
		add(carteScartate, gbc);
		*/
	}
}
