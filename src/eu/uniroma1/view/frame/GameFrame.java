package eu.uniroma1.view.frame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import java.awt.*;

import eu.uniroma1.controller.Enableable;
import eu.uniroma1.controller.FieldController;
import eu.uniroma1.controller.MainPlayerController;
import eu.uniroma1.controller.PlayerController;
import eu.uniroma1.controller.Resettable;
import eu.uniroma1.controller.Restartable;
import eu.uniroma1.view.dialog.ProfileDialog;
import eu.uniroma1.view.panel.ContainerPanel;
import eu.uniroma1.view.panel.DeckPanel;
import eu.uniroma1.view.utils.AudioManager;
import eu.uniroma1.view.utils.DeckPosition;

/**
 * Game frame class
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
	private JPanel internalFramePanel;
	
	/**
	 * Show insertion data frame.
	 */
	public void showInsertionDataFrame()
	{
		
		GridBagConstraints gbc = new GridBagConstraints();
		
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.weightx = 0.1;
		gbc.weighty = 0.1;
		gbc.anchor = GridBagConstraints.CENTER;
		dataInsertionFrame = new InsertionDataFrame(this);
		dataInsertionFrame.setVisible(true);
        
        internalFramePanel.add(dataInsertionFrame, gbc);
        add(internalFramePanel);
	}
	
	/**
	 * Show insertion number of players dialog
	 */
	public void showInsertionNPlayersDialog()
	{
		Integer[] possibleNumberOfPlayers = { 2, 3, 4 };
        int nPlayers = JOptionPane.showOptionDialog(this, "Number of players:",
                "Number of players insertion",
                JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, possibleNumberOfPlayers,
                possibleNumberOfPlayers[0]);
        
        if (nPlayers < 0)
        	System.exit(0);
        FieldController.getInstance().updateNumberOfPlayers(possibleNumberOfPlayers[nPlayers]);
        if (MainPlayerController.getInstance().getPlayerData().isEmptyData())
	        /* Are consecutive */
	        showInsertionDataFrame();
        else
        	showEnemiesInsertionDialog();
	}
	
	/**
	 * Show enemies insertion dialog
	 */
	public void showEnemiesInsertionDialog()
	{
		GridBagConstraints gbc = new GridBagConstraints();
		
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.weightx = 0.1;
		gbc.weighty = 0.1;
		gbc.anchor = GridBagConstraints.CENTER;
		
		enemiesSelectionFrame = new EnemiesSelectionFrame(this);
		enemiesSelectionFrame.setVisible(true);
		internalFramePanel.add(enemiesSelectionFrame, gbc);

		add(internalFramePanel);
	}
	
	private void setGridBagLayout()
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
	
	private void initializeDeckView()
	{
		GridBagConstraints gbc = new GridBagConstraints();
		
		if (deckPanel == null)
		{
			/* Tha name of the player is already  */
			deckPanel = new DeckPanel(FieldController.getInstance().getObservableForReplacingCards());
			setGridBagLayout();
			deckPanel.setVisible(true);
			/* Insert deck */
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
	
	private void initializeMainPlayerView()
	{
		GridBagConstraints gbc = new GridBagConstraints();
		
		if (mainPlayerPanel == null)
		{
			mainPlayerPanel = new ContainerPanel(MainPlayerController.getInstance(), DeckPosition.TOP);
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
	
	private void initializeRobotFrontPlayerView()
	{
		GridBagConstraints gbc = new GridBagConstraints();
		
		if (robotFrontPlayerPanel == null)
		{
			robotFrontPlayerPanel = new ContainerPanel(FieldController.getInstance().getNextEnemy(), DeckPosition.DOWN);
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
	
	private void initializeRobotDxPlayerView()
	{
		GridBagConstraints gbc = new GridBagConstraints();
		
		if (FieldController.getInstance().getNumberOfPlayingPlayers() > 2)
		{
			if (robotDxPlayerPanel == null)
			{
				robotDxPlayerPanel = new ContainerPanel(FieldController.getInstance().getNextEnemy(), DeckPosition.ON_THE_LEFT);
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
	
	private void initializeRobotSxPlayerView()
	{
		GridBagConstraints gbc = new GridBagConstraints();
		
		if (FieldController.getInstance().getNumberOfPlayingPlayers() > 3)
		{
			if (robotSxPlayerPanel == null)
			{
				robotSxPlayerPanel = new ContainerPanel(FieldController.getInstance().getNextEnemy(), DeckPosition.ON_THE_RIGHT);
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
	
	/**
	 * Setup the game field
	 */
	public void setGameField()
	{
		FieldController.getInstance().initializeComponents();
		
		initializeDeckView();
		initializeMainPlayerView();
		initializeRobotFrontPlayerView();
		initializeRobotDxPlayerView();
		initializeRobotSxPlayerView();
		
		setJMenuBar(createMenuBar());
		getContentPane().setBackground(new Color(255, 255, 204));
		/* Indicate to the controller to start the game */
		FieldController.getInstance().startGame((currentPlayer, nPlayerInGame) -> {
			int playerIndex;
			
			/* Only the view know how the players are arranged.
			 * This is for counterclockwise rotation. */
			switch (currentPlayer)
			{
			case 0:
				if (nPlayerInGame > 2)
					playerIndex = 2;
				else
					playerIndex = 1;
				break;
			case 1:
				if (nPlayerInGame > 3)
					playerIndex = 3;
				else
					playerIndex = 0;
				break;
			case 2:
				playerIndex = 1;
				break;
				default:
					playerIndex = 0;
					break;
			}
			return playerIndex;
		});
	}
	
	private JMenuBar createMenuBar()
	{
		JMenuBar menuBar = new JMenuBar();
		JMenu menuFile = new JMenu("File");
		JMenuItem userProfile = new JMenuItem("Profile");
		JMenuItem menuItemExit = new JMenuItem("Exit");
		
		userProfile.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				disableObject();
				ProfileDialog profileDialog = new ProfileDialog(GameFrame.this);
				profileDialog.setVisible(true);
			}
		});
		
		menuItemExit.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				/* The first parameter is the parent component, usually it is used the underlying frame class */
				int actionWindow = JOptionPane.showConfirmDialog(new JFrame(), "Do you want to exit the application?", "Closing application",
											  JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
				if (actionWindow == JOptionPane.OK_OPTION)
					System.exit(0);
			}
		});
		
		/* To add a logic separator between import/export and exit */
		menuFile.add(userProfile);
		menuFile.addSeparator();
		menuFile.add(menuItemExit);
		
		menuBar.add(menuFile);
		
		return menuBar;
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
		JOptionPane.showMessageDialog(new JFrame(), "No more cards!", "Game finished!", JOptionPane.OK_OPTION);
	}
	
	private class ObserverForVictory implements Observer, Restartable
	{
		private boolean wholeGameFinished;
		
		@Override
		public void restart() 
		{
			if (!wholeGameFinished)
			{
				setGameField();
			}
			else
			{
				showInsertionNPlayersDialog();
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
				JOptionPane.showMessageDialog(new JFrame(), "The winner of this game is: " + playerController.getPlayerData().getPlayerName(), "Winner!", JOptionPane.INFORMATION_MESSAGE);
			}
			else
			{
				JOptionPane.showMessageDialog(new JFrame(), "The final winner of this game is: " + playerController.getPlayerData().getPlayerName(), "Winner!", JOptionPane.INFORMATION_MESSAGE);
				wholeGameFinished = true;
			}			
		}
	}
	
	/**
	 * Game frame class builder
	 */
	public GameFrame()
	{
		/* Set the name to the frame */
		super("JTrash");
		
		observerVictory = new ObserverForVictory();
		
		internalFramePanel = new JPanel(new GridBagLayout());
		
		/* Set starting size */
		setExtendedState(JFrame.MAXIMIZED_BOTH);

		/* Set to 200 pixel from left side and 200 from the top */
		setLocation(0, 0);
		
		/* Set application close when this frame is closed */
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		/* Layout will be set later */
		
		/* Set frame visible */
		setVisible(true);
		
		AudioManager.getInstance().loopPlay(System.getProperty("user.dir").concat("\\resources\\canzone_di_sottofondo.wav"));
		FieldController.getInstance().addObserver(this);
		FieldController.getInstance().getObservableForGameFinish().addObserver(observerVictory);
		showInsertionNPlayersDialog();
	}
}
