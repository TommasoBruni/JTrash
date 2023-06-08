package eu.uniroma1.view.frame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

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

import eu.uniroma1.controller.MainPlayerFieldController;
import eu.uniroma1.model.exceptions.DeckFinishedException;
import eu.uniroma1.model.exceptions.GameNotInProgressException;
import eu.uniroma1.controller.PlayersController;
import eu.uniroma1.view.dialog.ProfileDialog;
import eu.uniroma1.view.utils.interfaces.Closeable;
import eu.uniroma1.view.panel.AnimationPanel;
import eu.uniroma1.view.panel.AvatarScorePanel;
import eu.uniroma1.view.panel.CardsPanel;
import eu.uniroma1.view.panel.ContainerPanel;
import eu.uniroma1.view.panel.DeckPanel;
import eu.uniroma1.view.utils.DeckPosition;

/**
 * Classe per creare il frame di gioco
 */
public class GameFrame extends JFrame implements Closeable
{
	private ContainerPanel mainPlayerPanel;
	private ContainerPanel robotDxPlayerPanel;
	private ContainerPanel robotSxPlayerPanel;
	private ContainerPanel robotFrontPlayerPanel;
	private DeckPanel deckPanel;
	private InsertionDataFrame dataInsertionFrame;
	
	private void setupPerInserimentoDati()
	{
		JPanel pannelloPerInternalFrame = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.weightx = 0.1;
		gbc.weighty = 0.1;
		gbc.anchor = GridBagConstraints.CENTER;
		dataInsertionFrame = new InsertionDataFrame(pannelloPerInternalFrame, this);
		dataInsertionFrame.setVisible(true);
        
        pannelloPerInternalFrame.add(dataInsertionFrame, gbc);
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
        PlayersController.getInstance().aggiornaNumeroGiocatori(possibileNumeroGiocatori[numeroGiocatori]);
	}
	
	public void setGridBagLayout()
	{
		setLayout(new GridBagLayout());
	}
	
	@Override
	public void enableComponent()
	{
		this.setEnabled(true);
	}
	
	@Override
	public void disableComponent()
	{
		this.setEnabled(false);
	}
	
