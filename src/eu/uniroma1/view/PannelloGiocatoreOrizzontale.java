package eu.uniroma1.view;

import java.awt.Container;
import java.awt.Point;
import java.util.Observable;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class PannelloGiocatoreOrizzontale extends PannelloGiocatore
{
	private static final int numeroColonne = 1;
	private static final int numeroRighe = 5;
	private static final int gapVerticale = 50;
	private static final int gapOrizzontale = 0;
	
	public PannelloGiocatoreOrizzontale(String nomeGiocatore, PosizioneDelMazzo posizioneDelMazzo,
										ImageIcon avatarIcon)
	{
		this(nomeGiocatore, avatarIcon, posizioneDelMazzo, null);
	}
	
	public PannelloGiocatoreOrizzontale(String nomeGiocatore, ImageIcon avatarIcon,
										PosizioneDelMazzo posizioneDelMazzo, Observable observable)
	{
		super(nomeGiocatore, numeroColonne, numeroRighe, 
			  gapVerticale, gapOrizzontale, avatarIcon, posizioneDelMazzo, observable);
	}
}
