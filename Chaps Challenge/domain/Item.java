package src.main.java.nz.ac.vuw.ecs.swen225.gp22.domain;

/**
 * The Item class represents items which can be picked up, such as Keys and Treasures
 * @author Takumi Grainger (300583202)
 *
 */
public abstract class Item implements Actor {
	protected Point location;
	protected boolean collected = false;
	
	/**
	 * Constructor for Item objects.
	 * @param location the location of this Item
	 */
	public Item(Point location) {
		this.location = location;
		this.collected = false;
	}
	
	/**
	 * Constructor for Item objects.
	 * @param location the location of this Item
	 * @param collected whether or not this Item was collected
	 */
	public Item(Point location, boolean collected) {
		this.location = location;
		this.collected = collected;
	}
	
	@Override
	public void ping(Game game) {
		if (!collected && game.player().location().equals(location)) {
			collected = true;
			game.collectItem(this);
		}
	}
	
	@Override
	public Point location() { return location; }
	
	/**
	 * Check if this Item was collected.
	 * @return collected whether or not this Item has been collected
	 */
	public boolean collected() { return collected; }
	
	@Override
	public boolean equals(Actor a) {
		if (this == a) return true;
		if (!(a instanceof Item)) return false;
		Item other = (Item) a;
		return this.location.equals(other.location) && this.collected == other.collected;
	}
}