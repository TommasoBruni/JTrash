package eu.uniroma1.view.frame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.beans.PropertyVetoException;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.security.Provider;
import java.util.Observable;
import java.util.Observer;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JComponent;
import javax.swing.JDesktopPane;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import java.awt.*;

import eu.uniroma1.controller.Enableable;
import eu.uniroma1.controller.EnemyController;
import eu.uniroma1.controller.FieldController;
import eu.uniroma1.controller.MainPlayerController;
import eu.uniroma1.controller.PlayerController;
import eu.uniroma1.model.exceptions.DeckFinishedException;
import eu.uniroma1.model.exceptions.GameNotInProgressException;
import eu.uniroma1.model.exceptions.MoveNotAllowedException;
import eu.uniroma1.controller.PlayerData;
import eu.uniroma1.controller.Resettable;
import eu.uniroma1.controller.Restartable;
import eu.uniroma1.view.dialog.ProfileDialog;
import eu.uniroma1.view.panel.AnimationPanel;
import eu.uniroma1.view.panel.AvatarScorePanel;
import eu.uniroma1.view.panel.CardsPanel;
import eu.uniroma1.view.panel.ContainerPanel;
import eu.uniroma1.view.panel.DeckPanel;
import eu.uniroma1.view.utils.DeckPosition;

/**
 * Classe per creare il frame di gioco
 */
public class GameFrame extends JFrame implements Enableable, Observer, Resettable
{
	private ContainerPanel mainPlayerPanel;
	private ContainerPanel robotDxPlayerPanel;
	private ContainerPanel robotSxPlayerPanel;
	private ContainerPanel robotFrontPlayerPanel;
	private DeckPanel deckPanel;
	private InsertionDataFrame dataInsertionFrame;
	private EnemiesSelectionFrame enemiesSelectionFrame;
	private ObserverForVictory observerVictory;
	private JPanel pannelloPerInternalFrame;
	
	public void setupPerInserimentoDati()
	{
		
		GridBagConstraints gbc = new GridBagConstraints();
		
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.weightx = 0.1;
		gbc.weighty = 0.1;
		gbc.anchor = GridBagConstraints.CENTER;
		dataInsertionFrame = new InsertionDataFrame(this);
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
        FieldController.getInstance().updateNumberOfPlayers(possibileNumeroGiocatori[numeroGiocatori]);
        if (MainPlayerController.getInstance().getPlayerData().isEmptyData())
	        /* Sono consecutivi */
	        setupPerInserimentoDati();
        else
        	mostraDialogInserimentoNemici();
	}
	
	public void mostraDialogInserimentoNemici()
	{
		GridBagConstraints gbc = new GridBagConstraints();
		
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.weightx = 0.1;
		gbc.weighty = 0.1;
		gbc.anchor = GridBagConstraints.CENTER;
		
		enemiesSelectionFrame = new EnemiesSelectionFrame(this);
		enemiesSelectionFrame.setVisible(true);
		pannelloPerInternalFrame.add(enemiesSelectionFrame, gbc);

		add(pannelloPerInternalFrame);
	}
	
	public void setGridBagLayout()
	{
		setLayout(new GridBagLayout());
	}
	
	@Override
	public void enableObject()
	{
		this.setEnabled(true);
	}
	
	@Override
	public void disableObject()
	{
		this.setEnabled(false);
	}
	
	public void initializeDeckView()
	{
		GridBagConstraints gbc = new GridBagConstraints();
		
		if (deckPanel == null)
		{
			/* Sono sicuro che quando arrivo qui il nome giocatore è già stato impostato */
			deckPanel = new DeckPanel(FieldController.getInstance().getObservableForReplacingCards());
			setGridBagLayout();
			deckPanel.setVisible(true);
			// Inserisco il mazzo
			gbc.gridx = 0;
			gbc.gridy = 1;
			gbc.weightx = 0.1;
			gbc.weighty = 0.1;
			gbc.anchor = GridBagConstraints.CENTER;
			add(deckPanel, gbc);
		}
		else
		{
			deckPanel.reset();
			deckPanel.setVisible(true);
		}
	}
	
	public void initializeMainPlayerView()
	{
		GridBagConstraints gbc = new GridBagConstraints();
		
		if (mainPlayerPanel == null)
		{
			mainPlayerPanel = new ContainerPanel(MainPlayerController.getInstance(), DeckPosition.IN_ALTO);
			gbc.gridx = 0;
			gbc.gridy = 2;
			gbc.weightx = 0.1;
			gbc.weighty = 0.1;
			gbc.anchor = GridBagConstraints.PAGE_END;
			add(mainPlayerPanel, gbc);
		}
		else
		{
			mainPlayerPanel.reset();
		}
		mainPlayerPanel.setVisible(true);
	}
	
	public void initializeRobotFrontPlayerView()
	{
		GridBagConstraints gbc = new GridBagConstraints();
		
		if (robotFrontPlayerPanel == null)
		{
			robotFrontPlayerPanel = new ContainerPanel(FieldController.getInstance().getNextEnemy(), DeckPosition.IN_BASSO);
			gbc.gridx = 0;
			gbc.gridy = 0;
			gbc.weightx = 0.1;
			gbc.weighty = 0.1;
			gbc.anchor = GridBagConstraints.PAGE_START;
			add(robotFrontPlayerPanel, gbc);
		}
		else
		{
			robotFrontPlayerPanel.reset();
		}
		robotFrontPlayerPanel.setVisible(true);
	}
	
