package eu.uniroma1.view.frame;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dialog;
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
import eu.uniroma1.controller.PlayerDataController;
import eu.uniroma1.view.dialog.AvatarSelectionDialog;

public class InsertionDataFrame extends JInternalFrame 
{
	private JPanel pannelloContenitore;
	private JLabel labelNomeUtente;
	private JTextField textFieldNomeUtente;
	private JLabel labelNickname;
	private JTextField textFieldNickname;
	private JButton buttonOk;
	private JButton buttonAnnulla;
	private JButton buttonSelezionaAvatar;
	private AvatarSelectionDialog dialogSelezioneAvatar;
	
	public ImageIcon getAvatarSelezionato()
	{
		return dialogSelezioneAvatar.getSelectedAvatar();
	}
	
	public InsertionDataFrame(Container parentComponent, GameFrame frameParent)
	{
		super("Inserimento dati",
		          false, //resizable
		          false, //closable
		          false, //maximizable
		          false);
		labelNomeUtente = new JLabel("Nome utente:");
		textFieldNomeUtente = new JTextField(15);
		labelNickname = new JLabel("Nickname:");
		textFieldNickname = new JTextField(15);
		pannelloContenitore = new JPanel();
		buttonOk = new JButton("Ok");
		buttonAnnulla = new JButton("Annulla");
		buttonOk.setPreferredSize(buttonAnnulla.getPreferredSize());
		buttonSelezionaAvatar = new JButton("Seleziona avatar");
		dialogSelezioneAvatar = new AvatarSelectionDialog(new JFrame());
		
		buttonOk.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String nomeUtente = textFieldNomeUtente.getText();
				String nickname = textFieldNickname.getText();
				
				/* Si controlla che non siano vuoti e che non contengano solamente spazi vuoti */
				if (nomeUtente.isBlank() || nickname.isBlank())
				{
					JOptionPane.showMessageDialog(new JFrame(), "Inserire dati validi!", "Errore inserimento dati", JOptionPane.OK_OPTION);
					return;
				}
				
				if (dialogSelezioneAvatar.getSelectedAvatar() == null)
				{
					JOptionPane.showMessageDialog(new JFrame(), "Selezionare avatar!", "Errore inserimento dati", JOptionPane.OK_OPTION);
					return;
				}
					
				PlayerDataController.getInstance().aggiornaDatiGiocatore(nomeUtente, nickname, dialogSelezioneAvatar.getSelectedAvatar());
				/* Dispose utile per chiudere la finestra corrente */
				dispose();
				frameParent.impostaCampoDiGioco();
			}
		});
		
		buttonAnnulla.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				/* Rimuove esattamente questa finestra */
				dispose();
				frameParent.mostraInserimentoNumeroGiocatori();				
			}
		});
		
		buttonSelezionaAvatar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				dialogSelezioneAvatar.setVisible(true);
			}
		});
		
		setPreferredSize(new Dimension(200, 200));
		
		pannelloContenitore.add(labelNomeUtente);
		pannelloContenitore.add(textFieldNomeUtente);
		pannelloContenitore.add(labelNickname);
		pannelloContenitore.add(textFieldNickname);
		pannelloContenitore.add(buttonSelezionaAvatar);
		pannelloContenitore.add(buttonOk);
		pannelloContenitore.add(buttonAnnulla);
		add(pannelloContenitore);
	}
}
