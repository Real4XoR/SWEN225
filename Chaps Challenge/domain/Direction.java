package src.main.java.nz.ac.vuw.ecs.swen225.gp22.domain;

/**
 * Direction enum represents the Directions an Actor can move in.
 * @author Takumi Grainger (300583202)
 *
 */
public enum Direction {
	NONE(0,0),
	UP(0,-1),
	DOWN(0,1),
	LEFT(-1,0),
	RIGHT(1,0);
	
	/**
	 * arrow is the Point to add to an Actor's location
	 */
	public final Point arrow;
	
	/**
	 * Get the Point to add to an Actor's location
	 * @return arrow
	 */
	public Point arrow() { return this.arrow; }
	Direction(int x, int y) { this.arrow = new Point(x, y);}
}