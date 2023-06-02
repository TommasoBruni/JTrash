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
	private JLabel labelNomeGiocatore;

	@Override
	public void update(Observable o, Object arg)
	{
		Giocatore giocatore = (Giocatore)arg;
		
		labelNomeGiocatore.setText(giocatore.getNome());
		labelIcon.setIcon(giocatore.getAvatar());
	}
	
	public PannelloAvatarPunteggio(String nomeGiocatore, ImageIcon avatarIcon, Observable observable)
	{
		labelNomeGiocatore = new JLabel(nomeGiocatore);
		labelIcon = new JLabel(avatarIcon);
		if (observable != null)
			observable.addObserver(this);
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
	}
}
