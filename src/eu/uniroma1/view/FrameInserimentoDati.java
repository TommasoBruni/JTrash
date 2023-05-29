package eu.uniroma1.view;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyVetoException;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import eu.uniroma1.controller.Controller;

public class FrameInserimentoDati extends JInternalFrame 
{
	private JPanel pannelloContenitore;
	private JLabel labelNomeUtente;
	private JTextField textFieldNomeUtente;
	private JLabel labelNickname;
	private JTextField textFieldNickname;
	private JButton buttonOk;
	private JButton buttonAnnulla;
	private JButton buttonSelezionaAvatar;
	private String pathAvatarSelezionato;
	
	public FrameInserimentoDati(Controller controller, Container parentComponent, FrameDiGioco frameParent)
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
		buttonOk.setSize(new Dimension(50, 50));
		buttonAnnulla = new JButton("Annulla");
		buttonOk.setPreferredSize(buttonAnnulla.getPreferredSize());
		buttonSelezionaAvatar = new JButton("Seleziona avatar");
		
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
				
				if (pathAvatarSelezionato == null)
				{
					JOptionPane.showMessageDialog(new JFrame(), "Selezionare avatar!", "Errore inserimento dati", JOptionPane.OK_OPTION);
					return;
				}
					
				controller.aggiungiGiocatore(nomeUtente, nickname, null);
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
				pathAvatarSelezionato = new DialogSelezioneAvatar(new JFrame()).getSelectedAvatar();
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
