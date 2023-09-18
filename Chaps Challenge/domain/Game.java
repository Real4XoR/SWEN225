package src.main.java.nz.ac.vuw.ecs.swen225.gp22.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Game class represents the model for Chap's Challenge.
 * Games should be built with the GameBuilder to ensure the fields are valid.
 * @author Takumi Grainger (300583202)
 *
 */
public class Game {
	private Player player;
	private List<Actor> actors;
	private List<Item> inventory;
	private Tile[][] board;
	private long treasures;
	private long treasuresCollected = 0L;
	private long treasuresRemaining;
	private boolean changed = false;
	private boolean playerDead = false;
	
	/**
	 * Constructor for Game objects.
	 * @param player the Player to use
	 * @param actors the List of Actors to use
	 * @param board the 2D array of Tiles to use
	 */
	public Game(Player player, List<Actor> actors, Tile[][] board) {
		this.board = board;
		this.player = player;
		this.actors = actors;
		createInventory();
		countTreasures();
		makeChange();
	}
	
	/**
	 * Get the Player of this Game.
	 * @return the Player of this Game
	 */
	public Player player() { return player; }
	
	/**
	 * Get an unmodifiable version of the Actor list.
	 * @return an unmodifiable version of the Actor list
	 */
	public List<Actor> actors() {
		return Collections.unmodifiableList(actors);
	}
	
	/**
	 * Get the board of this Game.
	 * @return the array of Tiles which make up this game's board
	 */
	public Tile[][] board() { return board; }
	
	/**
	 * Get an unmodifiable version of the Player's inventory.
	 * @return an unmodifiable list of the Player's items
	 */
	public List<Item> inventory() {
		return Collections.unmodifiableList(inventory);
	}
	
	/**
	 * Get the RoamingActors of this Game.
	 * This is used by App to allow Fuzz to avoid them.
	 * @return A list of RoamingActors in this Game
	 */
	public List<RoamingActor> getRoamingActorList() {
		return actors.stream().filter(RoamingActor.class::isInstance).map(RoamingActor.class::cast).toList();
	}
	
	/**
	 * Get the Items of this Game, including Treasures.
	 * This is used by App to allow Fuzz to prioritize finding them.
	 * @return A list of Items in this Game
	 */
	public List<Item> getItemList() {
		return actors.stream().filter(Item.class::isInstance).map(Item.class::cast).toList();
	}
	
	/**
	 * Get the Keys of this Game.
	 * This is used by App to allow Fuzz to prioritize finding them.
	 * @return A list of Keys in this Game
	 */
	public List<Key> getKeyList() {
		return getItemList().stream().filter(Key.class::isInstance).map(Key.class::cast).toList();
	}
	
	/**
	 * Get the Treasures of this Game.
	 * This is used by App to allow Fuzz to prioritize finding them.
	 * @return A list of Treasures in this Game
	 */
	public List<Treasure> getTreasureList() {
		return getItemList().stream().filter(Treasure.class::isInstance).map(Treasure.class::cast).toList();
	}
	
	/**
	 * Create the inventory for this Game.
	 */
	private void createInventory() {
		inventory = new ArrayList<>();
		getItemList().stream().filter(i -> !(i instanceof Treasure) && i.collected()).forEach(inventory::add);
	}
	
	/**
	 * Count the number of Treasures in this Game and update the variables accordingly.
	 */
	private void countTreasures() {
		treasures = getTreasureList().size();
		treasuresCollected = getTreasureList().stream().filter(Treasure::collected).count();
		treasuresRemaining = treasures - treasuresCollected;
		unlockAll();
	}
	
	/**
	 * Get the total number of Treasures in this Game.
	 * @return treasures the total number of Treasures in this Game
	 */
	public long treasures() { return treasures; }
	
	/**
	 * Get the number of Treasures collected.
	 * @return treasuresCollected
	 */
	public long treasuresCollected() { return treasuresCollected; }
	
	/**
	 * Get the number of Treasures remaining.
	 * @return treasuresRemaining
	 */
	public long treasuresRemaining() { return treasuresRemaining; }
	
	/**
	 * Attempt to collect an Item and add it to the inventory.
	 * @param item the Item to collect
	 */
	public void collectItem(Item item) {
		if (!item.collected()) {
			throw new IllegalArgumentException("Item has not been collected yet");
		}
		if (inventory.contains(item)) {
			throw new IllegalStateException("Item is already in inventory");
		}
		inventory.add(item);
		assert inventory.contains(item);
		makeChange();
	}
	
