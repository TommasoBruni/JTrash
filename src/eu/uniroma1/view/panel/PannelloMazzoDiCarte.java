package eu.uniroma1.view.panel;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

import eu.uniroma1.controller.Controller;
import eu.uniroma1.model.eccezioni.MazzoFinitoException;
import eu.uniroma1.model.eccezioni.PartitaNonInCorsoException;
import eu.uniroma1.view.button.ButtonCarta;
import eu.uniroma1.view.utils.PosizioneDelMazzo;

import javax.swing.*;

public class PannelloMazzoDiCarte extends JPanel
{
	private ButtonCarta carteDaPescare;
	private ButtonCarta carteScartate;
	
	public JButton getCarteDaPescareButton()
	{
		return carteDaPescare;
	}
	
	public PannelloMazzoDiCarte()
	{
		try 
		{
			carteDaPescare = new ButtonCarta(Controller.getInstance().prossimaCarta(), PosizioneDelMazzo.IN_ALTO);
			carteScartate = new ButtonCarta(Controller.getInstance().prossimaCarta(), PosizioneDelMazzo.IN_ALTO);
			carteScartate.gira();
		} 
		catch (PartitaNonInCorsoException | MazzoFinitoException e)
		{
			/* Non accadrà mai stiamo creando adesso il campo di gioco */
			e.printStackTrace();
		}
		
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
