package eu.uniroma1.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JDesktopPane;
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
		JDesktopPane desktop = new JDesktopPane();
		desktop.setDragMode(JDesktopPane.OUTLINE_DRAG_MODE);
        FrameInserimentoDati frame = new FrameInserimentoDati(controller, desktop, this);
        frame.setVisible(true);        
        
		setContentPane(desktop);
		desktop.add(frame, BorderLayout.CENTER);
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
        /* +2 perché il metodo sopra restituisce l'indice all'interno dell'array */
        controller.aggiornaNumeroGiocatori(numeroGiocatori + 2);
	}
	
	public void setBorderLayout()
	{
		setLayout(new BorderLayout());
	}
	
	public void setGridBagLayout()
	{
		setLayout(new GridBagLayout());
	}
	
	/**
	 * Costruttore frame di gioco
	 */
	public FrameDiGioco()
	{
		/* Imposta il nome al frame */
		super("JTrash");
		controller = new Controller();
		pannelloGiocatorePrincipale = new PannelloGiocatoreVerticale();
		pannelloGiocatoreRobotDiFronte = new PannelloGiocatoreVerticale();
		pannelloGiocatoreRobotDx = new PannelloGiocatoreOrizzontale();
		pannelloGiocatoreRobotSx = new PannelloGiocatoreOrizzontale();
		pannelloMazzoDiCarte = new PannelloMazzoDiCarte();
		GridBagConstraints gbc = new GridBagConstraints();
		
		/* Imposta una grandezza iniziale */
		setExtendedState(JFrame.MAXIMIZED_BOTH);

		/* Imposta a 200 pixel dal lato sinistro e 200 dall'alto */
		setLocation(0, 0);
		
		/* Imposta il fatto di chiudere l'applicazione quando viene chiuso il frame */
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		/* Il layout si imposta in seguito */
		//setBorderLayout();
		
		setGridBagLayout();
		/*
		add(pannelloGiocatorePrincipale, BorderLayout.PAGE_END);
		add(pannelloGiocatoreRobotDiFronte, BorderLayout.PAGE_START);
		add(pannelloGiocatoreRobotDx, BorderLayout.LINE_END);
		add(pannelloGiocatoreRobotSx, BorderLayout.LINE_START);
		add(pannelloMazzoDiCarte, BorderLayout.CENTER);
		*/
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
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.weightx = 0.1;
		gbc.weighty = 0.1;
		gbc.anchor = GridBagConstraints.LINE_END;
		add(pannelloGiocatoreRobotDx, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.weightx = 0.1;
		gbc.weighty = 0.1;
		gbc.anchor = GridBagConstraints.LINE_START;
		add(pannelloGiocatoreRobotSx, gbc);
		
		//add(pannelloGiocatoreRobotDx, BorderLayout.LINE_END);
		//add(pannelloGiocatoreRobotSx, BorderLayout.LINE_START);
		//add(pannelloMazzoDiCarte, BorderLayout.CENTER);
		
		/* Imposta il frame visibile */
		
		setVisible(true);
		/* TODO: aggiungere questo prima di mostrare il campo da gioco
		  
		mostraInserimentoNumeroGiocatori();
		
		pannelloGiocatore = new PannelloGiocatore();
		*/
	}
}
