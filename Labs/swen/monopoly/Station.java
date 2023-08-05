package swen.monopoly;

/**
 * Station.
 */
public class Station extends Property {
  public Station(String name, int price) {
    super(name, price);
  }

  /**
   * Calcuate rent for this station. Should only be called if hasOwner() == true.
   */
  public int getRent() {
    // first, determine how many stations owned by player
    int nstations = 0;
    for (Property p : getOwner()) {
      if (p instanceof Station) {
        nstations = nstations + 1;
      }
    }
    // now compute rent, taking number owned into account
    return 50 * nstations;
  }

  /**
   * Override default equals() method.
   */
  public boolean equals(Object o) {
    if (o instanceof SpecialArea) {
      return super.equals(o);
    }
    return false;
  }
}
