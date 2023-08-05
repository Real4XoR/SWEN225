package swen225.battleships;

import java.util.*;

public class BattleShipsGame {
	// grid  dimensions
	private int width;
	private int height;
	
	private GridSquare[][] leftSquares;
	private GridSquare[][] rightSquares;
	
	// list of ships to keep track of liveliness of fleet
	private ArrayList<BattleShip> leftShips;
	private ArrayList<BattleShip> rightShips;	
	
	
	/**
     * Create an instance of the battle ships game where each side has width x
     * height squares.
     * 
     * @param width
     * @param height
     */
	public BattleShipsGame(int width, int height) {
		this.width = width;
		this.height = height;

		leftSquares = new GridSquare[width][height];
		rightSquares = new GridSquare[width][height];
		
		leftShips = new ArrayList<BattleShip>();
		rightShips = new ArrayList<BattleShip>();
	}
	
	/**
	 * Check whether or not this game is finished!
	 * @return
	 */
	public boolean isOver() {
		return didPlayerWin() || didComputerWin();
	}
	
	/**
	 * Check whether player has won or not.
	 * @return
	 */
	public boolean didPlayerWin() {
		return areAllShipsDestroyed(rightShips);
	}
	
	/**
	 * Check whether computer has won or not.
	 * @return
	 */
	public boolean didComputerWin() {
		return areAllShipsDestroyed(leftShips);
	}

	/**
	 * Check whether all ships have been destroyed
	 * @param ships
	 * @return fleet alive status
	 */
	public boolean areAllShipsDestroyed(List<BattleShip> ships) {
		for (BattleShip ship : ships) 
			if(!ship.isDestroyed()) 
				return false;
		
		return true;
	}
	
	/**
     * Bomb a square at a particular location.
     * 
     * @param xpos
     * @param ypos
     * @param leftSide
     *            		Indicate whether its on the left (i.e. player's) side, or on
     *            		the right (i.e. computer's) side.
     */
	public String bombSquare(int xpos, int ypos, boolean leftSide) {
		GridSquare[][] squares = leftSide ? leftSquares : rightSquares;
		GridSquare square = squares[xpos][ypos];
		
		if(square.getType()==GridSquare.Type.EMPTY) {   // hit water?
			square.setType(GridSquare.Type.MISS);            // record as a miss.
		} else if(square.getType() == GridSquare.Type.SHIP) {
			square.setType(GridSquare.Type.HIT);              // record ship element being hit
			square.getShip().sustainHit();                            // add to the ship's damage
			
			// if that was the last ship element, report the ship's name
			if (square.getShip().isDestroyed())
				return square.getShip().getName();
		}
		
		return null; // no ship has been destroyed in this turn.
	}
	
	/**
     * Get a grid square at a given position on the left side of the board.
     * 
     * @param xpos
     * @param ypos
     * @return
     */
	public GridSquare getLeftSquare(int xpos, int ypos) {
		return leftSquares[xpos][ypos];
	}

	/**
     * Get a grid square at a given position on the right side of the board.
     * 
     * @param xpos
     * @param ypos
     * @return
     */
	public GridSquare getRightSquare(int xpos, int ypos) {
		return rightSquares[xpos][ypos];
	}
	
	/**
	 * Get width of one side of the board.
	 * @return
	 */
	public int getWidth() { return width; }
		
	/**
	 * Get height of one side of the board.
	 * @return
	 */
	public int getHeight() { return height; }
	

	/**
	 * Construct a randomly generated board
	 * @param game
	 */
	public void createRandomBoard(Random random) {
		// To start with, initialise all squares to be empty
		for(int y=0; y<height; y++) 
			for(int x=0; x<width; x++) {
				leftSquares[x][y] = new GridSquare(GridSquare.Type.EMPTY);
				rightSquares[x][y] = new GridSquare(GridSquare.Type.EMPTY);
			}

		// Now add ships to left side
		addRandomShip(new BattleShip("Submarine 1",3),true,random);
		addRandomShip(new BattleShip("Submarine 2",3),true,random);
		addRandomShip(new BattleShip("Submarine 3",3),true,random);
		addRandomShip(new BattleShip("Corvette",4),true,random);
		addRandomShip(new BattleShip("Carrier",5),true,random);
		
		// Now add ships to right side
		addRandomShip(new BattleShip("Submarine 1",3),false,random);
		addRandomShip(new BattleShip("Submarine 2",3),false,random);
		addRandomShip(new BattleShip("Submarine 3",3),false,random);
		addRandomShip(new BattleShip("Corvette",4),false,random);
		addRandomShip(new BattleShip("Carrier",5),false,random);		
	}
	
	/**
     * Add a random ship of a given length to one side of the board. The new
     * ship must not  intersect any existing ships.
     * 
     * @param length
     * @param squares
     * @param random
     */
	private void addRandomShip(BattleShip ship, boolean leftSide, Random random) {
		GridSquare[][] squares = leftSide ? leftSquares : rightSquares;
		
		if(leftSide) {
			leftShips.add(ship);
		} else {
			rightShips.add(ship);
		}
		
		boolean vertical = random.nextBoolean();
		int xStep = vertical ? 0 : 1;
		int yStep = vertical ? 1 : 0;
		
		GridSquare.ShipPart shipMinEnd =  vertical ? 	GridSquare.ShipPart.VERTICAL_TOP_END : 
			                                                         		GridSquare.ShipPart.HORIZONTAL_LEFT_END;
		
		GridSquare.ShipPart shipMiddle =  vertical ? 	GridSquare.ShipPart.VERTICAL_MIDDLE : 
            																GridSquare.ShipPart.HORIZONTAL_MIDDLE;
		
		GridSquare.ShipPart shipMaxEnd =  vertical ?	GridSquare.ShipPart.VERTICAL_BOTTOM_END : 
            																GridSquare.ShipPart.HORIZONTAL_RIGHT_END;

		int length = ship.getLength();

		// keep trying to add ship until an available area has been found
		while(true) {
			int xpos = random.nextInt(width - xStep*length);
			int ypos = random.nextInt(height - yStep*length);
			
			// check whether this ship would intersect any existing ones
			if(isSpaceClear(squares, xpos, ypos, xStep, yStep, length)) {
				
				// create first element of ship
				squares[xpos][ypos] = new GridSquare(GridSquare.Type.SHIP, shipMinEnd, ship);

				// create the mid-section of the ship
				int i=1;
				for (; i<length-1; i++) 
					squares[xpos+i*xStep][ypos+i*yStep] = new GridSquare(GridSquare.Type.SHIP, shipMiddle, ship);
					
				// create last element of the ship
				squares[xpos+i*xStep][ypos+i*yStep] = new GridSquare(GridSquare.Type.SHIP, shipMaxEnd, ship);
					
				return;
			}
		}
	}
	

	public boolean isSpaceClear(GridSquare[][] squares, int xpos, int ypos, int xStep, int yStep, int length)	{
		for(int i=0; i<length; i++) 
			if(squares[xpos+i*xStep][ypos+i*yStep].getType() != GridSquare.Type.EMPTY)
				return false;
	
			return true;
	}
}
