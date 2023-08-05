package swen225.battleships;

/**
 * This class represents battle ships.
 * @author djp
 *
 */
public class BattleShip {
	private String name;
	private int length;
	private int nhits; // number of hits so far
	
	/**
	 * Construct new battle ship.
	 * @param name Name of battle ship
	 * @param length Length of battle ship (in squares)
	 */
	public BattleShip(String name, int length) {
		this.name = name;
		this.length = length;
		this.nhits = 0; // no hits so far
	}
	
	/**
	 * Record that this battle ship has been hit.	 
	 */
	public void sustainHit() { nhits++; }
	
	/**
	 * Check whether or not this battle ship has been destroyed 
	 */
	public boolean isDestroyed() { 
		return nhits == length; 
	}
	
	/**
	 * return  the length of this ship
	 * @return
	 */
	public int getLength() { return length; }
	
	public String getName() {
		return name;
	}
}
