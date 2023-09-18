package src.main.java.nz.ac.vuw.ecs.swen225.gp22.domain;

/**
 * RoamingActor class represents Actors which can move around the board.
 * An example of this is the Player.
 * @author Takumi Grainger (300583202)
 *
 */
public abstract class RoamingActor implements Actor {
	protected Point location;
	protected Direction facingDirection = Direction.DOWN; // down by default
	
	/**
	 * Constructor for RoamingActor objects.
	 * @param location the location of this RoamingActor
	 */
	public RoamingActor(Point location) {
		this.location = location;
	}
	
	@Override
	public Point location() { return location; }
	
	/**
	 * Get the facing direction of this RoamingActor.
	 * @return facingDirection the facing direction of this RoamingActor
	 */
	public Direction facingDirection() { return facingDirection; }
	
	@Override
	public void move(Direction direction, Game game) {
		facingDirection = direction; // Actor must face the direction regardless of movement
		Point nextLocation = location.add(direction.arrow());
		if (!game.isWall(nextLocation)) { location = nextLocation; } // only allowed to move on free tiles
		// TODO: add logic for Actor collisions
		game.makeChange();
	}
	
	@Override
	public boolean equals(Actor a) {
		if (this == a) return true;
		if (!(a instanceof RoamingActor)) return false;
		RoamingActor other = (RoamingActor) a;
		return this.location.equals(other.location) && this.facingDirection.equals(other.facingDirection);
	}
}
