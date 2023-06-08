package eu.uniroma1.controller;

import java.util.Observable;

public class ObservableForHintCard extends Observable
{
	public void setStatusChanged()
	{
		setChanged();
	}
}
