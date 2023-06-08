package eu.uniroma1.view.panel;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

import eu.uniroma1.controller.PlayingFieldController;
import eu.uniroma1.model.carte.Carte;
import eu.uniroma1.model.exceptions.MazzoFinitoException;
import eu.uniroma1.model.exceptions.PartitaNonInCorsoException;
import eu.uniroma1.view.button.ButtonCarta;
import eu.uniroma1.view.utils.PosizioneDelMazzo;

import javax.swing.*;

public class PannelloMazzoDiCarte extends JPanel
{
	private ButtonCarta carteDaPescare;
	private PannelloTrash trashSpace;
	private ButtonCarta cartaPescata;
	private JPanel contenitoreCarte;
	private boolean firstCard;
	
	public JButton getCarteDaPescareButton()
	{
		return carteDaPescare;
	}
	
	public PannelloMazzoDiCarte()
	{
		firstCard = true;
		try 
		{
			carteDaPescare = new ButtonCarta(PlayingFieldController.getInstance().prossimaCarta(), PosizioneDelMazzo.IN_ALTO);
			trashSpace = new PannelloTrash();
			cartaPescata = new ButtonCarta(PlayingFieldController.getInstance().prossimaCarta());
			cartaPescata.gira();
			cartaPescata.setVisible(false);
		} 
		catch (PartitaNonInCorsoException | MazzoFinitoException e)
		{
			/* Non accadrà mai stiamo creando adesso il campo di gioco */
			e.printStackTrace();
		}
		
		cartaPescata.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				PlayingFieldController.getInstance().lastSelectedCard(cartaPescata.getCarta());
			}
		});
		
		carteDaPescare.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				Carte carta;
				
				if (!firstCard)
				{
					try 
					{
						cartaPescata.changeCard(PlayingFieldController.getInstance().prossimaCarta());
					} 
					catch (PartitaNonInCorsoException | MazzoFinitoException e1) 
					{
						carteDaPescare.setVisible(false);
						JOptionPane.showMessageDialog(new JFrame(), "Non ci sono più carte!", "Partita finita!", JOptionPane.OK_OPTION);
						return;
					}
				}
				
				/* Se è la prima carta basta solo impostare il button visibile
				 * altrimenti bisogna cambiare la carta */
				cartaPescata.setVisible(true);
				carta = cartaPescata.getCarta();
				PlayingFieldController.getInstance().lastSelectedCard(carta);
				
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
		contenitoreCarte.add(cartaPescata, gbc);
        
		gbc.gridx = 2;
		gbc.gridy = 0;
		
		gbc.weightx = 0.01;
		gbc.weighty = 0.01;
		
		gbc.anchor = GridBagConstraints.LINE_START;
		
		/* Per inserire un po' di spazio tra le carte da pescare, la pescata e quelle scartate */
		gbc.insets = new Insets(0, 30, 0, 0);
		contenitoreCarte.add(trashSpace, gbc);
        
        add(contenitoreCarte);
	}
}
