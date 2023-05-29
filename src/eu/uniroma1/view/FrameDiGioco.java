package eu.uniroma1.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import java.awt.*;

import eu.uniroma1.controller.Controller;

/**
 * Classe per creare il frame di gioco
 */
public class FrameDiGioco extends JFrame
{
	private Controller controller;
	private PannelloGiocatore pannelloGiocatorePrincipale;
	private PannelloGiocatore pannelloGiocatoreRobotDx;
	private PannelloGiocatore pannelloGiocatoreRobotSx;
	private PannelloGiocatore pannelloGiocatoreRobotDiFronte;
	private PannelloMazzoDiCarte pannelloMazzoDiCarte;
	
	private void setupPerInserimentoDati()
	{
		JPanel pannelloPerInternalFrame = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.weightx = 0.1;
		gbc.weighty = 0.1;
		gbc.anchor = GridBagConstraints.CENTER;
        FrameInserimentoDati frame = new FrameInserimentoDati(controller, pannelloPerInternalFrame, this);
        frame.setVisible(true);
        
        pannelloPerInternalFrame.add(frame, gbc);
        add(pannelloPerInternalFrame);
	}
	
	public void mostraInserimentoNumeroGiocatori()
	{
		Integer[] possibileNumeroGiocatori = { 2, 3, 4 };
        int numeroGiocatori = JOptionPane.showOptionDialog(this, "Numero giocatori:",
                "Inserimento numero giocatori",
                JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, possibileNumeroGiocatori,
                possibileNumeroGiocatori[0]);
        
        if (numeroGiocatori < 0)
        	System.exit(0);
        /* Sono consecutivi */
        setupPerInserimentoDati();
        controller.aggiornaNumeroGiocatori(possibileNumeroGiocatori[numeroGiocatori]);
	}
	
	public void setGridBagLayout()
	{
		setLayout(new GridBagLayout());
	}
	
	public void impostaCampoDiGioco()
	{
		PannelloAnimazione pannelloAnimazione;
		/* Sono sicuro che quando arrivo qui il nome giocatore è già stato impostato */
		pannelloGiocatoreRobotDx = new PannelloGiocatoreOrizzontale("Luca");
		pannelloGiocatoreRobotSx = new PannelloGiocatoreOrizzontale("Paolo");
		pannelloMazzoDiCarte = new PannelloMazzoDiCarte();
		GridBagConstraints gbc = new GridBagConstraints();
		
		setGridBagLayout();
		
		/*
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.weightx = 0.1;
		gbc.weighty = 0.1;
		gbc.anchor = GridBagConstraints.CENTER;
		*/
		//pannelloAnimazione = new PannelloAnimazione();
		//add(pannelloAnimazione);
		
		// Inserisco il mazzo
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.weightx = 0.1;
		gbc.weighty = 0.1;
		gbc.anchor = GridBagConstraints.CENTER;
		add(pannelloMazzoDiCarte, gbc);
		
		pannelloGiocatorePrincipale = new PannelloGiocatoreVerticale(controller.getNomeUltimoGiocatore());
		
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.weightx = 1;
		gbc.weighty = 1;
		gbc.anchor = GridBagConstraints.PAGE_END;
		add(pannelloGiocatorePrincipale, gbc);
		
		//pannelloGiocatorePrincipale.startAnimazione();
		pannelloGiocatoreRobotDiFronte = new PannelloGiocatoreVerticale("Franco");

		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.weightx = 0.1;
		gbc.weighty = 0.1;
		gbc.anchor = GridBagConstraints.PAGE_START;
		add(pannelloGiocatoreRobotDiFronte, gbc);
		
		if (controller.getNumeroGiocatoriInPartita() > 2)
		{
			gbc.gridx = 0;
			gbc.gridy = 1;
			gbc.weightx = 0.1;
			gbc.weighty = 0.1;
			gbc.anchor = GridBagConstraints.LINE_END;
			add(pannelloGiocatoreRobotDx, gbc);
		}
		
		if (controller.getNumeroGiocatoriInPartita() > 3)
		{
			gbc.gridx = 0;
			gbc.gridy = 1;
			gbc.weightx = 0.1;
			gbc.weighty = 0.1;
			gbc.anchor = GridBagConstraints.LINE_START;
			add(pannelloGiocatoreRobotSx, gbc);
		}
		setJMenuBar(creaBarraMenu());
	}
	
	private JMenuBar creaBarraMenu()
	{
		JMenuBar barraMenu = new JMenuBar();
		JMenu menuFile = new JMenu("File");
		
		JMenuItem menuItemEsci = new JMenuItem("Esci");
		
		menuItemEsci.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				/* Il primo parametro è il componente genitore, solitamente si utilizza
				 * la classe frame sottostante. */
				int azioneFinestra = JOptionPane.showConfirmDialog(new JFrame(), "Vuoi uscire dall'applicazione?", "Chiusura applicazione",
											  JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
				if (azioneFinestra == JOptionPane.OK_OPTION)
					System.exit(0);
				/* Per lo showInputDialog si fa così: */
				/*String nomeUtente = JOptionPane.showInputDialog(Frame.this, "Inserisci nome utente", "Inserimento utente", JOptionPane.QUESTION_MESSAGE);*/
				
				/* Per l'error dialog, per cambiare icona basta cambiare l'ultimo parametro  */
				/*JOptionPane.showMessageDialog(Frame.this, "Errore nell'inserimento dei dati", "Errore!", JOptionPane.ERROR_MESSAGE);*/
			}
		});
		
		
		/* Per aggiungere un separatore logico tra gli item importa/esporta ed esci */
		menuFile.addSeparator();
		menuFile.add(menuItemEsci);
		
		barraMenu.add(menuFile);
		
		return barraMenu;
	}
	
	/**
	 * Costruttore frame di gioco
	 */
	public FrameDiGioco()
	{
		/* Imposta il nome al frame */
		super("JTrash");
		controller = new Controller();
		
		/* Imposta una grandezza iniziale */
		setExtendedState(JFrame.MAXIMIZED_BOTH);

		/* Imposta a 200 pixel dal lato sinistro e 200 dall'alto */
		setLocation(0, 0);
		
		/* Imposta il fatto di chiudere l'applicazione quando viene chiuso il frame */
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		/* Il layout si imposta in seguito */
		
		/* Imposta il frame visibile */
		setVisible(true);
		//AudioManager.getInstance().play(System.getProperty("user.dir").concat("\\resources\\canzone_di_sottofondo.wav"));
		
		mostraInserimentoNumeroGiocatori();
	}
}
