package swen.monopoly;

/**
 * Util.
 */
public class Utility extends Property {
  public Utility(String name, int price) {
    super(name, price);
  }

  /**
   * Calculate rent for this Utility. Should only be called if hasOwner() == true.
   */
  public int getRent() {
    // first, determine how many utilities owned by player
    int nutils = 0;
    for (Property p : getOwner()) {
      if (p instanceof Utility) {
        nutils++;
      }
    }
    // now compute rent, taking number owned into account
    return 75 * nutils;
  }

}
