package org.jpacman.framework.model;

/**
 * The rectangular board containing the sprites.
 * 
 * @author Arie van Deursen, TU Delft, Jan 22, 2012
 */
public class Board implements IBoardInspector {

	private final int height;
	private final int width;
	private final Tile[][] tiles;
	
	/**
	 * Create a new board.
	 * @param w Width of the board
	 * @param h Height of the board.
	 */
	public Board(int w, int h) {
		assert w >= 0;
		assert h >= 0;
		width = w;
		height = h;
		tiles = new Tile[width][height];
		
		for (int x = 0; x < w; x++) {
			for (int y = 0; y < h; y++) {
				tiles[x][y] = new Tile(x, y); 
			}
		}
	}
	
	@Override
	public int getHeight() {
		return height;
	}

	@Override
	public int getWidth() {
		return width;
	}

	/**
	 * Put a sprite at a given position.
	 * @param s Sprite to be put on the board
	 * @param x x-coordinate
	 * @param y y-coordinate
	 */
	public void put(Sprite s, int x, int y) {
		assert withinBorders(x, y) : "PRE1: (x,y) on board.";
		assert s != null : "PRE2: Sprite not null";
		s.occupy(tileAt(x, y));
	}
	
	/**
	 * Verify that the given location falls within the
	 * borders of the board.
	 * @param x x-coordinate
	 * @param y y-coordinate
	 * @return True iff (x,y) falls within the board.
	 */
	public boolean withinBorders(int x, int y) {
		return
			x >= 0 && x < width 
			&& y >= 0 && y < height;
	}

	@Override
	public Sprite spriteAt(int x, int y) {
		assert withinBorders(x, y) : "PRE: (x,y) on board.";		
		return tileAt(x, y).topSprite();
	}
	
	@Override
	public SpriteType spriteTypeAt(int x, int y) {
		Sprite s = spriteAt(x, y);
		SpriteType result;
		if (s == null) {
			result = SpriteType.EMPTY;
		} else {
			result = s.getSpriteType();
		}
		return result;
	}

	/**
	 * @param x x-coordinate
	 * @param y y-coordinate
	 * @return The tile at the given (x,y) place
	 */
	public Tile tileAt(int x, int y) {
		return tiles[x][y];
	}
	
	/**
	 * Return a tile at position (x+dx, y+dy) from current
	 * tile at (x,y).
	 * @param t Reference tile / starting point.
	 * @param dx delta in x direction
	 * @param dy delta in y direction
	 * @return Tile at (x+dx, y+dy)
	 */
	public Tile tileAtOffset(Tile t, int dx, int dy) {
		assert t != null;
        int newx = tunnelledCoordinate(t.getX(), getWidth(), dx);
        int newy = tunnelledCoordinate(t.getY(), getHeight(), dy);
        assert withinBorders(newx, newy);
        Tile result = tileAt(newx, newy);
        assert result != null;
        return result;
	}
	
	private int tunnelledCoordinate(int current, int max, int delta) {
		assert current >= 0;
		assert current < max;

		// additional max needed if (current + delta) < 0.
		int result = ((current + delta) % max + max) % max;

		assert result >= 0;
		assert result < max;

		return result;
	}
	
	/**
	 * Obtain the tile in the given direction.
	 * @param t Starting position
	 * @param dir Direction
	 * @return Tile in direction from the given starting position.
	 */
	public Tile tileAtDirection(Tile t, Direction dir) {
		return tileAtOffset(t, dir.getDx(), dir.getDy());
	}
}