	public void impostaCampoDiGioco()
	{
		/* Dice al controller di iniziare la partita */
		MainPlayerFieldController.getInstance().startGame();
		AnimationPanel pannelloAnimazione;
		/* Sono sicuro che quando arrivo qui il nome giocatore è già stato impostato */
		deckPanel = new DeckPanel(MainPlayerFieldController.getInstance().getObservableForReplacingCards());
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
		add(deckPanel, gbc);
		
		try 
		{
			mainPlayerPanel = new ContainerPanel(new CardsPanel(DeckPosition.IN_ALTO, MainPlayerFieldController.getInstance().getObservableForHintCard()),
																  new AvatarScorePanel(PlayersController.getInstance().getNomeGiocatore(),
																		  					  PlayersController.getInstance().getAvatarGiocatore(),
																		  					  PlayersController.getInstance().getPartiteGiocateGiocatore(),
																		  					  PlayersController.getInstance().getPartiteVinteGiocatore(),
																		  					  PlayersController.getInstance().getPartitePerseGiocatore(),
																		  					  PlayersController.getInstance()),
																  DeckPosition.IN_ALTO);
			
			gbc.gridx = 0;
			gbc.gridy = 2;
			gbc.weightx = 0.1;
			gbc.weighty = 0.1;
			gbc.anchor = GridBagConstraints.PAGE_END;
			add(mainPlayerPanel, gbc);
		} 
		catch (GameNotInProgressException | DeckFinishedException e) 
		{
			/* Nessuna delle due eccezioni accadrà mai perché stiamo impostando in questo
			 * momento la partita */
			e.printStackTrace();
		}
		
		//pannelloGiocatorePrincipale.startAnimazione();
		try 
		{
			robotFrontPlayerPanel = new ContainerPanel(new CardsPanel(DeckPosition.IN_BASSO),
																	 new AvatarScorePanel(PlayersController.getInstance().getNomeGiocatore(),
																			 					 PlayersController.getInstance().getAvatarGiocatore()),
																	  DeckPosition.IN_BASSO);
			gbc.gridx = 0;
			gbc.gridy = 0;
			gbc.weightx = 0.1;
			gbc.weighty = 0.1;
			gbc.anchor = GridBagConstraints.PAGE_START;
			add(robotFrontPlayerPanel, gbc);
		} 
		catch (GameNotInProgressException | DeckFinishedException e) 
		{
			/* Nessuna delle due eccezioni accadrà mai perché stiamo impostando in questo
			 * momento la partita */
			e.printStackTrace();
		}
		
		if (PlayersController.getInstance().getNumeroGiocatoriInPartita() > 2)
		{
			try
			{
				robotDxPlayerPanel = new ContainerPanel(new CardsPanel(DeckPosition.SULLA_SX),
																   new AvatarScorePanel(PlayersController.getInstance().getNomeGiocatore(),
																		   				       PlayersController.getInstance().getAvatarGiocatore()),
																   DeckPosition.SULLA_SX);
				gbc.gridx = 0;
				gbc.gridy = 1;
				gbc.weightx = 0.1;
				gbc.weighty = 0.1;
				gbc.anchor = GridBagConstraints.LINE_END;
				add(robotDxPlayerPanel, gbc);
			} 
			catch (GameNotInProgressException | DeckFinishedException e)
			{
				/* Nessuna delle due eccezioni accadrà mai perché stiamo impostando in questo
				 * momento la partita */
				e.printStackTrace();
			}
		}
		
		if (PlayersController.getInstance().getNumeroGiocatoriInPartita() > 3)
		{
			try 
			{
				robotSxPlayerPanel = new ContainerPanel(new CardsPanel(DeckPosition.SULLA_DX),
																   new AvatarScorePanel(PlayersController.getInstance().getNomeGiocatore(),
																		   					   PlayersController.getInstance().getAvatarGiocatore()),
																   DeckPosition.SULLA_DX);
				gbc.gridx = 0;
				gbc.gridy = 1;
				gbc.weightx = 0.1;
				gbc.weighty = 0.1;
				gbc.anchor = GridBagConstraints.LINE_START;
				add(robotSxPlayerPanel, gbc);
			} 
			catch (GameNotInProgressException | DeckFinishedException e)
			{
				/* Nessuna delle due eccezioni accadrà mai perché stiamo impostando in questo
				 * momento la partita */
				e.printStackTrace();
			}				
		}
		setJMenuBar(creaBarraMenu());
	}
	
	private JMenuBar creaBarraMenu()
	{
		JMenuBar barraMenu = new JMenuBar();
		JMenu menuFile = new JMenu("File");
		JMenuItem profiloUtente = new JMenuItem("Profilo");
		JMenuItem menuItemEsci = new JMenuItem("Esci");
		
		profiloUtente.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				disableComponent();
				ProfileDialog dialogProfilo = new ProfileDialog(GameFrame.this);
				dialogProfilo.setVisible(true);
			}
		});
		
		menuItemEsci.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				/* Il primo parametro è il componente genitore, solitamente si utilizza
				 * la classe frame sottostante. */
				int azioneFinestra = JOptionPane.showConfirmDialog(new JFrame(), "Vuoi uscire dall'applicazione?", "Chiusura applicazione",
											  JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
				if (azioneFinestra == JOptionPane.OK_OPTION)
					System.exit(0);
			}
		});
		
		
		/* Per aggiungere un separatore logico tra gli item importa/esporta ed esci */
		menuFile.add(profiloUtente);
		menuFile.addSeparator();
		menuFile.add(menuItemEsci);
		
		barraMenu.add(menuFile);
		
		return barraMenu;
	}
	
	/**
	 * Costruttore frame di gioco
	 */
	public GameFrame()
	{
		/* Imposta il nome al frame */
		super("JTrash");
		
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
