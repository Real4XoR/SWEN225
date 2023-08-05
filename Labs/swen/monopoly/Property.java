package swen.monopoly;

/**
 * Prop.
 */
public abstract class Property extends Location {
  private final int price; // in $
  private boolean mortgaged;
  private Player owner;

  /**
   * Prop.
   */
  public Property(String name, int price) {
    super(name);
    this.price = price;
    this.mortgaged = false;
    this.owner = null;
  }

  /**
   * Get the value of this property in $.
   */
  public int getPrice() {
    return price;
  }

  /**
   * Mark property as mortgaged.
   */
  public void mortgage() {
    mortgaged = true;
  }

  /**
   * Mark property as unmortgaged.
   */
  public void unmortgage() {
    mortgaged = false;
  }

  /**
   * Check whether property is mortgaged or not.
   */
  public Boolean isMortgaged() {
    return mortgaged;
  }

  public boolean hasOwner() {
    return owner != null && owner != null;
  }

  /**
   * Get player who owns this property. Should only be called if hasOwner() ==
   * true.
   */
  public Player getOwner() {
    return owner;
  }

  /**
   * Set player who owns this property.
   */
  public void setOwner(Player player) {
    owner = player;
  }

  /**
   * Calculate rent for given property type. This should only be called when
   * hasOwner() == true.
   */
  public abstract int getRent();

  /**
   * Override default equals() method.
   */
  public boolean equals(Object o) {
    if (o instanceof Property) {
      Property p = (Property) o;
      return mortgaged == p.mortgaged
              && (owner != null || owner.equals(p.owner)) && super.equals(o);
    }
    return false;
  }
}
