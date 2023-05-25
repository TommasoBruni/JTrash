package eu.uniroma1.view;

import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import java.beans.PropertyVetoException;
import java.util.Timer;

import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import eu.uniroma1.controller.Controller;

/**
 * Classe per creare il frame di gioco
 */
public class FrameDiGioco extends JFrame
{
	private Controller controller;
	private JLabel pippo;
	private JLabel pippo2;
	
	private static void setupPerInserimentoDati(FrameDiGioco frameDiGioco)
	{
		JDesktopPane desktop = new JDesktopPane();
		desktop.setDragMode(JDesktopPane.OUTLINE_DRAG_MODE);
        FrameInserimentoDati frame = new FrameInserimentoDati(frameDiGioco.controller, desktop, frameDiGioco);
        frame.setVisible(true);        
        
		frameDiGioco.setContentPane(desktop);
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
        setupPerInserimentoDati(this);
        controller.aggiornaNumeroGiocatori(numeroGiocatori + 2);
	}
	
	public void setBorderLayout()
	{
		setLayout(new BorderLayout());
		add(pippo, BorderLayout.LINE_START);
	}
	
	/**
	 * Costruttore frame di gioco 
	 */
	public FrameDiGioco()
	{
		/* Imposta il nome al frame */
		super("JTrash");
		controller = new Controller();
		pippo = new JLabel("Ciaooo");
		pippo2 = new JLabel("Ciaooo");
		
		/* Imposta una grandezza iniziale */
		setSize(800, 500);
		
		/* Imposta a 200 pixel dal lato sinistro e 200 dall'alto */
		setLocation(200, 200);
		
		/* Imposta il fatto di chiudere l'applicazione quando viene chiuso il frame */
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		/* Il layout si imposta in seguito */
		
		/* Imposta il frame visibile */
		setVisible(true);
		mostraInserimentoNumeroGiocatori();
	}
}
