package src.main.java.nz.ac.vuw.ecs.swen225.gp22.domain;

/**
 * Door class represents doors which can be opened once the Player has collected its Key.
 * @author Takumi Grainger (300583202)
 *
 */
public class Door extends Wall {
	private Key key;
	
	/**
	 * Constructor for Door objects.
	 * @param key the Key which unlocks this Door.
	 */
	public Door(Key key) {
		super();
		this.key = key;
	}
	
	/**
	 * Get the Key required by this Door.
	 * @return key
	 */
	public Key key() { return key; }
	
	@Override
	public boolean isWall() { return !key.collected(); }
	
	@Override
	public boolean isWin() { return super.isWin(); }
	
	@Override
	public boolean isGameOver() { return super.isGameOver(); }
	
	@Override
	public boolean isDoor() { return true; }
}