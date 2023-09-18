package src.main.java.nz.ac.vuw.ecs.swen225.gp22.domain;

/**
 * ExitLock class represents Tiles which are unlocked once the Player has collected all the Treasures.
 * @author Takumi Grainger (300583202)
 *
 */
public class ExitLock extends Wall {
	private boolean locked = true;
	
	/**
	 * Constructor for ExitLock objects.
	 */
	public ExitLock() { super(); }
	
	@Override
	public boolean isWall() { return locked; }
	
	@Override
	public boolean isWin() { return super.isWin(); }
	
	@Override
	public boolean isGameOver() { return super.isGameOver(); }
	
	@Override
	public boolean isDoor() { return true; }
	
	@Override
	public void unlock() {
		if (!locked) throw new IllegalStateException("Exit Lock is already unlocked");
		locked = false;
		assert !locked;
	}
}