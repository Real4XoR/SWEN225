package src.main.java.nz.ac.vuw.ecs.swen225.gp22.domain;

/**
 * Actor interface provides the base methods for Actors on the board.
 * This includes Items (static) and the Player/Enemies (roaming).
 * @author Takumi Grainger (300583202)
 *
 */
public interface Actor {
	/**
	 * Ping this Actor so it can take an action within the Game.
	 * @param game the Game this Actor is in
	 */
	default void ping(Game game) {
		// do nothing by default
	}
	
	/**
	 * Get the location of this Actor.
	 * @return a Point which is the location of this Actor
	 */
	default Point location() {
		throw new Error("This Actor has not implemented location()");
	}
	
	/**
	 * Attempt to move this Actor in a given direction.
	 * @param direction the Direction to move in
	 * @param game the Game this Actor is in
	 */
	default void move(Direction direction, Game game) {
		throw new Error("This Actor can not move");
	}
	
	/**
	 * Check if this Actor is equal to another Actor.
	 * @param a the other Actor to check
	 * @return whether or not the Actors are equal
	 */
	boolean equals(Actor a);
}
