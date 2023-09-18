package src.main.java.nz.ac.vuw.ecs.swen225.gp22.domain;

/**
 * Tile interface represents Tiles on the board.
 * @author Takumi Grainger (300583202)
 *
 */
public interface Tile {
	/**
	 * Checks if this Tile is a wall.
	 * @return whether or not this Tile is a wall.
	 */
	default boolean isWall() {
		throw new Error("This Tile has not implemented isWall()");
	}
	
	/**
	 * Checks if this Tile is makes the Player win.
	 * @return whether or not this Tile makes the Player win.
	 */
	default boolean isWin() {
		return false;
	}
	
	/**
	 * Checks if this Tile can cause a game over.
	 * @return whether or not this Tile can cause a game over.
	 */
	default boolean isGameOver() {
		return false;
	}
	
	/**
	 * Checks if this Tile is a door.
	 * @return whether or not this Tile is a door.
	 */
	default boolean isDoor() {
		return false;
	}
	
	/**
	 * Unlocks this Tile.
	 */
	default void unlock() {
		// do nothing
	}
	
	/**
	 * Checks if this Tile has an info String.
	 * @return whether or not this Tile has an info String.
	 */
	default boolean hasInfo() {
		return false;
	}
	
	/**
	 * Gets the info String for this Tile.
	 * @return the info String for this Tile.
	 */
	default String getInfo() {
		return "";
	}
}