	/**
	 * Attempt to collect a Treasure.
	 * The number of Treasures collected is incremented;
	 * The number of Treasures remaining is decremented;
	 * And all of the Tiles are unlocked once there are no more Treasures remaining.
	 * @param treasure the Treasure to collect
	 */
	public void collectTreasure(Treasure treasure) {
		// precondition: treasure must be collected
		if (!treasure.collected()) {
			throw new IllegalArgumentException("Treasure has not been collected yet");
		}
		
		// precondition: treasuresRemaining is positive
		if (treasuresRemaining <= 0) {
			if (treasuresRemaining == 0) {
				throw new IllegalStateException("No more treasures remain");
			}
			throw new IllegalStateException("Treasures remaining is negative??");
		}
		
		// increment treasuresCollected
		long tmp = treasuresCollected;
		treasuresCollected++;
		assert treasuresCollected==tmp+1;
		
		// decrement treasuresRemaining
		tmp = treasuresRemaining;
		treasuresRemaining--;
		assert treasuresRemaining==tmp-1;
		
		// assert that they still add up to the total
		assert treasuresCollected+treasuresRemaining==treasures;
		
		unlockAll();
		makeChange();
	}
	
	/**
	 * Unlocks all the Tiles on the board once all Treasures have been collected.
	 * Only Tiles which overrode unlock() will be updated.
	 */
	private void unlockAll() {
		if (treasuresRemaining > 0) return; 
		for (int x=0; x<getWidth(); x++) {
			for (int y=0; y<getHeight(); y++) {
				getTile(new Point(x,y)).unlock();
			}
		}
	}
	
	/**
	 * Ping the Player and all other Actors in this Game.
	 */
	public void ping() {
		changed = false;
		player.ping(this);
		for (Actor actor : actors) { actor.ping(this); }
	}
	
	/**
	 * Get the width of this Game's board.
	 * @return the width of the board
	 */
	public int getWidth() { return board.length; }
	
	/**
	 * Get the height of this Game's board.
	 * @return the height of the board
	 */
	public int getHeight() { return board[0].length; }
	
	/**
	 * Get the Tile from a given Point on the board.
	 * @param p the Point to get the Tile from
	 * @return the Tile at this Point
	 */
	public Tile getTile(Point p) {
		if (p.x() < 0 || p.x() >= getWidth() || p.y() < 0 || p.y() >= getHeight()) {
			return new Wall(); // OoB Tiles are counted as Walls
		}
		return board[p.x()][p.y()];
	}
	
	/**
	 * Check if the Tile at a given Point is a wall.
	 * @param p the Point to check
	 * @return whether or not the Tile at this Point is a wall
	 */
	public boolean isWall(Point p) { return getTile(p).isWall(); }
	
	/**
	 * Check if the Player won this Game.
	 * @return whether or not the Player has won this Game
	 */
	public boolean isWin() { return getTile(player.location()).isWin(); }
	
	/**
	 * Check if the Player has lost this Game.
	 * @return whether or not the Player has lost this Game.
	 */
	public boolean isGameOver() { return playerDead || getTile(player.location()).isGameOver(); }
	
	/**
	 * Kill the Player.
	 */
	public void killPlayer() {
		playerDead = true;
		makeChange();
	}
	
	/**
	 * Respawn the Player.
	 */
	public void respawnPlayer() {
		playerDead = false;
		makeChange();
	}
	
	/**
	 * Check if the Player is dead.
	 * @return playerDead
	 */
	public boolean playerDead() { return playerDead; }
	
	/**
	 * Check if the Player is on an Info Field.
	 * @return whether or not the Player is on an Info Field
	 */
	public boolean hasInfo() { return getTile(player.location()).hasInfo(); }
	
	/**
	 * Get the message from the Info Field the Player is on.
	 * @return the message in the Info Field
	 */
	public String getInfo() { return getTile(player.location()).getInfo(); }
	
	/**
	 * Check if the Game's state has changed.
	 * This is so other modules can save states less often.
	 * @return changed
	 */
	public boolean changed() { return changed; }
	
	/**
	 * Let the Game know that its state has changed.
	 */
	public void makeChange() { changed = true; }
}
