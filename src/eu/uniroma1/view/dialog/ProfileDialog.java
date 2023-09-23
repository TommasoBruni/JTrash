package eu.uniroma1.view.dialog;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import eu.uniroma1.controller.Enableable;
import eu.uniroma1.controller.MainPlayerController;
import eu.uniroma1.controller.PlayerData;
import eu.uniroma1.view.frame.GameFrame;

/**
 * Profile dialog class 
 */
public class ProfileDialog extends JDialog
{
	private JLabel labelName;
	private JTextField fieldName;
	private JLabel labelNickname;
	private JTextField fieldNickname;
	private JButton buttonOk;
	private JButton cancelButton;
	private JButton buttonAvatarSelection;
	private AvatarSelectionDialog dialogSelezioneAvatar;
	
	/**
	 * Profile dialog class builder
	 * @param ownerFrame frame to make start the dialog
	 */
	public <T extends Frame & Enableable> ProfileDialog(T ownerFrame)
	{
		super(ownerFrame, "Profile", true);
		setResizable(false);
		JPanel containerPanel = new JPanel();
		
		labelName = new JLabel("Nome utente");
		fieldName = new JTextField(15);
		fieldName.setText(MainPlayerController.getInstance().getPlayerData().getPlayerName());
		labelNickname = new JLabel("Nickname");
		fieldNickname = new JTextField(15);
		fieldNickname.setText(MainPlayerController.getInstance().getPlayerData().getPlayerNickname());
		buttonOk = new JButton("Ok");
		cancelButton = new JButton("Annulla");
		buttonOk.setPreferredSize(cancelButton.getPreferredSize());
		buttonAvatarSelection = new JButton("Cambia avatar");
		dialogSelezioneAvatar = new AvatarSelectionDialog(new JFrame(), 
				MainPlayerController.getInstance().getPlayerData().getPlayerAvatar().getDescription());
		
		buttonAvatarSelection.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				dialogSelezioneAvatar.setVisible(true);
			}
		});
		
		buttonOk.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				String nomeUtente = fieldName.getText();
				String nickname = fieldNickname.getText();
				
				/* Check if they are not blank */
				if (nomeUtente.isBlank() || nickname.isBlank())
				{
					JOptionPane.showMessageDialog(new JFrame(), "Insert valid data!", "Error data insertion", JOptionPane.OK_OPTION);
					return;
				}
				
				MainPlayerController.getInstance().getPlayerData().updatePlayerData(nomeUtente, nickname, 
																	 dialogSelezioneAvatar.getSelectedAvatar() == null ?
																	 MainPlayerController.getInstance().getPlayerData().getPlayerAvatar() : dialogSelezioneAvatar.getSelectedAvatar());
				ownerFrame.enableObject();
				dispose();
			}
		});
		
		cancelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				ownerFrame.enableObject();
				dispose();
			}
		});
		
		//pannelloContenitore.setPreferredSize(new Dimension(250, 200));
		setPreferredSize(new Dimension(200, 200));
		containerPanel.add(labelName);
		containerPanel.add(fieldName);
		containerPanel.add(labelNickname);
		containerPanel.add(fieldNickname);
		containerPanel.add(buttonAvatarSelection);
		containerPanel.add(buttonOk);
		containerPanel.add(cancelButton);
		
		add(containerPanel);
		
		setLocationRelativeTo(ownerFrame);
		
		addWindowListener(new WindowListener() {
			
			@Override
			public void windowOpened(WindowEvent e) 
			{
				/* Not interesting */
			}
			
			@Override
			public void windowIconified(WindowEvent e) 
			{
				/* Not interesting */
			}
			
			@Override
			public void windowDeiconified(WindowEvent e) 
			{
				/* Not interesting */
			}
			
			@Override
			public void windowDeactivated(WindowEvent e) 
			{
				/* Not interesting */
			}
			
			@Override
			public void windowClosing(WindowEvent e) 
			{
				ownerFrame.enableObject();
			}
			
			@Override
			public void windowClosed(WindowEvent e) 
			{
				/* Not interesting */
			}
			
			@Override
			public void windowActivated(WindowEvent e) 
			{
				/* Not interesting */
			}
		});
		pack();
	}
}
