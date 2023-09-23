package eu.uniroma1.view.panel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.Border;

import eu.uniroma1.controller.PlayerController;
import eu.uniroma1.controller.Resettable;
import eu.uniroma1.model.exceptions.DeckFinishedException;
import eu.uniroma1.model.exceptions.GameNotInProgressException;
import eu.uniroma1.model.exceptions.MoveNotAllowedException;
import eu.uniroma1.view.utils.DeckPosition;

/**
 * Container panel class 
 */
public class ContainerPanel extends JPanel implements Resettable
{
	private CardsPanelTemporaneo cardsPanel;
	private AvatarScorePanel scoreAvatarPanel;
	
	@Override
	public void reset() 
	{
		cardsPanel.reset();
	}
	
	/**
	 * Container panel class builder
	 * @param playerController player controller of the class
	 * @param positionOfDeck position of the deck relative to the user 
	 */
	public ContainerPanel(PlayerController playerController, DeckPosition positionOfDeck)
	{
		this.scoreAvatarPanel = new AvatarScorePanel(playerController);
		try 
		{
			this.cardsPanel = new CardsPanelTemporaneo(positionOfDeck, playerController);
		}
		catch (GameNotInProgressException | DeckFinishedException | MoveNotAllowedException e)
		{
			/* None will be launched because the match is set right now*/
			e.printStackTrace();
		}
		GridBagConstraints gbc = new GridBagConstraints();
		setLayout(new GridBagLayout());

		/*
		setPreferredSize(new Dimension(this.pannelloAvatarPunteggio.getPreferredSize().width + this.pannelloCarte.getPreferredSize().width,
									   this.pannelloAvatarPunteggio.getPreferredSize().height + this.pannelloCarte.getPreferredSize().height));
		*/
		switch (positionOfDeck) {
		case ON_THE_LEFT:
			gbc.gridx = 0;
			gbc.gridy = 0;
			gbc.insets = new Insets(0, 0, 0, 30);
			/* It is a lateral player */
			add(this.cardsPanel, gbc);
			
			gbc.gridx = 1;
			gbc.gridy = 0;
			gbc.insets = new Insets(0, 0, 0, 0);
			add(this.scoreAvatarPanel, gbc);
			break;
		case ON_THE_RIGHT:
			/* It is a lateral player */
			gbc.gridx = 0;
			gbc.gridy = 0;
			gbc.insets = new Insets(0, 0, 0, 30);
			add(this.scoreAvatarPanel, gbc);
			
			gbc.gridx = 1;
			gbc.gridy = 0;
			gbc.insets = new Insets(0, 0, 0, 0);
			add(this.cardsPanel, gbc);
			break;
		case DOWN:
			/* It is a vertical player */
			gbc.gridx = 0;
			gbc.gridy = 0;
			gbc.insets = new Insets(0, 0, 30, 0);
			add(this.scoreAvatarPanel, gbc);
			
			gbc.gridx = 0;
			gbc.gridy = 1;
			gbc.insets = new Insets(0, 0, 0, 0);
			add(this.cardsPanel, gbc);
			break;
		default:
			/* It is a vertical player */
			gbc.gridx = 0;
			gbc.gridy = 0;
			gbc.insets = new Insets(0, 0, 30, 0);
			add(this.cardsPanel, gbc);
			
			gbc.gridx = 0;
			gbc.gridy = 1;
			gbc.insets = new Insets(0, 0, 0, 0);
			add(this.scoreAvatarPanel, gbc);
		}
		
		setBackground(new Color(255, 255, 204));
	}
}
