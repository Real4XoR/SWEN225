package src.main.java.nz.ac.vuw.ecs.swen225.gp22.domain;

/**
 * Point record represents 
 * @author Takumi Grainger (300583202)
 *
 * @param x the x coordinate
 * @param y the y coordinate
 */
public record Point(int x, int y) {
	/**
	 * Add the coordinates of another Point to this Point.
	 * @param p the Point to add
	 * @return a new Point which is the result of the addition
	 */
	public Point add(Point p) { return new Point(x()+p.x(), y()+p.y()); }
	
	/**
	 * Check if the coordinates of this Point are equal to another Point.
	 * @param p the Point to compare to
	 * @return whether or not these Points have equal coordinates
	 */
	public boolean equals(Point p) { return x() == p.x() && y() == p.y(); }
	
	@Override
	public String toString() { return "("+x()+","+y()+")"; }
}
