package eu.uniroma1.view;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import eu.uniroma1.controller.Controller;

/**
 * Classe per creare il frame di gioco
 */
public class FrameDiGioco extends JFrame
{
	private Controller controller;
	
	/**
	 * Costruttore frame di gioco 
	 */
	public FrameDiGioco()
	{
		/* Imposta il nome al frame */
		super("JTrash");
		Integer[] possibileNumeroGiocatori = { 2, 3, 4 };
		controller = new Controller();
		
		/* Imposta una grandezza iniziale */
		setSize(800, 500);
		
		/* Imposta a 200 pixel dal lato sinistro e 200 dall'alto */
		setLocation(200, 200);
		
		/* Imposta il fatto di chiudere l'applicazione quando viene chiuso il frame */
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		/* Imposta il layout */
		setLayout(new BorderLayout());
		
		/* Imposta il frame visibile */
		setVisible(true);
		
        int numeroGiocatori = JOptionPane.showOptionDialog(this, "Numero giocatori:",
                "Inserimento numero giocatori",
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, possibileNumeroGiocatori,
                possibileNumeroGiocatori[0]);
        
        if (numeroGiocatori < 0)
        	System.exit(0);
        controller.aggiornaNumeroGiocatori(numeroGiocatori);
	}
}
