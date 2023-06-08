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

import eu.uniroma1.controller.PlayingFieldController;
import eu.uniroma1.model.*;
import eu.uniroma1.model.carte.Carte;
import eu.uniroma1.model.carte.Colore;
import eu.uniroma1.model.carte.Valori;
import eu.uniroma1.model.exceptions.MazzoFinitoException;
import eu.uniroma1.model.exceptions.PartitaNonInCorsoException;
import eu.uniroma1.view.button.ButtonCarta;
import eu.uniroma1.view.utils.PosizioneDelMazzo;

public class CardsPanel extends JPanel implements Observer
{
	private ButtonCarta[] carte;
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
	
	public void restoreAllCardImage()
	{
		/* Restore all card images */
		for (ButtonCarta carta : carte)
			carta.restoreCardImage();
	}
	
	@Override
	public void update(Observable o, Object arg) 
	{
		Valori valore = ((Carte)arg).getValore();
		int intValue;
		
		restoreAllCardImage();
		
		try
		{
			intValue = Integer.parseInt(valore.toString()) - 1;
		}
		catch(NumberFormatException ex)
		{
			if (valore.toString().equals(Valori.ASSO.toString()))
			{
				intValue = 0;
			}
			else if (valore.toString().equals(Valori.KING.toString()) || 
					 valore.toString().equals(Valori.JOLLY.toString()))
			{
				for (ButtonCarta carta : carte)
					carta.setHintCard();
				return;
			}
			else
			{
				return;
			}
		}
		
		carte[intValue].setHintCard();
	}
	
	public CardsPanel(PosizioneDelMazzo posizioneDelMazzo) throws PartitaNonInCorsoException, MazzoFinitoException
	{
		this(posizioneDelMazzo, null);
	}
	
	public CardsPanel(PosizioneDelMazzo posizioneDelMazzo, Observable observable) throws PartitaNonInCorsoException, MazzoFinitoException
	{
		//animationTimer = new Timer();
		int i, j;
		boolean isHorizontal = (posizioneDelMazzo == PosizioneDelMazzo.SULLA_DX || posizioneDelMazzo == PosizioneDelMazzo.SULLA_SX);
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
		
		carte = new ButtonCarta[10];
		
		for (i = 0; i < carte.length / 2; i++)
		{
			carte[i] = new ButtonCarta(PlayingFieldController.getInstance().prossimaCarta(), posizioneDelMazzo, i);
			carte[i].addActionListener(new ActionListener() {	
				@Override
				public void actionPerformed(ActionEvent e) 
				{
					ButtonCarta c = (ButtonCarta)e.getSource();
					Carte newCard = PlayingFieldController.getInstance().getCardForReplacing(c.getPositionInTheField());
					
					if (newCard == null)
						return;
					/* TODO: remember the old card and take it as icon while the next move
					 * is not chosen */
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
			
			pannelloCarteSuperiori.add(carte[i], gbcPerCarte);
		}
		
		for (j = 0; j + i < carte.length; j++)
		{
			carte[j + i] = new ButtonCarta(PlayingFieldController.getInstance().prossimaCarta(), posizioneDelMazzo, i + j);
			carte[j + i].addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) 
				{
					ButtonCarta c = (ButtonCarta)e.getSource();
					Carte newCard = PlayingFieldController.getInstance().getCardForReplacing(c.getPositionInTheField());
					
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
			pannelloCarteInferiori.add(carte[j + i], gbcPerCarte);
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
