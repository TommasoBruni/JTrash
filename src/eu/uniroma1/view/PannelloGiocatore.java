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
	private JLabel label;
	
	public PannelloGiocatore()
	{
		int i;
		GridBagConstraints gbc = new GridBagConstraints();		
		Border bordoInterno = BorderFactory.createTitledBorder("Player");
		Border bordoEsterno = BorderFactory.createEmptyBorder(0, 5, 5, 5);
		Border bordoComposto = BorderFactory.createCompoundBorder(bordoEsterno, bordoInterno);
		label = new JLabel("Ciap");
		
		setBorder(bordoComposto);
		
		carte = new JButton[10];
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.insets = new Insets(0, 0, 0, 5);
		//setLayout(new BorderLayout());

		setPreferredSize(new Dimension(70, 150));
		
		for (i = 0; i < carte.length; i++)
		{
			carte[i] = new JButton();
			carte[i].setPreferredSize(new Dimension(30, 40));
			try
			{
				ImageIcon icon = new ImageIcon("C:\\Users\\tbruni\\Desktop\\Università\\Metodologie di programmazione\\JTrash\\src\\eu\\uniroma1\\view\\resources\\carta_da_gioco.jpg");
				/* Leggo l'immagine salvata nella directory "resources" dentro il progetto */
				carte[i].setIcon(icon);
			} 
			catch (Exception ex) 
			{
			    System.out.println(ex);
			}
			add(carte[i]);
			if ((i % 2) == 0)
				gbc.gridy += 1;
			else
				gbc.gridx += 1;
		}
		
		add(label);
		
		carte[0].addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int x = carte[1].getLocation().x + 5;
				int y = carte[1].getLocation().y + 5;
				
				carte[1].setLocation(x, y);
			}
		});
	}
}
