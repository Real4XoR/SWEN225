package swen.monopoly;

import java.util.ArrayList;

/**
 * Color.
 *
 * @author djp
 */

public class ColourGroup {
  private final ArrayList<Street> streets = new ArrayList<Street>();
  private final String colour;

  /**
   * Create colour group made up of Streets supplied as arguments.
   */
  public ColourGroup(String colour, Street... streets) {
    for (Street street : streets) {
      this.streets.add(street);
      street.setColourGroup(this);
    }
    this.colour = colour;
  }

  public String getColour() {
    return colour;
  }
}
