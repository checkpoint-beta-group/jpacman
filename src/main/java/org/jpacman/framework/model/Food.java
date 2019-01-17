package org.jpacman.framework.model;

import org.jpacman.framework.model.IBoardInspector.SpriteType;

public class Food extends Sprite {
	
	public static final int DEFAULT_POINTS = 10;
	
	private int points = DEFAULT_POINTS;

	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
	}
	
	/**
	 * @return That this sprite is a piece of Food.
	 */
	@Override
	public SpriteType getSpriteType() {
		return SpriteType.FOOD;
	}

}
