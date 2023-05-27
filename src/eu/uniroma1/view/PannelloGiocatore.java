package eu.uniroma1.view;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.Border;

public abstract class PannelloGiocatore extends JPanel
{
	private JButton[] carte;
	private Timer animationTimer;
	private static int velocitaAnimazioneMS = 12;
	private String nomeGiocatore;
	private ImageIcon icon;
	
	public PannelloGiocatore(String nomeGiocatore, String fileName, int larghezzaCarta, int altezzaCarta,
							 int numeroColonne, int numeroRighe, int gapVerticale, int gapOrizzontale)
	{
		this.nomeGiocatore = nomeGiocatore;
		//animationTimer = new Timer();
		int i;
		boolean isLarger = larghezzaCarta > altezzaCarta;
		ImageIcon icon;
		Border bordoInterno = BorderFactory.createTitledBorder(this.nomeGiocatore);
		Border bordoEsterno = BorderFactory.createEmptyBorder(0, 0, 0, 0);
		Border bordoComposto = BorderFactory.createCompoundBorder(bordoEsterno, bordoInterno);
		JPanel pannelloCarteSuperiori = new JPanel();
		JPanel pannelloCarteInferiori = new JPanel();
		GridBagConstraints gbc = new GridBagConstraints();
		
		pannelloCarteSuperiori.setLayout(new GridLayout(numeroRighe, numeroColonne, gapOrizzontale, gapVerticale));
		pannelloCarteInferiori.setLayout(new GridLayout(numeroRighe, numeroColonne, gapOrizzontale, gapVerticale));
		
		
		setBorder(bordoComposto);
		setLayout(new GridBagLayout());
		
		setPreferredSize(new Dimension(600, 600));
		
		carte = new JButton[10];
		
		try
		{
			/* Leggo l'immagine salvata nella directory "resources" dentro il progetto */
			icon = new ImageIcon(System.getProperty("user.dir").concat("\\resources\\" + fileName));
		} 
		catch (Exception ex) 
		{
			/* Lanciare un'altra eccezione */
		    throw ex;
		}
		
		for (i = 0; i < carte.length / 2; i++)
		{
			carte[i] = new JButton();
			carte[i].setIcon(icon);
			carte[i].setPreferredSize(new Dimension(larghezzaCarta, altezzaCarta));
			pannelloCarteSuperiori.add(carte[i]);
		}
		for (; i < carte.length; i++)
		{
			carte[i] = new JButton();
			carte[i].setIcon(icon);
			carte[i].setPreferredSize(new Dimension(larghezzaCarta, altezzaCarta));
			pannelloCarteInferiori.add(carte[i]);
		}

		if (isLarger)
			gbc.insets = new Insets(0, 0, 0, 10);
		else
			gbc.insets = new Insets(0, 0, 10, 0);
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.weightx = 0.1;
		gbc.weighty = 0.1;
		
		add(pannelloCarteSuperiori, gbc);
		gbc.weightx = 1;
		gbc.weighty = 1;
		if (isLarger)
		{
		    gbc.gridx = 1;
		    gbc.gridy = 0;
		}
		else
		{
		    gbc.gridx = 0;
		    gbc.gridy = 1;
		}
		add(pannelloCarteInferiori, gbc);
	}
}
