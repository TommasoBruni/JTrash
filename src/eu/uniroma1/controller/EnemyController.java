package eu.uniroma1.controller;

import java.util.Observable;

import eu.uniroma1.model.carte.Card;
import eu.uniroma1.model.exceptions.MoveNotAllowedException;

public class EnemyController extends PlayerController
{
	@Override
	public void operationWithSelectedCard(Card card) throws MoveNotAllowedException 
	{
		/* TODO: implementare logica */
		throw new MoveNotAllowedException();
	}
}
