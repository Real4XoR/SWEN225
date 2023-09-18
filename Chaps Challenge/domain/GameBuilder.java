package src.main.java.nz.ac.vuw.ecs.swen225.gp22.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * GameBuilder class is responsible for building the Game object.
 * It handles the validation of fields before the object is built.
 * @author Takumi Grainger (300583202)
 *
 */
public class GameBuilder {
	private Player player;
	private List<Actor> actors = new ArrayList<>();
	private Tile[][] board;
	
	/**
	 * Constructor for GameBuilder objects
	 */
	public GameBuilder() {}
	
	/**
	 * Set the Player to use for this GameBuilder.
	 * @param player the Player to use
	 * @return the GameBuilder
	 */
	public GameBuilder player(Player player) {
		if (player == null) throw new IllegalArgumentException("Player cannot be null");
		this.player = player;
		return this;
	}
	
	/**
	 * Set the Actor list to use for this GameBuilder.
	 * null or empty lists are ignored.
	 * @param actors the Actor list to use
	 * @return the GameBuilder
	 */
	public GameBuilder actors(List<Actor> actors) {
		if (actors != null && !actors.isEmpty()) this.actors = actors;
		return this;
	}
	
	/**
	 * Set the board to use for this GameBuilder.
	 * The board cannot be null or contain null Tiles.
	 * @param board the Tile array to use
	 * @return the GameBuilder
	 */
	public GameBuilder board(Tile[][] board) {
		if (board == null) throw new IllegalArgumentException("Board cannot be null");
		
		// check for null Tiles
		for (int x=0; x<board.length; x++) {
			for (int y=0; y< board[0].length; y++) {
				if (board[x][y] == null) {
					throw new IllegalArgumentException("Board contains null at ("+x+","+y+")");
				}
			}
		}
		
		this.board = board;
		return this;
	}
	
	/**
	 * Build a Game object using this GameBuilder's fields.
	 * Fields are validated as well.
	 * @return a new Game with the GameBuilder's fields
	 */
	public Game build() {
		// validate the board first as the Player & Actor list validations depend on it
		// verify the Player is not null
		this.board(board).player(player);
		
		// now do extra validations
		validatePlayer();
		validateActors();
		
		// finally, build the Game object
		return new Game(player, actors, board);
	}
	
	// helper methods for validation
	
	/**
	 * Check if a given Point is within a wall on the board.
	 * @param location
	 * @return whether or not the Point is within a wall
	 */
	private boolean isWall(Point location) {
		if (location.x() < 0 || location.x() >= board[0].length || location.y() < 0 || location.y() >= board.length) {
			throw new IllegalArgumentException("OoB Point at "+location);
		}
		return board[location.x()][location.y()].isWall();
	}
	
	/**
	 * Validate a Player.
	 * The Player cannot be inside a wall.
	 */
	private void validatePlayer() {
		if (isWall(player.location())) throw new IllegalStateException("Player is in a wall at "+player.location());
	}
	
	/**
	 * Validate an Actor.
	 * The Actor cannot be inside a wall or be a Player.
	 * The Actor cannot share locations with any other Actor.
	 * If the Actor is a collected Item (excluding Treasures), it must be in the Inventory.
	 * @param actor the Actor to validate
	 */
	private void validateActor(Actor actor) {
		if (actor instanceof Player) throw new IllegalArgumentException("Actor is a Player");
		
		if (isWall(actor.location())) throw new IllegalStateException("Actor is in a wall at "+actor.location());
		
		// Check for shared locations
		if (actors.stream().anyMatch(a -> a != actor && a.location().equals(actor.location()))) {
			throw new IllegalStateException("Actors share a Point at "+actor.location());
		}
	}
	
	/**
	 * Validate the Actor list.
	 * The list cannot contain Players or contain Actors which are inside walls.
	 * The Actors cannot share locations with or each other.
	 */
	private void validateActors() {
		if (!actors.isEmpty()) actors.forEach(actor -> validateActor(actor));
	}
}
