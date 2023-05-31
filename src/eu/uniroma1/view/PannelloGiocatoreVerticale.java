package eu.uniroma1.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.imageio.ImageIO;
import javax.swing.Timer;
import javax.swing.border.Border;
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
	
	public PannelloGiocatoreVerticale(String nomeGiocatore, ImageIcon avatarIcon)
	{
		this(nomeGiocatore, avatarIcon, null);
	}
	
	public PannelloGiocatoreVerticale(String nomeGiocatore, ImageIcon avatarIcon, Observable observable)
	{
		super(nomeGiocatore, numeroColonne, numeroRighe,
			  gapVerticale, gapOrizzontale, avatarIcon, observable);
	}
}
