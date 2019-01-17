package org.jpacman.test.framework.ui;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;

import org.jpacman.framework.controller.IController;
import org.jpacman.framework.model.Direction;
import org.jpacman.framework.model.IGameInteractor;
import org.jpacman.framework.ui.IDisposable;
import org.jpacman.framework.ui.PacmanInteraction;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Test the state machine for the PAUSED / PLAYING
 * states, using Binder's approach to testing
 * state machines.
 * 
 * @author Arie van Deursen, TU Delft, Feb 2, 2012
 */
@RunWith(MockitoJUnitRunner.class)
public class PacmanInteractionTest {

	@Mock private IController ghostController;
	@Mock private IDisposable mainWindow;
	@Mock private IGameInteractor theGame;
	
	private PacmanInteraction pacman;
	
	/**
	 * Create the state machine.
	 */
	@Before
	public void setUp() {
		pacman = new PacmanInteraction(mainWindow, theGame);
		pacman.addController(ghostController);
	}
	
	@Test
	public void testStartEvent() {
		pacman.start();
		verifyZeroInteractions(theGame);
		verifyZeroInteractions(mainWindow);
		verify(ghostController).start();
	}
	
	@Test 
	public void testT1PauseAndExit() {
		pacman.exit();
		verifyZeroInteractions(theGame);
		verify(mainWindow).dispose();
		verify(ghostController).stop();
	}
	
	@Test 
	public void testT2PlayingExit() {
		pacman.start();
		pacman.exit();
		verifyZeroInteractions(theGame);
		verify(mainWindow).dispose();
		verify(ghostController).stop();
	}
	
	@Test
	public void testT3startAndMove() {
		pacman.start();
		pacman.up();
		verify(theGame, times(1)).movePlayer(Direction.UP);
		verifyNoMoreInteractions(theGame);
	}
	
	@Test
	public void testT4startAndStop() {
		pacman.start();
		pacman.stop();
		verifyZeroInteractions(theGame);
		verify(ghostController).stop();
		verifyZeroInteractions(mainWindow);
	}
	
	@Test
	public void testT5SneakyPausedPlay() {
		pacman.down();
		verifyZeroInteractions(theGame);		
	}
	
	@Test
	public void testT6SneakyPausedStop() {
		pacman.stop();
		verifyZeroInteractions(ghostController);		
	}
	
	@Test 
	public void testT7SneakyPlayingStart() {
		pacman.start();
		verify(ghostController).start();
		pacman.start();
		verifyNoMoreInteractions(ghostController);
	}
	
	@Test
	public void testT8RemainingMoves() {
		pacman.start();
		pacman.left();
		verify(theGame, times(1)).movePlayer(Direction.LEFT);
		pacman.right();
		verify(theGame, times(1)).movePlayer(Direction.RIGHT);		
	}
}
