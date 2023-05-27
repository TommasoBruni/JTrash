package eu.uniroma1.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

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
		pannelloGiocatorePrincipale = new PannelloGiocatoreVerticale(controller.getNomeUltimoGiocatore());
		pannelloGiocatoreRobotDiFronte = new PannelloGiocatoreVerticale("Franco");
		pannelloGiocatoreRobotDx = new PannelloGiocatoreOrizzontale("Luca");
		pannelloGiocatoreRobotSx = new PannelloGiocatoreOrizzontale("Paolo");
		pannelloMazzoDiCarte = new PannelloMazzoDiCarte();
		GridBagConstraints gbc = new GridBagConstraints();
		
		//setGridBagLayout();
		
		/*
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.weightx = 0.1;
		gbc.weighty = 0.1;
		gbc.anchor = GridBagConstraints.CENTER;
		*/
		pannelloAnimazione = new PannelloAnimazione();
		add(pannelloAnimazione);
		/*
		// Inserisco il mazzo
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.weightx = 0.1;
		gbc.weighty = 0.1;
		gbc.anchor = GridBagConstraints.CENTER;
		add(pannelloMazzoDiCarte, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.weightx = 1;
		gbc.weighty = 1;
		gbc.anchor = GridBagConstraints.PAGE_END;
		add(pannelloGiocatorePrincipale, gbc);
		
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
		*/
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
