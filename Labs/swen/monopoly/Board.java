package swen.monopoly;

import java.util.ArrayList;

/**
 * Represents the Monopoly Board.
 *
 * @author djp
 */
public class Board {
  private final ArrayList<Location> places = new ArrayList<Location>();

  /**
   * Board.
   */
  public Board() {

    // Construct the locations
    Street oldKentRoad = new Street("Old Kent Road", 60, 2);
    Street whiteChapel = new Street("Whitechapel Road", 60, 4);

    Street angel = new Street("The Angel Islington", 100, 6);
    Street euston = new Street("Euston Road", 100, 6);
    Street pentonville = new Street("Pentonville", 120, 9);

    Street pallmall = new Street("Pall Mall", 140, 10);
    Street whitehall = new Street("Whitehall", 140, 10);
    Street northumberland = new Street("Northumberland Ave", 160, 12);

    Street bow = new Street("Bow Street", 180, 14);
    Street marlborough = new Street("Marlborough Street", 180, 14);
    Street vine = new Street("Vine Street", 200, 16);

    Street strand = new Street("The Strand", 220, 18);
    Street fleet = new Street("Fleet Street", 220, 18);
    Street trafalgar = new Street("Trafalgar Square", 240, 20);

    Street leceister = new Street("Leceister Square", 260, 22);
    Street conventry = new Street("Conventry Street", 260, 22);
    Street picadilly = new Street("Picadilly Circus", 280, 24);

    Street regent = new Street("Regent Street", 300, 26);
    Street oxford = new Street("Oxford Street", 300, 26);
    Street bond = new Street("Bond Street", 320, 28);

    Street park = new Street("Park Lane", 350, 35);
    Street mayfair = new Street("Mayfair", 400, 50);

    // Setup the colour groups
    new ColourGroup("Brown", oldKentRoad, whiteChapel);
    new ColourGroup("Cyan", angel, euston, pentonville);
    new ColourGroup("Pink", pallmall, whitehall, northumberland);
    new ColourGroup("Orange", bow, marlborough, vine);
    new ColourGroup("Red", strand, fleet, trafalgar);
    new ColourGroup("Yellow", leceister, conventry, picadilly);
    new ColourGroup("Green", regent, oxford, bond);
    new ColourGroup("Blue", park, mayfair);

    // Finally, construct the board itself
    places.add(new SpecialArea("Go"));
    places.add(oldKentRoad);
    places.add(new SpecialArea("Community Chest"));
    places.add(whiteChapel);
    places.add(new SpecialArea("Income Tax"));
    places.add(new Station("Kings Cross Station", 200));
    places.add(angel);
    places.add(new SpecialArea("Chance"));
    places.add(euston);
    places.add(pentonville);
    places.add(new SpecialArea("Jail"));
    places.add(pallmall);
    places.add(new Utility("Electric Company", 150));
    places.add(whitehall);
    places.add(northumberland);
    places.add(new Station("Marylebone Station", 200));
    places.add(bow);
    places.add(new SpecialArea("Community Chest"));
    places.add(marlborough);
    places.add(vine);
    places.add(new SpecialArea("Free Parking"));
    places.add(strand);
    places.add(new SpecialArea("Chance"));
    places.add(fleet);
    places.add(trafalgar);
    places.add(new Station("Fenchurch St. Station", 200));
    places.add(leceister);
    places.add(conventry);
    places.add(new Utility("Water Works", 150));
    places.add(picadilly);
    places.add(new SpecialArea("Goto Jail"));
    places.add(regent);
    places.add(oxford);
    places.add(new SpecialArea("Community Chest"));
    places.add(bond);
    places.add(new Station("Livepool St. Station", 200));
    places.add(new SpecialArea("Chance"));
    places.add(park);
    places.add(new SpecialArea("Super Tax"));
    places.add(mayfair);
  }

  /**
   * Return location correspond to "Go" on the board.
   */
  public Location getStartLocation() {
    return places.get(0);
  }

  /**
   * Find location which is a given number of steps along from location argument.
   *
   * @param location Location
   * @param steps int
   *
   * @return loc
   */
  public Location findLocation(Location location, int steps) {
    int idx = places.indexOf(location);
    idx = (idx + steps) % places.size();
    return places.get(idx);
  }

  /**
   * Find location with the given name.
   *
   * @param name String
   *
   * @return check
   */
  public Location findLocation(String name) {
    for (Location l : places) {
      if (l.getName().equals(name)) {
        return l;
      }
    }
    return null;
  }
}
