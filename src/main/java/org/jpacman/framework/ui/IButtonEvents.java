package org.jpacman.framework.ui;

/**
 * Events triggered when a button is clicked.
 * 
 * @author Arie van Deursen, TU Delft, Jan 9, 2012
 */

public interface IButtonEvents {
	
	/**
	 * Start the game.
	 */
	void start();
	
	/**
	 * Pause the game.
	 */
	void stop();
	
	/**
	 * Exit the game.
	 */
	void exit();
}
