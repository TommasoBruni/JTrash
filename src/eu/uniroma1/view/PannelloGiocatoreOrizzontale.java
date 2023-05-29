package eu.uniroma1.view;

import java.awt.Container;
import java.awt.Point;

import javax.swing.JFrame;

public class PannelloGiocatoreOrizzontale extends PannelloGiocatore
{
	private static final String nomeFile = "carta_da_gioco_orizzontale.jpg";
	private static final int larghezzaCarta = 55;
	private static final int altezzaCarta = 40;
	private static final int numeroColonne = 1;
	private static final int numeroRighe = 5;
	private static final int gapVerticale = 50;
	private static final int gapOrizzontale = 0;
	
	public PannelloGiocatoreOrizzontale(String nomeGiocatore)
	{
		super(nomeGiocatore, nomeFile, larghezzaCarta, altezzaCarta, numeroColonne, numeroRighe, gapVerticale, gapOrizzontale);
	}
}
