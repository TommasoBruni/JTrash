package eu.uniroma1.view.panel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.imageio.ImageIO;
import javax.swing.Timer;
import javax.swing.border.Border;

import eu.uniroma1.model.eccezioni.MazzoFinitoException;
import eu.uniroma1.model.eccezioni.PartitaNonInCorsoException;
import eu.uniroma1.view.PosizioneDelMazzo;

import javax.swing.*;

import java.io.*;
import java.util.*;
import java.awt.*;

public class PannelloGiocatoreVerticale extends PannelloGiocatore
{
	private static final int numeroColonne = 5;
	private static final int numeroRighe = 1;
	private static final int gapVerticale = 0;
	private static final int gapOrizzontale = 50;
	
	public PannelloGiocatoreVerticale(String nomeGiocatore, PosizioneDelMazzo posizioneDelMazzo,
									  ImageIcon avatarIcon) throws PartitaNonInCorsoException, MazzoFinitoException
	{
		this(nomeGiocatore, avatarIcon, posizioneDelMazzo, null);
	}
	
	public PannelloGiocatoreVerticale(String nomeGiocatore, ImageIcon avatarIcon,
									  PosizioneDelMazzo posizioneDelMazzo, Observable observable) throws PartitaNonInCorsoException, MazzoFinitoException
	{
		super(nomeGiocatore, numeroColonne, numeroRighe,
			  gapVerticale, gapOrizzontale, avatarIcon, posizioneDelMazzo, observable);
	}
}
