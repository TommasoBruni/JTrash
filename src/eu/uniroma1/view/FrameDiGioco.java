package eu.uniroma1.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

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
	private PannelloGiocatore pannelloGiocatore;
	
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
		revalidate();
		repaint();
	}
	
	/**
	 * Costruttore frame di gioco
	 */
	public FrameDiGioco()
	{
		/* Imposta il nome al frame */
		super("JTrash");
		controller = new Controller();
		pannelloGiocatore = new PannelloGiocatore();
		
		/* Imposta una grandezza iniziale */
		setSize(800, 500);
		
		/* Imposta a 200 pixel dal lato sinistro e 200 dall'alto */
		setLocation(200, 200);
		
		/* Imposta il fatto di chiudere l'applicazione quando viene chiuso il frame */
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		/* Il layout si imposta in seguito */
		setBorderLayout();
		add(pannelloGiocatore, BorderLayout.PAGE_END);
		
		/* Imposta il frame visibile */
		
		setVisible(true);
		/*
		mostraInserimentoNumeroGiocatori();
		
		pannelloGiocatore = new PannelloGiocatore();
		*/
	}
}
