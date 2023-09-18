package src.main.java.nz.ac.vuw.ecs.swen225.gp22.domain;

/**
 * Treasure class represents the Treasure the Player must collect to unlock ExitLocks.
 * @author Takumi Grainger (300583202)
 *
 */
public class Treasure extends Item {
	/**
	 * Constructor for Treasure objects.
	 * @param location the location of this Treasure.
	 */
	public Treasure(Point location) {
		super(location);
	}
	
	/**
	 * Constructor for Treasure objects.
	 * @param location the location of this Treasure.
	 * @param collected whether or not this Treasure was collected
	 */
	public Treasure(Point location, boolean collected) {
		super(location, collected);
	}
	
	@Override
	public void ping(Game game) {
		if (!collected() && game.player().location().equals(location())) {
			super.collected = true;
			game.collectTreasure(this);
		}
	}
	
	@Override
	public Point location() { return super.location; }
	
	@Override
	public boolean collected() { return super.collected; }

	@Override
	public boolean equals(Actor a) {
		if (this == a) return true;
		if (!(a instanceof Treasure)) return false;
		Treasure other = (Treasure) a;
		return this.location().equals(other.location()) && this.collected() == other.collected();
	}
}