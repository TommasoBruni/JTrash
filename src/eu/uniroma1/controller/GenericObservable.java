package eu.uniroma1.controller;

import java.util.Observable;

/**
 * General purpose observable. 
 */
public class GenericObservable extends Observable
{
	
	/**
	 * Set status changed. 
	 */
	public void setStatusChanged()
	{
		setChanged();
	}
}
