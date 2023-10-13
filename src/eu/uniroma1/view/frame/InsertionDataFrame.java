package eu.uniroma1.view.frame;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.beans.PropertyVetoException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import eu.uniroma1.controller.MainPlayerController;
import eu.uniroma1.model.exceptions.DeckFinishedException;
import eu.uniroma1.model.exceptions.GameNotInProgressException;
import eu.uniroma1.controller.PlayerData;
import eu.uniroma1.view.dialog.AvatarSelectionDialog;

/**
 * Insertion data frame class
 */
public class InsertionDataFrame extends JInternalFrame 
{
	private JPanel containerPanel;
	private JLabel usernameLabel;
	private JTextField textFieldUsername;
	private JLabel labelNickname;
	private JTextField textFieldNickname;
	private JButton buttonOk;
	private JButton buttonCancel;
	private JButton buttonSelectAvatar;
	private AvatarSelectionDialog dialogSelectAvatar;
	
	/**
	 * Get selected avatar
	 * @return image icon of selected avatar 
	 */
	public ImageIcon getSelectedAvatar()
	{
		return dialogSelectAvatar.getSelectedAvatar();
	}
	
	/**
	 * Insertion data frame class builder
	 * @param frameParent frame parent of this frame
	 */
	public InsertionDataFrame(GameFrame frameParent)
	{
		super("Data insertion");
		GridBagConstraints gbc = new GridBagConstraints();
		
		usernameLabel = new JLabel("Username:");
		textFieldUsername = new JTextField(15);
		labelNickname = new JLabel("Nickname:");
		textFieldNickname = new JTextField(15);
		containerPanel = new JPanel();
		buttonOk = new JButton("Ok");
		buttonCancel = new JButton("Cancel");
		buttonOk.setPreferredSize(buttonCancel.getPreferredSize());
		buttonSelectAvatar = new JButton("Select avatar");
		dialogSelectAvatar = new AvatarSelectionDialog(new JFrame());
		
		buttonOk.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String username = textFieldUsername.getText();
				String nickname = textFieldNickname.getText();
				
				/* Check that they are not blank. */
				if (username.isBlank() || nickname.isBlank())
				{
					JOptionPane.showMessageDialog(new JFrame(), "Insert valid data!", "Error data insertion", JOptionPane.OK_OPTION);
					return;
				}
				
				if (dialogSelectAvatar.getSelectedAvatar() == null)
				{
					JOptionPane.showMessageDialog(new JFrame(), "Select avatar!", "Error data insertion", JOptionPane.OK_OPTION);
					return;
				}
					
				MainPlayerController.getInstance().getPlayerData().updatePlayerData(username, nickname, dialogSelectAvatar.getSelectedAvatar());
				/* Dispose will close the current window */
				dispose();
				frameParent.showEnemiesInsertionDialog();
			}
		});
		
		buttonCancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				/* Remove this window */
				dispose();
				frameParent.showInsertionNPlayersDialog();				
			}
		});
		
		buttonSelectAvatar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				dialogSelectAvatar.setVisible(true);
			}
		});
		
		//setPreferredSize(new Dimension(200, 200));
		containerPanel.setLayout(new GridBagLayout());
		
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.insets = new Insets(10, 0, 0, 0);
		
		containerPanel.add(usernameLabel, gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 1;
		gbc.insets = new Insets(10, 0, 0, 0);
		containerPanel.add(textFieldUsername, gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 2;
		gbc.insets = new Insets(10, 0, 0, 0);
		containerPanel.add(labelNickname, gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 3;
		gbc.insets = new Insets(10, 0, 0, 0);
		
		containerPanel.add(textFieldNickname, gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 4;
		gbc.insets = new Insets(10, 0, 0, 0);
		containerPanel.add(buttonSelectAvatar, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 5;
		gbc.insets = new Insets(0, 10, 10, 0);
		gbc.anchor = GridBagConstraints.CENTER;
		containerPanel.add(buttonOk, gbc);
		
		gbc.gridx = 2;
		gbc.gridy = 5;
		gbc.insets = new Insets(0, 0, 10, 10);
		gbc.anchor = GridBagConstraints.CENTER;
		containerPanel.add(buttonCancel, gbc);
		add(containerPanel);
		pack();
	}
}
