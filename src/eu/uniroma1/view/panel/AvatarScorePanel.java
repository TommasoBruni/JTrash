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

/**
 * Avatar score panel class 
 */
public class AvatarScorePanel extends JPanel implements Observer
{
	private JLabel labelIcon;
	private JPanel scorePanel;
	private JLabel playerNicknameLabel;
	private JLabel playedGamesLabel;
	private JLabel wonGamesLabel;
	private JLabel lostGamesLabel;
	private JLabel labelLevel;

	@Override
	public void update(Observable o, Object arg)
	{
		Player player = (Player)arg;
		
		playerNicknameLabel.setText(player.getNickname() + " ");
		labelIcon.setIcon(player.getAvatar());
		
		if (playedGamesLabel != null)
		{
			playedGamesLabel.setText("Played: " + (player.getLostMatches() + player.getWonMatches()) + " ");
			wonGamesLabel.setText("Won: " + player.getWonMatches() + " ");
			lostGamesLabel.setText("Lost: " + player.getLostMatches() + " ");
			labelLevel.setText(player.getLevel().toString() + " ");
		}
	}
	
	/**
	 * Avatar score panel builder 
	 */
	public AvatarScorePanel(PlayerController playerController)
	{
		PlayerData playerData = playerController.getPlayerData();
		GridBagConstraints gbc = new GridBagConstraints();
		
		setLayout(new GridBagLayout());
		playerNicknameLabel = new JLabel(playerData.getPlayerName() + " ");
		labelIcon = new JLabel(playerData.getPlayerAvatar());
		setBackground(new Color(255, 255, 204));
		
		playerData.addObserver(this);	
		if (playerController instanceof MainPlayerController)
		{
			GridBagConstraints gbcForScores = new GridBagConstraints();
			
			scorePanel = new JPanel();
			scorePanel.setLayout(new GridBagLayout());
			/* This is the real player not a robot. */
			playedGamesLabel = new JLabel("Played: " + playerData.getPlayedGames() + " ");
			wonGamesLabel = new JLabel("Won: " + playerData.getWonGames() + " ");
			lostGamesLabel = new JLabel("Lost: " + playerData.getLostGames() + " ");
			labelLevel = new JLabel(playerData.getLevel().toString() + " ");
			
			gbcForScores.gridx = 0;
			gbcForScores.gridy = 0;
			scorePanel.add(playedGamesLabel, gbcForScores);
			
			
			gbcForScores.gridx = 0;
			gbcForScores.gridy = 1;
			scorePanel.add(wonGamesLabel, gbcForScores);

			gbcForScores.gridx = 0;
			gbcForScores.gridy = 2;
			scorePanel.add(lostGamesLabel, gbcForScores);
			
			gbcForScores.gridx = 0;
			gbcForScores.gridy = 3;
			scorePanel.add(labelLevel, gbcForScores);
			
			gbc.gridx = 1;
			gbc.gridy = 0;
			gbc.insets = new Insets(0, 10, 0, 0);
			add(scorePanel, gbc);
			scorePanel.setBackground(new Color(255, 255, 204));
		}
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.insets = new Insets(0, 0, 5, 0);
		
		add(labelIcon, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.insets = new Insets(0, 0, 0, 0);
		add(playerNicknameLabel, gbc);
	}
}
