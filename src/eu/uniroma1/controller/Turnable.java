package eu.uniroma1.controller;

/**
 * Interface to get the right rotation of the players.
 */
@FunctionalInterface
public interface Turnable 
{
	/**
	 * Obtain the right next turn based on the current one.
	 * @param currentTurn current player turn index.
	 * @param nPlayingPlayers number of players in game.
	 * @return next turn.
	 */
	int getNextTurn(int currentTurn, int nPlayingPlayers);
}
