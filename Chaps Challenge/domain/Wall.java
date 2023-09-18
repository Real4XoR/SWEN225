package src.main.java.nz.ac.vuw.ecs.swen225.gp22.domain;

/**
 * Wall class represents solid Tiles which Actors cannot stand on.
 * @author Takumi Grainger (300583202)
 *
 */
public class Wall implements Tile {
	/**
	 * Constructor for Wall objects
	 */
	public Wall() {}
	
	@Override
	public boolean isWall() { return true; }
}
