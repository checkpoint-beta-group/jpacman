package org.jpacman.framework.ui;

/**
 * The high level events that can occur in Pacman,
 * and that can be triggered via keyboard shortcuts.
 * 
 * @author Arie van Deursen, TU Delft, Feb 4, 2012
 */
public interface IKeyboardEvents extends IButtonEvents {

	/**
	 * Move the player one up.
	 */
	void up();
	
	/**
	 * Move the player one down.
	 */
	void down();
	
	/**
	 * Move the player one to the left.
	 */
	void left();
	
	/**
	 * Move the player on to the right.
	 */
	void right();
	
}
