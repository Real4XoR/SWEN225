package src.main.java.nz.ac.vuw.ecs.swen225.gp22.domain;

/**
 * Exit class represents the exit for the level. 
 * The Player will win the level once they step on it.
 * @author Takumi Grainger (300583202)
 *
 */
public class Exit implements Tile {
	/**
	 * Constructor for Exit objects
	 */
	public Exit() {}
	
	@Override
	public boolean isWall() { return false; }
	
	@Override
	public boolean isWin() { return true; }
}
