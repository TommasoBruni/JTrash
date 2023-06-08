package eu.uniroma1.view.panel;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.Border;

import eu.uniroma1.controller.PlayingFieldController;
import eu.uniroma1.model.exceptions.DeckFinishedException;
import eu.uniroma1.model.exceptions.GameNotInProgressException;
import eu.uniroma1.view.button.CardButton;
import eu.uniroma1.view.utils.interfaces.Closeable;

public class TrashPanel extends JPanel implements Closeable
{
	private CardButton carteScartate;
	
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
		try 
		{
			carteScartate = new CardButton(PlayingFieldController.getInstance().prossimaCarta());
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
		Border bordoComposto = BorderFactory.createCompoundBorder(bordoEsterno, bordoInterno);
		
		setBorder(bordoComposto);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        
		gbc.gridx = 0;
		gbc.gridy = 0;
		
		gbc.weightx = 0.01;
		gbc.weighty = 0.01;
		add(carteScartate, gbc);
	}
}