	public void initializeRobotDxPlayerView()
	{
		GridBagConstraints gbc = new GridBagConstraints();
		
		if (FieldController.getInstance().getNumberOfPlayingPlayers() > 2)
		{
			if (robotDxPlayerPanel == null)
			{
				robotDxPlayerPanel = new ContainerPanel(FieldController.getInstance().getNextEnemy(), DeckPosition.SULLA_SX);
				gbc.gridx = 0;
				gbc.gridy = 1;
				gbc.weightx = 0.1;
				gbc.weighty = 0.1;
				gbc.anchor = GridBagConstraints.LINE_END;
				add(robotDxPlayerPanel, gbc);
			}
			else
			{
				robotDxPlayerPanel.reset();
			}
			robotDxPlayerPanel.setVisible(true);
		}
	}
	
	public void initializeRobotSxPlayerView()
	{
		GridBagConstraints gbc = new GridBagConstraints();
		
		if (FieldController.getInstance().getNumberOfPlayingPlayers() > 3)
		{
			if (robotSxPlayerPanel == null)
			{
				robotSxPlayerPanel = new ContainerPanel(FieldController.getInstance().getNextEnemy(), DeckPosition.SULLA_DX);
				gbc.gridx = 0;
				gbc.gridy = 1;
				gbc.weightx = 0.1;
				gbc.weighty = 0.1;
				gbc.anchor = GridBagConstraints.LINE_START;
				add(robotSxPlayerPanel, gbc);
			}
			else
			{
				robotSxPlayerPanel.reset();
			}
			robotSxPlayerPanel.setVisible(true);
		}
	}
	
	public void impostaCampoDiGioco()
	{
		FieldController.getInstance().initializeComponents();
		AnimationPanel pannelloAnimazione;	
		
		/*
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.weightx = 0.1;
		gbc.weighty = 0.1;
		gbc.anchor = GridBagConstraints.CENTER;
		*/
		//pannelloAnimazione = new PannelloAnimazione();
		//add(pannelloAnimazione);
		
		
		//pannelloGiocatorePrincipale.startAnimazione();
		initializeDeckView();
		initializeMainPlayerView();
		initializeRobotFrontPlayerView();
		initializeRobotDxPlayerView();
		initializeRobotSxPlayerView();
		
		setJMenuBar(creaBarraMenu());
		getContentPane().setBackground(new Color(255, 255, 204));
		/* Dice al controller di iniziare la partita */
		FieldController.getInstance().startGame();
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
				disableObject();
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
	
	@Override
	public void reset()
	{
		mainPlayerPanel.setVisible(false);
		robotFrontPlayerPanel.setVisible(false);
		if (FieldController.getInstance().getNumberOfPlayingPlayers() > 2)
			robotDxPlayerPanel.setVisible(false);
		if (FieldController.getInstance().getNumberOfPlayingPlayers() > 3)
			robotSxPlayerPanel.setVisible(false);
		deckPanel.setVisible(false);
	}
	
	@Override
	public void update(Observable o, Object arg) 
	{
		JOptionPane.showMessageDialog(new JFrame(), "Non ci sono più carte!", "Partita finita!", JOptionPane.OK_OPTION);
	}
	
	private class ObserverForVictory implements Observer, Restartable
	{
		private boolean wholeGameFinished;
		
		@Override
		public void restart() 
		{
			if (!wholeGameFinished)
			{
				impostaCampoDiGioco();
			}
			else
			{
				mostraInserimentoNumeroGiocatori();
				wholeGameFinished = false;
			}
		}
		
		@Override
		public void update(Observable o, Object arg) 
		{
			PlayerController playerController = (PlayerController)arg;
			
			reset();
			
			FieldController.getInstance().setItemToRestart(this);
			if (playerController.getCardsInHand() > 0)
			{
				JOptionPane.showMessageDialog(new JFrame(), "Il vincitore di questa partita e': " + playerController.getPlayerData().getPlayerName(), "Vincitore!", JOptionPane.INFORMATION_MESSAGE);
			}
			else
			{
				JOptionPane.showMessageDialog(new JFrame(), "Il vincitore finale e': " + playerController.getPlayerData().getPlayerName(), "Vincitore!", JOptionPane.INFORMATION_MESSAGE);
				wholeGameFinished = true;
			}			
		}
	}
	
	/**
	 * Costruttore frame di gioco
	 */
	public GameFrame()
	{
		/* Imposta il nome al frame */
		super("JTrash");
		
		observerVictory = new ObserverForVictory();
		
		pannelloPerInternalFrame = new JPanel(new GridBagLayout());
		
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
		FieldController.getInstance().addObserver(this);
		FieldController.getInstance().getObservableForGameFinish().addObserver(observerVictory);
		mostraInserimentoNumeroGiocatori();
	}
}
