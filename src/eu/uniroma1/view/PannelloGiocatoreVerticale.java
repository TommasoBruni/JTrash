package eu.uniroma1.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.border.Border;

import java.io.*;
import java.util.*;
import java.awt.*;

public class PannelloGiocatoreVerticale extends PannelloGiocatore 
{
	private static final String nomeFile = "carta_da_gioco_verticale.jpg";
	private static final int larghezzaCarta = 39;
	private static final int altezzaCarta = 55;
	private static final int numeroColonne = 5;
	private static final int numeroRighe = 1;
	private static final int gapVerticale = 0;
	private static final int gapOrizzontale = 50;
	
	public PannelloGiocatoreVerticale(String nomeGiocatore)
	{
		super(nomeGiocatore, nomeFile, larghezzaCarta, altezzaCarta, numeroColonne, numeroRighe, gapVerticale, gapOrizzontale);
	}
}
