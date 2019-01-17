package org.jpacman.test.framework.model;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.jpacman.framework.model.PointManager;
import org.junit.Test;

public class PointManagerTest {

	@Test
	public void testAddAndConsume() {
		PointManager pm = new PointManager();

		pm.addPointsToBoard(1);
		assertFalse(pm.allEaten());

		pm.consumePointsOnBoard(1);
		assertTrue(pm.allEaten());
	}

}
