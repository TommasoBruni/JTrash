package eu.uniroma1.view.panel;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

import eu.uniroma1.controller.MainPlayerController;
import eu.uniroma1.controller.PlayerController;
import eu.uniroma1.controller.PlayerData;
import eu.uniroma1.controller.Resettable;
import eu.uniroma1.model.Player;

public class AvatarScorePanel extends JPanel implements Observer
{
	private JLabel labelIcon;
	private JPanel pannelloPerPunteggi;
	private JLabel labelNicknameGiocatore;
	private JLabel labelPartiteGiocate;
	private JLabel labelPartiteVinte;
	private JLabel labelPartitePerse;
	private JLabel labelLevel;

	@Override
	public void update(Observable o, Object arg)
	{
		Player giocatore = (Player)arg;
		
		labelNicknameGiocatore.setText(giocatore.getNickname() + " ");
		labelIcon.setIcon(giocatore.getAvatar());
		
		if (labelPartiteGiocate != null)
		{
			labelPartiteGiocate.setText("Giocate: " + (giocatore.getPartitePerse() + giocatore.getPartiteVinte()) + " ");
			labelPartiteVinte.setText("Vinte: " + giocatore.getPartiteVinte() + " ");
			labelPartitePerse.setText("Perse: " + giocatore.getPartitePerse() + " ");
			labelLevel.setText(giocatore.getLevel().toString() + " ");
		}
	}
	
	public AvatarScorePanel(PlayerController playerController)
	{
		PlayerData playerData = playerController.getPlayerData();
		GridBagConstraints gbc = new GridBagConstraints();
		
		setLayout(new GridBagLayout());
		labelNicknameGiocatore = new JLabel(playerData.getPlayerName() + " ");
		labelIcon = new JLabel(playerData.getPlayerAvatar());
		setBackground(new Color(255, 255, 204));
		
		playerData.addObserver(this);	
		if (playerController instanceof MainPlayerController)
		{
			GridBagConstraints gbcPerPunteggi = new GridBagConstraints();
			
			pannelloPerPunteggi = new JPanel();
			pannelloPerPunteggi.setLayout(new GridBagLayout());
			/* This is the real player not a robot. */
			labelPartiteGiocate = new JLabel("Giocate: " + playerData.getPlayedGames() + " ");
			labelPartiteVinte = new JLabel("Vinte: " + playerData.getWonGames() + " ");
			labelPartitePerse = new JLabel("Perse: " + playerData.getLostGames() + " ");
			labelLevel = new JLabel(playerData.getLevel().toString() + " ");
			
			gbcPerPunteggi.gridx = 0;
			gbcPerPunteggi.gridy = 0;
			pannelloPerPunteggi.add(labelPartiteGiocate, gbcPerPunteggi);
			
			
			gbcPerPunteggi.gridx = 0;
			gbcPerPunteggi.gridy = 1;
			pannelloPerPunteggi.add(labelPartiteVinte, gbcPerPunteggi);

			gbcPerPunteggi.gridx = 0;
			gbcPerPunteggi.gridy = 2;
			pannelloPerPunteggi.add(labelPartitePerse, gbcPerPunteggi);
			
			gbcPerPunteggi.gridx = 0;
			gbcPerPunteggi.gridy = 3;
			pannelloPerPunteggi.add(labelLevel, gbcPerPunteggi);
			
			gbc.gridx = 1;
			gbc.gridy = 0;
			gbc.insets = new Insets(0, 10, 0, 0);
			add(pannelloPerPunteggi, gbc);
			pannelloPerPunteggi.setBackground(new Color(255, 255, 204));
		}
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.insets = new Insets(0, 0, 5, 0);
		
		add(labelIcon, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.insets = new Insets(0, 0, 0, 0);
		add(labelNicknameGiocatore, gbc);
	}
}
