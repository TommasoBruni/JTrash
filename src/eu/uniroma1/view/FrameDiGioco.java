package eu.uniroma1.view;

import java.awt.BorderLayout;

import javax.swing.JFrame;

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
		
		/* Imposta una grandezza iniziale */
		setSize(800, 500);
		
		/* Imposta il fatto di chiudere l'applicazione quando viene chiuso il frame */
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		/* Imposta il layout */
		setLayout(new BorderLayout());
		
		/* Imposta il frame visibile */
		setVisible(true);
	}
}
