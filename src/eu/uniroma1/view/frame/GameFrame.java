package eu.uniroma1.view.frame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.beans.PropertyVetoException;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
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

import eu.uniroma1.controller.EnemyController;
import eu.uniroma1.controller.FieldController;
import eu.uniroma1.controller.MainPlayerController;
import eu.uniroma1.controller.PlayerController;
import eu.uniroma1.model.exceptions.DeckFinishedException;
import eu.uniroma1.model.exceptions.GameNotInProgressException;
import eu.uniroma1.model.exceptions.MoveNotAllowedException;
import eu.uniroma1.controller.PlayerData;
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
public class GameFrame extends JFrame implements Closeable, Observer
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
        /* Sono consecutivi */
        setupPerInserimentoDati();
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
		EnemyController currentEnemyController;
		/* Dice al controller di iniziare la partita */
		FieldController.getInstance().startGame();
		AnimationPanel pannelloAnimazione;
		/* Sono sicuro che quando arrivo qui il nome giocatore è già stato impostato */
		deckPanel = new DeckPanel(FieldController.getInstance().getObservableForReplacingCards());
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
			mainPlayerPanel = new ContainerPanel(new CardsPanel(DeckPosition.IN_ALTO, MainPlayerController.getInstance()),
																  new AvatarScorePanel(MainPlayerController.getInstance()),
																  DeckPosition.IN_ALTO);
			
			gbc.gridx = 0;
			gbc.gridy = 2;
			gbc.weightx = 0.1;
			gbc.weighty = 0.1;
			gbc.anchor = GridBagConstraints.PAGE_END;
			add(mainPlayerPanel, gbc);
		} 
		catch (GameNotInProgressException | MoveNotAllowedException | DeckFinishedException e) 
		{
			/* Nessuna delle due eccezioni accadrà mai perché stiamo impostando in questo
			 * momento la partita */
			e.printStackTrace();
		}
		
		//pannelloGiocatorePrincipale.startAnimazione();
		try 
		{
			currentEnemyController = FieldController.getInstance().getNextEnemy();
			robotFrontPlayerPanel = new ContainerPanel(new CardsPanel(DeckPosition.IN_BASSO, currentEnemyController),
													   new AvatarScorePanel(currentEnemyController), DeckPosition.IN_BASSO);
			gbc.gridx = 0;
			gbc.gridy = 0;
			gbc.weightx = 0.1;
			gbc.weighty = 0.1;
			gbc.anchor = GridBagConstraints.PAGE_START;
			add(robotFrontPlayerPanel, gbc);
		} 
		catch (GameNotInProgressException | MoveNotAllowedException | DeckFinishedException e) 
		{
			/* Nessuna delle due eccezioni accadrà mai perché stiamo impostando in questo
			 * momento la partita */
			e.printStackTrace();
		}
		
		if (FieldController.getInstance().getNumberOfPlayingPlayers() > 2)
		{
			try
			{
				currentEnemyController = FieldController.getInstance().getNextEnemy();
				robotDxPlayerPanel = new ContainerPanel(new CardsPanel(DeckPosition.SULLA_SX, currentEnemyController),
														new AvatarScorePanel(currentEnemyController), DeckPosition.SULLA_SX);
				gbc.gridx = 0;
				gbc.gridy = 1;
				gbc.weightx = 0.1;
				gbc.weighty = 0.1;
				gbc.anchor = GridBagConstraints.LINE_END;
				add(robotDxPlayerPanel, gbc);
			} 
			catch (GameNotInProgressException | MoveNotAllowedException | DeckFinishedException e) 
			{
				/* Nessuna delle due eccezioni accadrà mai perché stiamo impostando in questo
				 * momento la partita */
				e.printStackTrace();
			}
		}
		
		if (FieldController.getInstance().getNumberOfPlayingPlayers() > 3)
		{
			try 
			{
				currentEnemyController = FieldController.getInstance().getNextEnemy();
				robotSxPlayerPanel = new ContainerPanel(new CardsPanel(DeckPosition.SULLA_DX, currentEnemyController),
														new AvatarScorePanel(currentEnemyController), DeckPosition.SULLA_DX);
				gbc.gridx = 0;
				gbc.gridy = 1;
				gbc.weightx = 0.1;
				gbc.weighty = 0.1;
				gbc.anchor = GridBagConstraints.LINE_START;
				add(robotSxPlayerPanel, gbc);
			} 
			catch (GameNotInProgressException | MoveNotAllowedException | DeckFinishedException e)
			{
				/* Nessuna delle due eccezioni accadrà mai perché stiamo impostando in questo
				 * momento la partita */
				e.printStackTrace();
			}				
		}
		
		setJMenuBar(creaBarraMenu());
		getContentPane().setBackground(new Color(255, 255, 204));
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
	
	@Override
	public void update(Observable o, Object arg) 
	{
		JOptionPane.showMessageDialog(new JFrame(), "Non ci sono più carte!", "Partita finita!", JOptionPane.OK_OPTION);
	}
	
	private class ObserverForVictory implements Observer
	{
		@Override
		public void update(Observable o, Object arg) 
		{
			PlayerController playerController = (PlayerController)arg;
			
			JOptionPane.showMessageDialog(new JFrame(), "Vincitore!", "Il vincitore e': " + playerController.getPlayerData().getNomeGiocatore(), JOptionPane.INFORMATION_MESSAGE);
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
		FieldController.getInstance().getObservableForGameFinish().addObserver(observerVictory);
		
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
		mostraInserimentoNumeroGiocatori();
	}
}
