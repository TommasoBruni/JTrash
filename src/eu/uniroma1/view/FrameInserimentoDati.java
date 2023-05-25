package eu.uniroma1.view;

import java.awt.BorderLayout;
import java.awt.Dimension;

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
	
	public FrameInserimentoDati(Controller controller)
	{
		super("Inserimento dati",
		          false, //resizable
		          false, //closable
		          true, //maximizable
		          true);
		labelNomeUtente = new JLabel("Nome utente:");
		textFieldNomeUtente = new JTextField(15);
		labelNickname = new JLabel("Nickname:");
		textFieldNickname = new JTextField(15);
		pannelloContenitore = new JPanel();

		setSize(200, 200);
		setLocation(100, 100);
		
		pannelloContenitore.add(labelNomeUtente);
		pannelloContenitore.add(textFieldNomeUtente);
		pannelloContenitore.add(labelNickname);
		pannelloContenitore.add(textFieldNickname);
		add(pannelloContenitore);
	}
}
