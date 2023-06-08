package eu.uniroma1.view.panel;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.Observable;
import java.util.Observer;
import java.util.stream.Collectors;

import javax.swing.Timer;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.Border;

import eu.uniroma1.controller.MainPlayerFieldController;
import eu.uniroma1.model.*;
import eu.uniroma1.model.carte.Card;
import eu.uniroma1.model.carte.CardColor;
import eu.uniroma1.model.carte.Value;
import eu.uniroma1.model.exceptions.DeckFinishedException;
import eu.uniroma1.model.exceptions.GameNotInProgressException;
import eu.uniroma1.model.exceptions.MoveNotAllowedException;
import eu.uniroma1.view.button.CardButton;
import eu.uniroma1.view.utils.DeckPosition;

public class CardsPanel extends JPanel implements Observer
{
	private CardButton[] cards;
	private Timer animationTimer;
	private boolean firstTime;
	private int xMovement;
	private int yMovement;
	
	/*
	@Override
	public void paint(Graphics g)
	{
		// TODO Auto-generated method stub
		super.paint(g);
		Graphics2D graphic2D = (Graphics2D) frameToDraw.getParent().getGraphics();
		
		if (firstTime)
		{
			System.out.println(frameToDraw);
			xMovement = frameToDraw.getCarteDaPescareButton().getBounds().x;
			yMovement = frameToDraw.getCarteDaPescareButton().getBounds().y;
			firstTime = false;
		}
		try
		{
			icon = new ImageIcon(System.getProperty("user.dir").concat("\\resources\\" + fileName));
		} 
		catch (Exception ex) 
		{
		    throw ex;
		}
		//frameToDraw.draw(icon, xMovement, yMovement);
		graphic2D.drawImage(icon.getImage(), xMovement, yMovement, null);
	}
	*/
	
	/*
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		xMovement += 5;
		yMovement += 5;
		repaint();
	}
	
	public void startAnimazione()
	{
		animationTimer = new Timer(50, this);
		animationTimer.restart();
	}
	*/
	
	public void disableAllCards()
	{
		for (CardButton card : cards)
			card.setEnabled(false);
	}
	
	public void enableAllCards()
	{
		for (CardButton card : cards)
			card.setEnabled(true);
	}
	
	public void restoreAllCardImage()
	{
		/* Restore all card images */
		for (CardButton carta : cards)
			carta.restoreCardImage();
	}
	
	@Override
	public void update(Observable o, Object arg) 
	{
		Value valore = ((Card)arg).getValore();
		int intValue;
		
		restoreAllCardImage();
		
		try
		{
			intValue = Integer.parseInt(valore.toString()) - 1;
		}
		catch(NumberFormatException ex)
		{
			if (valore.toString().equals(Value.ASSO.toString()))
			{
				intValue = 0;
			}
			else if (valore.toString().equals(Value.KING.toString()) || 
					 valore.toString().equals(Value.JOLLY.toString()))
			{
				for (CardButton carta : cards)
					carta.setHintCard();
				return;
			}
			else
			{
				return;
			}
		}
		
		cards[intValue].setHintCard();
	}
	
	public CardsPanel(DeckPosition posizioneDelMazzo) throws GameNotInProgressException, DeckFinishedException, MoveNotAllowedException
	{
		this(posizioneDelMazzo, null);
	}
	
