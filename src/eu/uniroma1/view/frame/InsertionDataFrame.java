package eu.uniroma1.view.frame;

import java.awt.Dimension;
import java.awt.Frame;
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
		
		setPreferredSize(new Dimension(200, 200));
		
		containerPanel.add(usernameLabel);
		containerPanel.add(textFieldUsername);
		containerPanel.add(labelNickname);
		containerPanel.add(textFieldNickname);
		containerPanel.add(buttonSelectAvatar);
		containerPanel.add(buttonOk);
		containerPanel.add(buttonCancel);
		add(containerPanel);
	}
}
