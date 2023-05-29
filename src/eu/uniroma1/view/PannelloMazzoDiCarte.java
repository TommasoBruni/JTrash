package eu.uniroma1.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

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
		
        //setBorder(new EmptyBorder(10, 10, 10, 10));
		/*
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        //gbc.anchor = GridBagConstraints.NORTH;

        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        JPanel buttons = new JPanel(new GridBagLayout());
        buttons.add(carteDaPescare, gbc);
        
        add(buttons);
        */
		setLayout(new BorderLayout());
		JPanel buttons = new JPanel(new BorderLayout());
		buttons.add(carteDaPescare, BorderLayout.CENTER);
		add(buttons, BorderLayout.CENTER);
	}
}