	public CardsPanel(DeckPosition posizioneDelMazzo, Observable observable) throws GameNotInProgressException, DeckFinishedException, MoveNotAllowedException
	{
		//animationTimer = new Timer();
		int i, j;
		boolean isHorizontal = (posizioneDelMazzo == DeckPosition.SULLA_DX || posizioneDelMazzo == DeckPosition.SULLA_SX);
		JPanel pannelloCarteSuperiori = new JPanel();
		JPanel pannelloCarteInferiori = new JPanel();
		GridBagConstraints gbcTraPanelInfESup = new GridBagConstraints();
		GridBagConstraints gbcPerCarte = new GridBagConstraints();
		
		if (observable != null)
			observable.addObserver(this);
		
		firstTime = true;
		
		pannelloCarteSuperiori.setLayout(new GridBagLayout());
		pannelloCarteInferiori.setLayout(new GridBagLayout());
		setLayout(new GridBagLayout());
		
		cards = new CardButton[10];
		
		for (i = 0; i < cards.length / 2; i++)
		{
			cards[i] = new CardButton(MainPlayerFieldController.getInstance().nextCard(), posizioneDelMazzo, i);
			cards[i].addActionListener(new ActionListener() {	
				@Override
				public void actionPerformed(ActionEvent e) 
				{
					CardButton c = (CardButton)e.getSource();
					Card newCard;
					try 
					{
						newCard = MainPlayerFieldController.getInstance().getCardForReplacing(c.getPositionInTheField());
					}
					catch (MoveNotAllowedException e1) 
					{
						return;
					}
					
					if (newCard == null)
						return;
					/* TODO: remember the old card and take it as icon until the next move
					 * is chosen */
					c.changeCard(newCard);
					restoreAllCardImage();
				}
			});
			
			if (isHorizontal)
			{
				gbcPerCarte.gridx = 0;
				gbcPerCarte.gridy = i;
				gbcPerCarte.insets = new Insets(0, 0, 15, 0);
			}
			else
			{
				gbcPerCarte.gridx = i;
				gbcPerCarte.gridy = 0;
				gbcPerCarte.insets = new Insets(0, 0, 0, 15);
			}
			
			pannelloCarteSuperiori.add(cards[i], gbcPerCarte);
		}
		
		for (j = 0; j + i < cards.length; j++)
		{
			cards[j + i] = new CardButton(MainPlayerFieldController.getInstance().nextCard(), posizioneDelMazzo, i + j);
			cards[j + i].addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) 
				{
					CardButton c = (CardButton)e.getSource();
					Card newCard;
					
					try 
					{
						newCard = MainPlayerFieldController.getInstance().getCardForReplacing(c.getPositionInTheField());
					} 
					catch (MoveNotAllowedException e1) 
					{
						return;
					}
					
					if (newCard == null)
						return;
					/* TODO: remember the old card and take it as icon while the next move
					 * is not chosen */
					c.changeCard(newCard);
				}
			});
			if (isHorizontal)
			{
				gbcPerCarte.gridx = 0;
				gbcPerCarte.gridy = j;
				gbcPerCarte.insets = new Insets(0, 0, 15, 0);
			}
			else
			{
				gbcPerCarte.gridx = j;
				gbcPerCarte.gridy = 0;
				gbcPerCarte.insets = new Insets(0, 0, 0, 15);
			}
			pannelloCarteInferiori.add(cards[j + i], gbcPerCarte);
		}
		
		if (isHorizontal)
			gbcTraPanelInfESup.insets = new Insets(0, 0, 0, 5);
		else
			gbcTraPanelInfESup.insets = new Insets(0, 0, 5, 0);
		
		if (!isHorizontal)
		{
			gbcTraPanelInfESup.gridx = 1;
			gbcTraPanelInfESup.gridy = 0;
			gbcTraPanelInfESup.weightx = 0.1;
			gbcTraPanelInfESup.weighty = 0.1;
		}
		else
		{
			gbcTraPanelInfESup.gridx = 0;
			gbcTraPanelInfESup.gridy = 0;
			gbcTraPanelInfESup.weightx = 0.1;
			gbcTraPanelInfESup.weighty = 0.1;
		}
		
		add(pannelloCarteSuperiori, gbcTraPanelInfESup);
		gbcTraPanelInfESup.weightx = 1;
		gbcTraPanelInfESup.weighty = 1;
		if (isHorizontal)
		{
		    gbcTraPanelInfESup.gridx = 1;
		    gbcTraPanelInfESup.gridy = 0;
		}
		else
		{
		    gbcTraPanelInfESup.gridx = 1;
		    gbcTraPanelInfESup.gridy = 1;
		}
		add(pannelloCarteInferiori, gbcTraPanelInfESup);
	}
}
