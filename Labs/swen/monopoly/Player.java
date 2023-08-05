package swen.monopoly;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Player.
 * <p></p>
 * This class repesents a player in the game. In particular, it records
 * what properties are currently owned by that player.
 */
public class Player implements Iterable<Property> {
  private final ArrayList<Property> portfolio;
  private Location location;
  private int cash;
  private final String token;
  private final String name;

  /**
   * Player.
   */
  public Player(String name, String token, int cash, Location location) {
    this.cash = cash;
    this.token = token;
    this.location = location;
    this.name = name;
    portfolio = new ArrayList<Property>();
  }

  public String getName() {
    return name;
  }

  /**
   * Get current location of player.
   */
  public Location getLocation() {
    return location;
  }

  /**
   * Set current location of player.
   */
  public void setLocation(Location l) {
    location = l;
  }

  public int getBalance() {
    return cash;
  }

  public String getToken() {
    return token;
  }

  /**
   * Deduct amount in $ from player.
   */
  public void deduct(int amount) {
    cash -= amount;
  }

  /**
   * Credit amount in $ to player.
   */
  public void credit(int amount) {
    cash += amount;
  }

  /**
   * Buy property. This will deduct cash from players balance and add property to
   * list of those owned by this player. If player has insufficient funds, balance
   * will go negative. Assumes property not already owned by anyone!
   */
  public void buy(Property p) {
    if (p.hasOwner()) {
      throw new IllegalArgumentException("cannot buy property!");
    }

    cash -= p.getPrice();
    portfolio.add(p);
    p.setOwner(this);
  }

  /**
   * Sell property. This will credit cash from players balance and remove property
   * from list of those owned by this player. Assumes property is owned by player!
   */
  public void sell(Property p) {
    if (p.getOwner() != this) {
      throw new IllegalArgumentException("cannot sell property!");
    }

    cash += p.getPrice();
    portfolio.remove(p);
    p.setOwner(null);
  }

  /**
   * Iterate methods owned by player.
   */
  public Iterator<Property> iterator() {
    return portfolio.iterator();
  }
}
