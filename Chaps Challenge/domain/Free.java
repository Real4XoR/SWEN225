package src.main.java.nz.ac.vuw.ecs.swen225.gp22.domain;

/**
 * Free class represents free Tiles which Actors can stand on.
 * @author Takumi Grainger (300583202)
 *
 */
public class Free implements Tile {
	/**
	 * Constructor for Free objects
	 */
	public Free() {}
	
	@Override
	public boolean isWall() { return false; }
}
