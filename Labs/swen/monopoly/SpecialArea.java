package swen.monopoly;

/**
 * Special.
 */
public class SpecialArea extends Location {
  public SpecialArea(String n) {
    super(n);
  }

  public boolean hasOwner() {
    return false;
  }

  /**
   * Should not be called on Special Area.
   */
  public Player getOwner() {
    throw new RuntimeException("Should not call getOwner() on a SpeciaArea!");
  }

  /**
   * Should not be called on Special Area.
   */
  public int getRent() {
    throw new RuntimeException("Should not call getRent() on a SpeciaArea!");
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
