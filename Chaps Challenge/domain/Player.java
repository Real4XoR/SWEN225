package src.main.java.nz.ac.vuw.ecs.swen225.gp22.domain;

/**
 * Player class represents the Player, Chap.
 * It can be moved by the user.
 * @author Takumi Grainger (300583202)
 *
 */
public class Player extends RoamingActor {
	// For checking changes
	private Point oldLocation;
	private Direction oldFacingDirection = Direction.DOWN;
	
	/**
	 * Constructor for Player objects.
	 * @param location the location of this Player
	 */
	public Player(Point location) {
		super(location);
		oldLocation = new Point(location.x(), location.y());
	}
	
	@Override
	public void ping(Game game) {
		if (!oldLocation.equals(location()) || oldFacingDirection != facingDirection()) {
			game.makeChange();
		}
		updateOldFields();
	}
	
	@Override
	public Point location() { return super.location; }
	
	@Override
	public Direction facingDirection() { return super.facingDirection; }
	
	private void updateOldFields() {
		oldLocation = location();
		oldFacingDirection = facingDirection();
	}
	
	@Override
	public void move(Direction direction, Game game) {
		updateOldFields();
		super.move(direction, game);
	}
	
	@Override
	public boolean equals(Actor a) {
		if (this == a) return true;
		if (!(a instanceof Player)) return false;
		Player other = (Player) a;
		return this.location.equals(other.location) && this.facingDirection.equals(other.facingDirection);
	}
}
