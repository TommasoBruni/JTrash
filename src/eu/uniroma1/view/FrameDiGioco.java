package eu.uniroma1.view;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Frame;

import javax.swing.Icon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.Border;

/**
 * Classe per creare il frame di gioco
 */
public class FrameDiGioco extends JFrame
{	
	/**
	 * Costruttore frame di gioco 
	 */
	public FrameDiGioco()
	{
		/* Imposta il nome al frame */
		super("JTrash");
		Integer[] possibileNumeroGiocatori = { 2, 3, 4 };
		
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
	}
}
