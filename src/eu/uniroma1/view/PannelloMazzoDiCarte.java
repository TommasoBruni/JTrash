package eu.uniroma1.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.*;

public class PannelloMazzoDiCarte extends JPanel
{
	private JButton carteDaPescare;
	private JButton carteScartate;
	
	public JButton getCarteDaPescareButton()
	{
		return carteDaPescare;
	}
	
	public PannelloMazzoDiCarte()
	{
		ImageIcon icon;
		carteDaPescare = new JButton();
		carteScartate = new JButton();
		
		try
		{
			/* Leggo l'immagine salvata nella directory "resources" dentro il progetto */
			icon = new ImageIcon(System.getProperty("user.dir").concat("\\resources\\carta_da_gioco_verticale.jpg"));
		} 
		catch (Exception ex) 
		{
			/* TODO: Lanciare un'altra eccezione */
		    throw ex;
		}
		carteDaPescare.setIcon(icon);
		carteDaPescare.setPreferredSize(new Dimension(icon.getIconWidth(), icon.getIconHeight()));
		carteDaPescare.setMaximumSize(new Dimension(icon.getIconWidth(), icon.getIconHeight()));
		carteDaPescare.setMinimumSize(new Dimension(icon.getIconWidth(), icon.getIconHeight()));
		
		carteScartate.setIcon(icon);
		carteScartate.setPreferredSize(new Dimension(icon.getIconWidth(), icon.getIconHeight()));
		carteScartate.setMaximumSize(new Dimension(icon.getIconWidth(), icon.getIconHeight()));
		carteScartate.setMinimumSize(new Dimension(icon.getIconWidth(), icon.getIconHeight()));
		
        //setBorder(new EmptyBorder(10, 10, 10, 10));	
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
		
		gbc.weightx = 0.01;
		gbc.weighty = 0.01;
		gbc.anchor = GridBagConstraints.LINE_END;
        
        JPanel buttons = new JPanel(new GridBagLayout());
        buttons.add(carteDaPescare, gbc);
        
		gbc.gridx = 1;
		gbc.gridy = 0;
		
		gbc.weightx = 0.01;
		gbc.weighty = 0.01;
		
		gbc.anchor = GridBagConstraints.LINE_START;
		
		/* Per inserire un po' di spazio tra le carte da pescare e quelle scartate */
		gbc.insets = new Insets(0, 20, 0, 0);
		buttons.add(carteScartate, gbc);
        
        add(buttons);
	}
}
