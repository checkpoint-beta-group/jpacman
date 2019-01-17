package org.jpacman.test.framework.factory;

import static org.junit.Assert.assertEquals;

import org.jpacman.framework.factory.DefaultGameFactory;
import org.jpacman.framework.factory.FactoryException;
import org.jpacman.framework.factory.IGameFactory;
import org.jpacman.framework.factory.MapParser;
import org.jpacman.framework.model.Board;
import org.jpacman.framework.model.Game;
import org.jpacman.framework.model.IBoardInspector.SpriteType;
import org.junit.Before;
import org.junit.Test;

public class FactoryIntegrationTest {

	MapParser parser;

	private String[] map = new String[] { "#####", "#...#", "#GPG#", "#   #",
			"#####" };

	@Before
	public void setUp() {
		IGameFactory factory = new DefaultGameFactory();
		parser = new MapParser(factory);
	}

	@Test
	public void testFullMap() throws FactoryException {
		Game g = parser.parseMap(map);
		Board b = g.getBoard();

		// did we recognize the right sprites?
		assertEquals(SpriteType.EMPTY, b.spriteTypeAt(1, 3));
		assertEquals(SpriteType.PLAYER, b.spriteTypeAt(2, 2));
		assertEquals(SpriteType.GHOST, b.spriteTypeAt(1, 2));
		assertEquals(SpriteType.WALL, b.spriteTypeAt(0, 0));
		assertEquals(SpriteType.FOOD, b.spriteTypeAt(1, 1));

		// did we properly set the player?
		assertEquals(g.getPlayer(), b.spriteAt(2, 2));

		// were all ghosts added?
		assertEquals(2, g.getGhosts().size());
	}
}
