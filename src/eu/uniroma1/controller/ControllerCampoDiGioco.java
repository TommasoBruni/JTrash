package eu.uniroma1.controller;

import java.util.List;
import java.util.Observable;

import javax.swing.ImageIcon;

import eu.uniroma1.model.Giocatore;
import eu.uniroma1.model.carte.*;
import eu.uniroma1.model.carte.MazzoDiCarte.MazzoDiCarteBuilder;
import eu.uniroma1.model.eccezioni.MazzoFinitoException;
import eu.uniroma1.model.eccezioni.PartitaNonInCorsoException;

public class ControllerCampoDiGioco extends Observable
{
	private static ControllerCampoDiGioco controller;
	private MazzoDiCarte mazzoDiCarte;
	private Carte ultimaCartaScartataSelezionata;
	
	public void iniziaPartita()
	{
		mazzoDiCarte = switch(ControllerGiocatore.getInstance().getNumeroGiocatoriInPartita())
					   {
					        case 2 -> new MazzoDiCarteBuilder()
							.mischia()
							.build();
					        default-> new MazzoDiCarteBuilder()
					        .combina(new MazzoDiCarteBuilder().build())
							.mischia()
							.build(); 
					   };
	}
	
	/**
	 * Ritorna la carta successiva nel mazzo se presente
	 * @return carta successiva nel mazzo
	 * @throws PartitaNonInCorsoException se la partita non è iniziata oppure è appena finita
	 * @throws MazzoFinitoException se non ci sono più carte nel mazzo 
	 */
	public Carte prossimaCarta() throws PartitaNonInCorsoException, MazzoFinitoException
	{
		Carte carta;
		
		if (mazzoDiCarte == null)
			throw new PartitaNonInCorsoException();
		try
		{
			carta = mazzoDiCarte.prossimaCarta();
		}
		catch(MazzoFinitoException ex)
		{
			/* Partita finita. */
			mazzoDiCarte = null;
			throw ex;
		}
		return carta;
	}
	
	public void ultimaCartaSelezionata(Carte carta)
	{
		ultimaCartaScartataSelezionata = carta;
		setChanged();
		notifyObservers(ultimaCartaScartataSelezionata);
	}
	
	public static ControllerCampoDiGioco getInstance()
	{
		if (controller == null)
			controller = new ControllerCampoDiGioco();
		return controller;
	}
	
	private ControllerCampoDiGioco()
	{
	}
}
