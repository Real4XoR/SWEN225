package swen.monopoly;

/**
 * Street.
 */
public class Street extends Property {
  private int numHouses;
  private int numHotels;
  private final int rent; // in $
  private ColourGroup colourGroup;

  /**
   *  Street.
   */
  public Street(String name, int price, int rent) {
    super(name, price);
    this.rent = rent;
    colourGroup = null;
  }

  /**
   * Get colour group to which this street belongs. Will return null if
   * setColourGroup not already called.
   */
  public ColourGroup getColourGroup() {
    return colourGroup;
  }

  public void setColourGroup(ColourGroup group) {
    colourGroup = group;
  }

  public int getRent() {
    return rent;
  }

  public int getHouses() {
    return numHouses;
  }

  public int getHotels() {
    return numHotels;
  }

  /**
   * Override default equals() method.
   */
  public boolean equal(Object o) {
    if (o instanceof Street) {
      return super.equals(o);
    }
    return false;
  }
}
