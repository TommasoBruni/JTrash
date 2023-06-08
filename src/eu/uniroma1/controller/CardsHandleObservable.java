package eu.uniroma1.controller;

import java.util.Observable;

public class CardsHandleObservable extends Observable
{
	public void setStatusChanged()
	{
		setChanged();
	}
}
