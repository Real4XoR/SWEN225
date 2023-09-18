package src.main.java.nz.ac.vuw.ecs.swen225.gp22.domain;

/**
 * Info class represents Tiles which display a message when the Player steps onto them.
 * @author Takumi Grainger (300583202)
 *
 */
public class Info implements Tile {
	private String info;
	
	/**
	 * Constructor for Info objects.
	 * @param info the message to be displayed by the Info Tile
	 */
	public Info(String info) { this.info = info; }
	
	@Override
	public boolean isWall() { return false; }
	
	@Override
	public boolean hasInfo() { return true; }
	
	@Override
	public String getInfo() { return info; }
}