package eu.uniroma1.view.panel;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

import eu.uniroma1.model.Giocatore;

public class PannelloAvatarPunteggio extends JPanel implements Observer
{
	private JLabel labelIcon;
	private JPanel pannelloPerPunteggi;
	private JLabel labelNomeGiocatore;
	private JLabel labelPartiteGiocate;
	private JLabel labelPartiteVinte;
	private JLabel labelPartitePerse;

	@Override
	public void update(Observable o, Object arg)
	{
		Giocatore giocatore = (Giocatore)arg;
		
		labelNomeGiocatore.setText(giocatore.getNome());
		labelIcon.setIcon(giocatore.getAvatar());
	}
	
	public PannelloAvatarPunteggio(String nomeGiocatore, ImageIcon avatarIcon)
	{
		this(nomeGiocatore, avatarIcon, 0, 0, 0, null);
	}
	
	public PannelloAvatarPunteggio(String nomeGiocatore, ImageIcon avatarIcon, long partiteGiocate, 
								   long partiteVinte, long partitePerse, Observable observable)
	{
		labelNomeGiocatore = new JLabel(nomeGiocatore);
		labelIcon = new JLabel(avatarIcon);
		
		if (observable != null)
		{
			pannelloPerPunteggi = new JPanel();
			pannelloPerPunteggi.setLayout(new GridBagLayout());
			/* Significa che è il giocatore reale e non un robot */
			labelPartiteGiocate = new JLabel("Partite giocate: " + partiteGiocate + " ");
			labelPartiteVinte = new JLabel("Partite vinte: " + partiteVinte + " ");
			labelPartitePerse = new JLabel("Partite perse: " + partitePerse + " ");
			observable.addObserver(this);
		}

		setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.insets = new Insets(0, 0, 5, 0);
		
		add(labelIcon, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.insets = new Insets(0, 0, 0, 0);
		add(labelNomeGiocatore, gbc);
		
		if (observable != null)
		{
			GridBagConstraints gbcPerPunteggi = new GridBagConstraints();
			
			gbcPerPunteggi.gridx = 0;
			gbcPerPunteggi.gridy = 0;
			pannelloPerPunteggi.add(labelPartiteGiocate, gbcPerPunteggi);
			
			
			gbcPerPunteggi.gridx = 0;
			gbcPerPunteggi.gridy = 1;
			pannelloPerPunteggi.add(labelPartiteVinte, gbcPerPunteggi);

			gbcPerPunteggi.gridx = 0;
			gbcPerPunteggi.gridy = 2;
			pannelloPerPunteggi.add(labelPartitePerse, gbcPerPunteggi);
			
			gbc.gridx = 1;
			gbc.gridy = 0;
			gbc.insets = new Insets(0, 10, 0, 0);
			add(pannelloPerPunteggi, gbc);
		}
	}
}
