package org.jpacman.test.framework.ui;

import org.jpacman.framework.controller.AbstractGhostMover;
import org.jpacman.framework.factory.FactoryException;
import org.jpacman.framework.ui.IKeyboardEvents;
import org.jpacman.framework.ui.MainUI;
import org.junit.Test;

/**
 * Smoke test for the main UI: Just start it
 * and interact with it to see if no exceptions are thrown.
 * The bare minimum for any test suite.
 * 
 * @author Arie van Deursen, TU Delft, Feb 4, 2012
 */
public class MainUISmokeTest {
	
	@Test
	public void testUIActions() throws FactoryException, InterruptedException {
		MainUI ui = new MainUI();
		ui.initialize();
		ui.start();
		IKeyboardEvents eventHandler = ui.eventHandler();

		// now trigger interesting events.
		eventHandler.start();
		eventHandler.up();
		eventHandler.left();

		// give the monsters some time to move.
		Thread.sleep(10 * AbstractGhostMover.DELAY);

		// and attempt some moves again.
		eventHandler.down();
		eventHandler.stop();
		eventHandler.start();
		eventHandler.right();
		
		// and we're done.
		eventHandler.exit();
	}

}
