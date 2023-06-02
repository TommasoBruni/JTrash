package eu.uniroma1.view.panel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.imageio.ImageIO;
import javax.swing.Timer;
import javax.swing.border.Border;

import eu.uniroma1.model.eccezioni.MazzoFinitoException;
import eu.uniroma1.model.eccezioni.PartitaNonInCorsoException;
import eu.uniroma1.view.utils.PosizioneDelMazzo;

import javax.swing.*;

import java.io.*;
import java.util.*;
import java.awt.*;

public class PannelloCarteVerticale extends PannelloCarte
{
	private static final int numeroColonne = 5;
	private static final int numeroRighe = 1;
	private static final int gapVerticale = 0;
	private static final int gapOrizzontale = 50;
	
	public PannelloCarteVerticale(PosizioneDelMazzo posizioneDelMazzo) throws PartitaNonInCorsoException, MazzoFinitoException
	{
		super(posizioneDelMazzo);
	}
}
