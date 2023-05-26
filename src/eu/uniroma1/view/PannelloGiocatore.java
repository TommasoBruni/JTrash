package eu.uniroma1.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

import java.io.*;
import java.util.*;
import java.awt.*;

public class PannelloGiocatore extends JPanel 
{
	private JButton[] carte;
	private Timer animationTimer;
	private static int velocitaAnimazioneMS = 12;
	
	public PannelloGiocatore()
	{
		animationTimer = new Timer();
		int i;
		ImageIcon icon;
		Border bordoInterno = BorderFactory.createTitledBorder("Player");
		Border bordoEsterno = BorderFactory.createEmptyBorder(0, 0, 0, 0);
		Border bordoComposto = BorderFactory.createCompoundBorder(bordoEsterno, bordoInterno);
		
		setBorder(bordoComposto);
		
		carte = new JButton[10];

		setPreferredSize(new Dimension(70, 120));
		
		try
		{
			/* Leggo l'immagine salvata nella directory "resources" dentro il progetto */
			icon = new ImageIcon(System.getProperty("user.dir").concat("\\resources\\carta_da_gioco.jpg"));
		} 
		catch (Exception ex) 
		{
			/* Lanciare un'altra eccezione */
		    throw ex;
		}
		
		for (i = 0; i < carte.length; i++)
		{
			carte[i] = new JButton();
			carte[i].setIcon(icon);
			carte[i].setPreferredSize(new Dimension(30, 40));
			add(carte[i]);
		}
				
		animationTimer.schedule(new TimerTask() {
			
			@Override
			public void run() {
				int x = carte[1].getLocation().x - 5;
				int y = carte[1].getLocation().y - 5;
				
				carte[1].setLocation(x, y);
			}
		}, 0, velocitaAnimazioneMS);
	}
}
