package eu.uniroma1.controller;

import java.util.Observable;

public class GenericObservable extends Observable
{
	public void setStatusChanged()
	{
		setChanged();
	}
}
