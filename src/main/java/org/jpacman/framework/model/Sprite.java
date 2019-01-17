package org.jpacman.framework.model;

import org.jpacman.framework.model.IBoardInspector.SpriteType;

/**
 * Any entity that occurs in the game.
 * 
 * @author Arie van Deursen, TU Delft, Dec 24, 2011
 */
public class Sprite {
	
	/**
	 * The tile the sprite is occupying -- null if 
	 * the sprite is disconnected.
	 */
	private Tile tile;
	
	/**
	 * Once the tile is set, the tile should indeed
	 * contain this sprite.
	 * @return true iff this invariant holds.
	 */
	protected boolean spriteInvariant() {
		return
			tile == null 
			||
			tile.containsSprite(this);
	}
	
	/**
	 * @return The tile this sprite is located on.
	 */
	public Tile getTile() {
		return tile;
	}
	
	/**
	 * Let the sprite occupy another tile.
	 * @param nextLocation The next tile to be occupied.
	 */
	public void occupy(Tile nextLocation) {
		assert spriteInvariant();
		assert nextLocation != null;
		
		if (tile != null) {
			tile.dropSprite(this);
		}
		nextLocation.addSprite(this);
		tile = nextLocation;
		
		assert this.equals(nextLocation.topSprite())
			: "Post: sprite at top of new tile.";
		assert spriteInvariant();
	}
	
	/**
	 * Move the sprite away from the tile it was occupying.
	 */
	public void deoccupy() {
		assert tile != null : "PRE: Must occupy a cell already.";
		assert spriteInvariant();
		tile.dropSprite(this);
		tile = null;
		assert spriteInvariant();
	}
	
	/**
	 * @return What sort of sprite we're looking at.
	 */
	public SpriteType getSpriteType() {
		return SpriteType.OTHER;
	}
	
	@Override 
	public String toString() {
		return getSpriteType().toString() + " occupying " + tile;
				
	}
}
