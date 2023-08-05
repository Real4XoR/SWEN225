package swen225.battleships;

import java.util.*;

/**
 * This class implements a text interface for the battle ships game
 *
 */
public class TextInterface {
	private static Random random = new Random();
	
	/**
	 * Convenience method.
	 * Generate a random integer between 0 and n
	 */
	public static int randomInteger(int n) {
		return random.nextInt(n);
	}
	
	/**
	 * Print a simple welcome message for when the game starts.
	 */
	private static void printWelcomeMessage() {		
		System.out.println("Welcome to the game of BattleShips!");
		System.out.println("(c) School of Engineering and Computer Science.");
	}
	
	/**
	 * Input an integer from the scanner. If an invalid input is given, then
	 * keep trying until a valid input is given.
	 * 
	 * @param msg The message to print before reading the input
	 * @param in The scanner from which to read the input
	 * @return
	 */
	private static int inputInteger(String msg, Scanner scanner) {
		while(true) {
			System.out.println(msg);
			try {
				int i = Integer.parseInt(scanner.nextLine());
				return i;
			} catch(NumberFormatException e) {
				System.out.println("Invalid integer.");
			}
		}
	}
	
	
	/**
     * Print a grid square to the standard output. If the visible flag is set,
     * then ShipSquares are visible.
     * 
     * @param square
     * @param visible
     */
	public static void printSquare(GridSquare square, boolean visible) {
		if(square.getType() == GridSquare.Type.EMPTY) {
			System.out.print("_");
		} else if(square.getType() == GridSquare.Type.MISS) {
			System.out.print("O");
		} else if(square.getType() == GridSquare.Type.HIT) {
			System.out.print("X");
		} else if(square.getType() == GridSquare.Type.SHIP) {	
			
			if(!visible) {
				System.out.print("_");
				return;
			}

			if(square.getShipPart() == GridSquare.ShipPart.HORIZONTAL_LEFT_END) {
				System.out.print("<");
			} else if(square.getShipPart() == GridSquare.ShipPart.HORIZONTAL_RIGHT_END) {
				System.out.print(">");
			} else if(square.getShipPart() == GridSquare.ShipPart.HORIZONTAL_MIDDLE) {
				System.out.print("#");	
			} else if(square.getShipPart() == GridSquare.ShipPart.VERTICAL_TOP_END) {
				System.out.print("^");
			} else if(square.getShipPart() == GridSquare.ShipPart.VERTICAL_BOTTOM_END) {
				System.out.print("v");
			} else if(square.getShipPart() == GridSquare.ShipPart.VERTICAL_MIDDLE) {
				System.out.print("#");
			} else {
				System.out.print("?");
			}
		}
	}
	
	/**
     * Print the current state of the game board to the standard output.
     * 
     * @param game
     */
	public static void printBoard(BattleShipsGame game) {
		for(int y = 0; y < game.getHeight(); y++) {
			
			if(y < 10) 
				System.out.print("0"); 
			
			System.out.print(y);
			
			for(int x = 0; x < game.getWidth(); x++) {
				printSquare(game.getLeftSquare(x,y),true); 
			}
			
			System.out.print("||");
			
			for(int x = 0; x < game.getWidth(); x++) {
				printSquare(game.getRightSquare(x,y),false);
			}		
			
			if(y < 10) 
				System.out.print("0"); 
			
			System.out.println(y);
		}
		
		
		// print X coordinates 
		
		System.out.print("__");
		
		for(int x = 0; x < game.getWidth();x++) 
			System.out.print(x%10);
				
		System.out.print("||");
		
		for(int x = 0; x < game.getWidth();x++) 
			System.out.print(x%10);
				
		System.out.println("\n");	
	}
	
	/**
	 * Play the game!
	 * 
	 * @param game
	 */
	public static void playGame(BattleShipsGame game) {
		Scanner scanner = new Scanner(System.in);
		game.createRandomBoard(random);
		
		while (!game.isOver()) {		
			printBoard(game);
			int xpos;
			int ypos;
			
			while (true) {
				xpos = inputInteger("Enter x coordinate for target:", scanner);
				ypos = inputInteger("Enter y coordinate for target:", scanner);
				
				if (xpos < 0 || ypos < 0 || xpos >= game.getWidth() || ypos >= game.getHeight()) {
					System.out.println("\n *** INVALID TARGET --- TRY AGAIN ***\n");
					continue;
				}		
				
				break;
			}
			
			String shipName = game.bombSquare(xpos, ypos, false);
			
			if (shipName != null)
				System.out.println("The opponent's "+shipName + " has been destroyed!");
			
			// computer get's their turn
			int c_xpos = randomInteger(game.getWidth());
			int c_ypos = randomInteger(game.getHeight());
			shipName = game.bombSquare(c_xpos,c_ypos,true);
			
			if (shipName!=null)
			  System.out.println("Your " + shipName + " has been destroyed!");
		}
		
		System.out.println("*** GAME OVER ***\n");
		
		if(game.didPlayerWin()) {
			System.out.println("*** WELL DONE! ***");
		} else {
			System.out.println("*** BAD LUCK! ***");
		}
	}
	
	public static void main(String[] args) {
		printWelcomeMessage();
		playGame(new BattleShipsGame(16, 16));
	}
}
