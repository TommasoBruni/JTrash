package eu.uniroma1.controller;

/**
 * Player current state.
 */
public enum PlayerState 
{
	/**
	 * Turn started state. 
	 */
	TURN_STARTED,
	/**
	 * Picked card state. 
	 */
	PICKED_CARD,
	/**
	 * Exchanging card state. 
	 */
	EXCHANGING,
	/**
	 * Turn over state. 
	 */
	TURN_IS_OVER
}
