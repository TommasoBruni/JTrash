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
import eu.uniroma1.view.utils.DeckPosition;

public class ContainerPanel extends JPanel implements Resettable
{
	private CardsPanel pannelloCarte;
	private AvatarScorePanel pannelloAvatarPunteggio;
	
	@Override
	public void reset() 
	{
		pannelloCarte.reset();
	}
	
	public void updatePlayerController(PlayerController playerController)
	{
		pannelloCarte.updatePlayerController(playerController);
	}
	
	public ContainerPanel(CardsPanel pannelloCarte, AvatarScorePanel pannelloAvatarPunteggio,
						  DeckPosition posizioneDelMazzo)
	{
		this.pannelloAvatarPunteggio = pannelloAvatarPunteggio;
		this.pannelloCarte = pannelloCarte;
		GridBagConstraints gbc = new GridBagConstraints();
		setLayout(new GridBagLayout());

		/*
		setPreferredSize(new Dimension(this.pannelloAvatarPunteggio.getPreferredSize().width + this.pannelloCarte.getPreferredSize().width,
									   this.pannelloAvatarPunteggio.getPreferredSize().height + this.pannelloCarte.getPreferredSize().height));
		*/
		switch (posizioneDelMazzo) {
		case SULLA_SX:
			gbc.gridx = 0;
			gbc.gridy = 0;
			gbc.insets = new Insets(0, 0, 0, 30);
			/* Significa che è un giocatore laterale */
			add(this.pannelloCarte, gbc);
			
			gbc.gridx = 1;
			gbc.gridy = 0;
			gbc.insets = new Insets(0, 0, 0, 0);
			add(this.pannelloAvatarPunteggio, gbc);
			break;
		case SULLA_DX:
			/* Significa che è un giocatore laterale */
			gbc.gridx = 0;
			gbc.gridy = 0;
			gbc.insets = new Insets(0, 0, 0, 30);
			add(this.pannelloAvatarPunteggio, gbc);
			
			gbc.gridx = 1;
			gbc.gridy = 0;
			gbc.insets = new Insets(0, 0, 0, 0);
			add(this.pannelloCarte, gbc);
			break;
		case IN_BASSO:
			/* Significa che è un giocatore verticale */
			gbc.gridx = 0;
			gbc.gridy = 0;
			gbc.insets = new Insets(0, 0, 30, 0);
			add(this.pannelloAvatarPunteggio, gbc);
			
			gbc.gridx = 0;
			gbc.gridy = 1;
			gbc.insets = new Insets(0, 0, 0, 0);
			add(this.pannelloCarte, gbc);
			break;
		default:
			/* Significa che è un giocatore verticale */
			gbc.gridx = 0;
			gbc.gridy = 0;
			gbc.insets = new Insets(0, 0, 30, 0);
			add(this.pannelloCarte, gbc);
			
			gbc.gridx = 0;
			gbc.gridy = 1;
			gbc.insets = new Insets(0, 0, 0, 0);
			add(this.pannelloAvatarPunteggio, gbc);
		}
		
		setBackground(new Color(255, 255, 204));
	}
}
