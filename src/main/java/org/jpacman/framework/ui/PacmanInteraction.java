package org.jpacman.framework.ui;

import java.util.ArrayList;
import java.util.List;

import org.jpacman.framework.controller.IController;
import org.jpacman.framework.model.Direction;
import org.jpacman.framework.model.IGameInteractor;

/**
 * Class mapping events triggered by the UI to 
 * actions in the underlying Pacman model.
 * 
 * @author Arie van Deursen, TU Delft, Jan 30, 2012
 */
public class PacmanInteraction implements IKeyboardEvents {
	
	/**
	 * The states a match can be in.
	 */
	public enum MatchState {
		PLAYING,
		PAUSING
	}
	
	/**
	 * The state of the ongoing match.
	 */
	private MatchState currentState = MatchState.PAUSING;
	
	/**
	 * Window to be deleted upon game exit.
	 */
	private final IDisposable disposableWindow;
	
	/**
	 * Model of the game.
	 */
	private final IGameInteractor gameInteraction;
	
	/**
	 * Controller for moving the ghosts around.
	 */
	private final List<IController> controllers;
	
	/**
	 * Create a new interaction object.
	 * @param window The top level window.
	 * @param game The underlying game.
	 */
	public PacmanInteraction(IDisposable window, IGameInteractor game) {
		disposableWindow = window;
		gameInteraction = game;
		controllers = new ArrayList<IController>();
	}
	
	@Override
	public void start() {
		if (currentState == MatchState.PAUSING) { 
			for (IController c: controllers) {
				c.start();
			}
			currentState = MatchState.PLAYING;
		}
	}

	@Override
	public void stop() {
		if (currentState == MatchState.PLAYING) {
			for (IController c: controllers) {
				c.stop();
			}
			currentState = MatchState.PAUSING;
		}
	}

	@Override
	public void exit() {
		for (IController c: controllers) {
			c.stop();
		}
		disposableWindow.dispose();
	}

	@Override
	public void up() {
		movePlayer(Direction.UP);
	}

	@Override
	public void down() {
		movePlayer(Direction.DOWN);
	}

	@Override
	public void left() {
		movePlayer(Direction.LEFT);
	}

	@Override
	public void right() {
		movePlayer(Direction.RIGHT);
	}
	
	/**
	 * Move the player in the given direction,
	 * provided we are in the playing state.
	 * @param dir New direction.
	 */
	private void movePlayer(Direction dir) {
		if (currentState == MatchState.PLAYING) {
			gameInteraction.movePlayer(dir);
		}
		// else: ignore move event.
	}
	
	/**
	 * Add an external controller, which should be stopped/started
	 * via the ui.
	 * @param controller The controller to be added.
	 */
	public void addController(IController controller) {
		controllers.add(controller);
	}
	
}
