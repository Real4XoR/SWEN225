package swen.monopoly;

/**
 * Represents a game of monopoly.
 *
 * @author David J. Pearce
 */
public class GameOfMonopoly {
  private final Board board = new Board();

  /**
   * Get the current game board.
   *
   */
  public Board getBoard() {
    return board;
  }

  /**
   * Move a player from current location a number of steps around the board. Rent
   * should be deducted as necessary. The players balance may go negative as a
   * result of this.
   */
  public void movePlayer(Player player, int nsteps) {
    // First, determine the new location on the board, and move the player
    // to that location.
    Location loc = board.findLocation(player.getLocation(), nsteps);
    player.setLocation(loc);

    // Second, collect rent if applicable
    if (loc.hasOwner()) {
      Property prop = (Property) loc; // only properties can have owners
      if (!prop.isMortgaged() && prop.getOwner() != player) {
        // can only collect rent on unmortgaged properties
        // and, obviously, don't collect on our own properties!
        int rent = prop.getRent();
        player.deduct(rent);
        prop.getOwner().credit(rent);
      }
    }
  }

  /**
   * Allow player to try and purchase location they are on. Only allowed if
   * location is a property, property not already owned and player has enough
   * money!
   */
  public void buyProperty(Player player) throws InvalidMove {
    Location loc = player.getLocation();
    if (!(loc instanceof Property)) {
      throw new InvalidMove(player + " cannot buy location "
              + loc.getName() + ": it's not a property!");
    }
    Property prop = (Property) loc;
    if (prop.hasOwner()) {
      throw new InvalidMove(
              player + " cannot buy location "
                      + prop.getName() + ": it's owned by "
                      + prop.getOwner().getName() + "!");
    }
    if (prop.getPrice() > player.getBalance()) {
      throw new InvalidMove(player + " cannot buy location "
              + prop.getName() + ": player has insufficient funds!");
    }
    // OK, player can buy the property!
    player.buy(prop);
  }

  /**
   * Allow player to sell a property. Only allowed if property actually owned by
   * player and not already mortgaged.
   */
  public void sellProperty(Player player, Location loc) throws InvalidMove {
    if (!(loc instanceof Property)) {
      throw new InvalidMove(player + " cannot sell location "
              + loc.getName() + ": it's not a property!");
    }
    Property prop = (Property) loc;
    if (prop.getOwner() != player) {
      throw new InvalidMove(player + " cannot sell location "
              + loc.getName() + ": it's not theirs!");
    }
    if (prop.isMortgaged()) {
      throw new InvalidMove(player + " cannot sell location "
              + loc.getName() + ": it's mortgaged!");
    }

    // OK, we can sell the property!
    player.sell(prop);
  }

  /**
   * Allow player to mortgage a property. Only allowed if property actually owned
   * by player and not already mortgaged.
   */
  public void mortgageProperty(Player player, Location loc) throws InvalidMove {
    if (!(loc instanceof Property)) {
      throw new InvalidMove(player + " cannot mortgage location "
              + loc.getName() + ": it's not a property!");
    }
    Property prop = (Property) loc;
    if (prop.getOwner() != player) {
      throw new InvalidMove(player + " cannot mortgage location "
              + loc.getName() + ": it's not theirs!");
    }
    if (prop.isMortgaged()) {
      throw new InvalidMove(player + " cannot mortgage location "
              + loc.getName() + ": it's already mortgaged!");
    }

    // OK, we can mortgage the property!
    player.credit(prop.getPrice() / 2);
    prop.mortgage();
  }

  /**
   * Allow player to mortgage a property. Only allowed if property actually owned
   * by player, it is mortgaged and player has enough money to pay for it.
   */
  public void unmortgageProperty(Player player, Location loc) throws InvalidMove {
    if (!(loc instanceof Property)) {
      throw new InvalidMove(player + " cannot unmortgage location "
              + loc.getName() + ": it's not a property!");
    }
    Property prop = (Property) loc;
    if (prop.getOwner() != player) {
      throw new InvalidMove(player + " cannot unmortgage location "
              + loc.getName() + ": it's not theirs!");
    }
    if (!prop.isMortgaged()) {
      throw new InvalidMove(player + " cannot mortgage location "
              + loc.getName() + ": it's not mortgaged!");
    }
    int cost = (int) ((double) (prop.getPrice() / 2) * 1.1);
    if (cost > player.getBalance()) {
      throw new InvalidMove(player + " cannot mortgage location "
              + loc.getName() + ": insufficient funds!");
    }
    // OK, we can unmortgage the property!
    player.deduct(cost);
    prop.unmortgage();
  }

  /**
   * Indicates an attempt to make an invalid move.
   */
  public static class InvalidMove extends Exception {
    public InvalidMove(String msg) {
      super(msg);
    }
  }
}
