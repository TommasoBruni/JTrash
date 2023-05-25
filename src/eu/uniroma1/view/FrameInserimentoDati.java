package eu.uniroma1.view;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyVetoException;

import javax.swing.JButton;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
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
	
	public FrameInserimentoDati(Controller controller, JDesktopPane parentDesktop, FrameDiGioco frameParent)
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
		
		buttonOk.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String nomeUtente = textFieldNomeUtente.getText();
				String nickname = textFieldNickname.getText();
				
				/* Si controlla che non siano vuoti e che non contengano solamente spazi vuoti */
				if (nomeUtente.isBlank() || nickname.isBlank())
					return;
				controller.aggiungiGiocatore(nomeUtente, nickname, null);
				/* Rimuove esattamente questa finestra */
				parentDesktop.getComponent(0).setVisible(false);
				frameParent.setBorderLayout();
			}
		});
		
		buttonAnnulla.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				/* Rimuove esattamente questa finestra */
				parentDesktop.getComponent(0).setVisible(false);
				frameParent.mostraInserimentoNumeroGiocatori();				
			}
		});
		
		buttonAnnulla.addActionListener(null);

		setSize(200, 175);
		setLocation(300, 150);
		
		pannelloContenitore.add(labelNomeUtente);
		pannelloContenitore.add(textFieldNomeUtente);
		pannelloContenitore.add(labelNickname);
		pannelloContenitore.add(textFieldNickname);
		pannelloContenitore.add(buttonOk);
		pannelloContenitore.add(buttonAnnulla);
		add(pannelloContenitore);
	}
}
