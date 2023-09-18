package src.main.java.nz.ac.vuw.ecs.swen225.gp22.domain;

/**
 * Key class represents keys which can open Doors once collected by the Player.
 * @author Takumi Grainger (300583202)
 *
 */
public class Key extends Item {
	private Colour colour;
	
	/**
	 * Constructor for Key objects.
	 * @param location the location of the Key
	 * @param colour the Colour of this Key
	 */
	public Key(Point location, Colour colour) {
		super(location);
		this.colour = colour;
	}
	
	/**
	 * Constructor for Key objects.
	 * @param location the location of the Key
	 * @param colour the Colour of this Key
	 * @param collected wether or not this Key was collected
	 */
	public Key(Point location, Colour colour, boolean collected) {
		super(location, collected);
		this.colour = colour;
	}
	
	/**
	 * Get the Colour of this Key.
	 * @return colour the Colour of this Key
	 */
	public Colour colour() { return colour; }
	
	@Override
	public void ping(Game game) {
		if (!collected() && game.player().location().equals(location())) {
			super.collected = true;
			game.collectItem(this);
		}
	}
	
	@Override
	public Point location() { return super.location; }
	
	@Override
	public boolean collected() { return super.collected; }

	@Override
	public boolean equals(Actor a) {
		if (this == a) return true;
		if (!(a instanceof Key)) return false;
		Key other = (Key) a;
		return this.location().equals(other.location()) && this.colour.equals(other.colour)
				&& this.collected() == other.collected();
	}
}
