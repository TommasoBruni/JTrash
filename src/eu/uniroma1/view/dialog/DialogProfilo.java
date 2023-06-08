package eu.uniroma1.view.dialog;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

import eu.uniroma1.controller.PlayingFieldController;
import eu.uniroma1.controller.PlayersController;
import eu.uniroma1.view.frame.GameFrame;
import eu.uniroma1.view.utils.interfaces.Closeable;

public class DialogProfilo extends JDialog
{
	private JLabel labelNome;
	private JTextField fieldNome;
	private JLabel labelNickname;
	private JTextField fieldNickname;
	private JButton buttonOk;
	private JButton buttonAnnulla;
	private JButton buttonSelezionaAvatar;
	private DialogSelezioneAvatar dialogSelezioneAvatar;
	
	public <T extends Frame & Closeable> DialogProfilo(T ownerFrame)
	{
		super(ownerFrame, "Profilo", true);
		setResizable(false);
		JPanel pannelloContenitore = new JPanel();
		
		labelNome = new JLabel("Nome utente");
		fieldNome = new JTextField(15);
		fieldNome.setText(PlayersController.getInstance().getNomeGiocatore());
		labelNickname = new JLabel("Nickname");
		fieldNickname = new JTextField(15);
		fieldNickname.setText(PlayersController.getInstance().getNicknameGiocatore());
		buttonOk = new JButton("Ok");
		buttonAnnulla = new JButton("Annulla");
		buttonOk.setPreferredSize(buttonAnnulla.getPreferredSize());
		buttonSelezionaAvatar = new JButton("Cambia avatar");
		dialogSelezioneAvatar = new DialogSelezioneAvatar(new JFrame(), 
				PlayersController.getInstance().getAvatarGiocatore().getDescription());
		
		buttonSelezionaAvatar.addActionListener(new ActionListener() {
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
				String nomeUtente = fieldNome.getText();
				String nickname = fieldNickname.getText();
				
				/* Si controlla che non siano vuoti e che non contengano solamente spazi */
				if (nomeUtente.isBlank() || nickname.isBlank())
				{
					JOptionPane.showMessageDialog(new JFrame(), "Inserire dati validi!", "Errore inserimento dati", JOptionPane.OK_OPTION);
					return;
				}
				
				PlayersController.getInstance().aggiornaDatiGiocatore(nomeUtente, nickname, 
																	 dialogSelezioneAvatar.getSelectedAvatar() == null ?
																	 PlayersController.getInstance().getAvatarGiocatore() : dialogSelezioneAvatar.getSelectedAvatar());
				ownerFrame.enableComponent();
				dispose();
			}
		});
		
		buttonAnnulla.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				ownerFrame.enableComponent();
				dispose();
			}
		});
		
		//pannelloContenitore.setPreferredSize(new Dimension(250, 200));
		setPreferredSize(new Dimension(200, 200));
		pannelloContenitore.add(labelNome);
		pannelloContenitore.add(fieldNome);
		pannelloContenitore.add(labelNickname);
		pannelloContenitore.add(fieldNickname);
		pannelloContenitore.add(buttonSelezionaAvatar);
		pannelloContenitore.add(buttonOk);
		pannelloContenitore.add(buttonAnnulla);
		
		add(pannelloContenitore);
		
		setLocationRelativeTo(ownerFrame);
		pack();
	}
}
