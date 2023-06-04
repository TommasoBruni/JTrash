package eu.uniroma1.view.panel;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.Border;

import eu.uniroma1.controller.ControllerCampoDiGioco;
import eu.uniroma1.model.eccezioni.MazzoFinitoException;
import eu.uniroma1.model.eccezioni.PartitaNonInCorsoException;
import eu.uniroma1.view.button.ButtonCarta;

public class PannelloTrash extends JPanel
{
	private ButtonCarta carteScartate;
	
	public PannelloTrash()
	{
		try 
		{
			carteScartate = new ButtonCarta(ControllerCampoDiGioco.getInstance().prossimaCarta());
			carteScartate.gira();
		} 
		catch (PartitaNonInCorsoException | MazzoFinitoException e)
		{
			/* Non succederà mai, stiamo creando ora il campo di gioco */
		}
		Border bordoInterno = BorderFactory.createTitledBorder("Trash");
		Border bordoEsterno = BorderFactory.createEmptyBorder(0, 5, 5, 5);
		Border bordoComposto = BorderFactory.createCompoundBorder(bordoEsterno, bordoInterno);
		
		setBorder(bordoComposto);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        
		gbc.gridx = 0;
		gbc.gridy = 0;
		
		gbc.weightx = 0.01;
		gbc.weighty = 0.01;
		add(carteScartate, gbc);
	}
}
