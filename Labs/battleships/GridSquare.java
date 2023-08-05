package swen225.battleships;

/**
 * An object of this class represents a single square on the grid
 * The type field encodes the general content/status of the square.
 * A shipPart field encodes which part of a ship is located on the square, if the general content type is SHIP".
 *  
 */
public class GridSquare { 		
	private GridSquare.Type type;				// encode the general square content/status
	private GridSquare.ShipPart shipPart;	// encode which element of a ship is located on this square
	private BattleShip ship;							// reference to the ship whose element is located on this square (if any)
	
	public enum Type {
		EMPTY,
		MISS,
		HIT,
		SHIP
	};
	
	public enum ShipPart {
		VERTICAL_TOP_END,
		VERTICAL_BOTTOM_END,
		VERTICAL_MIDDLE,
		HORIZONTAL_LEFT_END,
		HORIZONTAL_RIGHT_END,		
		HORIZONTAL_MIDDLE		
	};

	// construct a square that does not hold a ship.
	public GridSquare(Type type) {
		this.type = type;
	}

	// construct a square that is occupied by a ship part.
	public GridSquare(Type type, ShipPart shipPart, BattleShip ship) {
		this.type = type;
		this.shipPart = shipPart;
		this.ship = ship;		
	}

	public void setType(Type type) {
		this.type = type;
	}
	
	public GridSquare.Type getType() {
		return type;
	}
	
	public GridSquare.ShipPart getShipPart() {
		return shipPart;
	}
	
	public BattleShip getShip(){
		return ship;
	}
}
