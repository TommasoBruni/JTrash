package eu.uniroma1.view;

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
import eu.uniroma1.model.*;

public abstract class PannelloGiocatore extends JPanel implements Observer
{
	private ButtonCarta[] carte;
	private Timer animationTimer;
	private String nomeGiocatore;
	private JLabel labelIcon;
	private ImageIcon icon;
	private boolean firstTime;
	private int xMovement;
	private int yMovement;
	
	private void setBorderWithName(String nomeGiocatore)
	{
		Border bordoInterno = BorderFactory.createTitledBorder(nomeGiocatore);
		Border bordoEsterno = BorderFactory.createEmptyBorder(0, 0, 0, 0);
		Border bordoComposto = BorderFactory.createCompoundBorder(bordoEsterno, bordoInterno);
		
		setBorder(bordoComposto);
	}
	
	@Override
	public void update(Observable o, Object arg)
	{
		/* Primo nome giocatore, secondo nickname e terzo icona */
		Giocatore giocatore = (Giocatore)arg;
		setBorderWithName(giocatore.getNome());

		labelIcon.setIcon(giocatore.getAvatar());
	}
	
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
	public PannelloGiocatore(String nomeGiocatore, int numeroColonne, int numeroRighe,
			 int gapVerticale, int gapOrizzontale, ImageIcon avatarIcon, PosizioneDelMazzo posizionedelMazzo)
	{
		this(nomeGiocatore, numeroColonne, numeroRighe, gapVerticale, gapOrizzontale, avatarIcon, posizionedelMazzo, null);
	}
	
	public PannelloGiocatore(String nomeGiocatore, int numeroColonne, int numeroRighe,
							 int gapVerticale, int gapOrizzontale, ImageIcon avatarIcon,
							 PosizioneDelMazzo posizioneDelMazzo, Observable observable)
	{
		this.nomeGiocatore = nomeGiocatore;
		//animationTimer = new Timer();
		int i;
		boolean isHorizontal = numeroColonne < numeroRighe;
		JPanel pannelloCarteSuperiori = new JPanel();
		JPanel pannelloCarteInferiori = new JPanel();
		GridBagConstraints gbc = new GridBagConstraints();
		
		firstTime = true;
		labelIcon = new JLabel(avatarIcon);
		
		if (observable != null)
			observable.addObserver(this);
		
		pannelloCarteSuperiori.setLayout(new GridLayout(numeroRighe, numeroColonne, gapOrizzontale, gapVerticale));
		pannelloCarteInferiori.setLayout(new GridLayout(numeroRighe, numeroColonne, gapOrizzontale, gapVerticale));
		
		setBorderWithName(this.nomeGiocatore);
		setLayout(new GridBagLayout());
		
		setPreferredSize(new Dimension(600, 650));
		
		carte = new ButtonCarta[10];
		
		for (i = 0; i < carte.length / 2; i++)
		{
			carte[i] = new ButtonCarta("Jolly", posizioneDelMazzo);
			pannelloCarteSuperiori.add(carte[i]);
		}
		for (; i < carte.length; i++)
		{
			carte[i] = new ButtonCarta("♥10", posizioneDelMazzo);
			pannelloCarteInferiori.add(carte[i]);
		}
		carte[0].gira();
		
		if (!isHorizontal)
		{
			gbc.gridx = 0;
			gbc.gridy = 0;
			gbc.weightx = 0.1;
			gbc.weighty = 0.1;
		}
		else
		{
			gbc.gridx = 0;
			gbc.gridy = 1;
			gbc.weightx = 0.1;
			gbc.weighty = 0.1;
			gbc.insets = new Insets(10, 0, 0, 0);
		}
		
		add(labelIcon, gbc);
		
		if (isHorizontal)
			gbc.insets = new Insets(0, 0, 0, 10);
		else
			gbc.insets = new Insets(0, 0, 10, 0);
		
		if (!isHorizontal)
		{
			gbc.gridx = 1;
			gbc.gridy = 0;
			gbc.weightx = 0.1;
			gbc.weighty = 0.1;
		}
		else
		{
			gbc.gridx = 0;
			gbc.gridy = 0;
			gbc.weightx = 0.1;
			gbc.weighty = 0.1;
		}
		
		add(pannelloCarteSuperiori, gbc);
		gbc.weightx = 1;
		gbc.weighty = 1;
		if (isHorizontal)
		{
		    gbc.gridx = 1;
		    gbc.gridy = 0;
		}
		else
		{
		    gbc.gridx = 1;
		    gbc.gridy = 1;
		}
		add(pannelloCarteInferiori, gbc);
	}
}
