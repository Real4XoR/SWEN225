package src.main.java.nz.ac.vuw.ecs.swen225.gp22.renderer;

import src.main.java.nz.ac.vuw.ecs.swen225.gp22.domain.Actor;
import src.main.java.nz.ac.vuw.ecs.swen225.gp22.domain.Colour;
import src.main.java.nz.ac.vuw.ecs.swen225.gp22.domain.Door;
import src.main.java.nz.ac.vuw.ecs.swen225.gp22.domain.Exit;
import src.main.java.nz.ac.vuw.ecs.swen225.gp22.domain.ExitLock;
import src.main.java.nz.ac.vuw.ecs.swen225.gp22.domain.Game;
import src.main.java.nz.ac.vuw.ecs.swen225.gp22.domain.Info;
import src.main.java.nz.ac.vuw.ecs.swen225.gp22.domain.Key;
import src.main.java.nz.ac.vuw.ecs.swen225.gp22.domain.Player;
import src.main.java.nz.ac.vuw.ecs.swen225.gp22.domain.Point;
import src.main.java.nz.ac.vuw.ecs.swen225.gp22.domain.RoamingActor;
import src.main.java.nz.ac.vuw.ecs.swen225.gp22.domain.Tile;
import src.main.java.nz.ac.vuw.ecs.swen225.gp22.domain.Treasure;

import java.awt.Color;
import java.awt.Stroke;
import java.awt.BasicStroke;
import java.awt.Dimension;
import java.io.File;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.image.BufferedImage;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Toolkit;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import javax.swing.JPanel;
import javax.swing.Timer;
import javax.imageio.ImageIO;

/**
 * Renderer class for displaying the board, Draws the visible tiles, performs
 * animations.
 * 
 * @author Nathan Cobbald - ID: 30057419
 */
public class Renderer extends JPanel implements ActionListener {

	private static final int focusRange = 12;
	private static final int tileSize = 35;
	private int tileWidth = 11;
	private int tileHeight = 9;
	protected static final String filePath = "src/main/java/nz/ac/vuw/ecs/swen225/gp22/Resources/Images/";

	private final Tile[][] focus;
	private int[][] floorthings;
	private Player player;
	private List<RoamingActor> actors = new ArrayList<>(); // List of actors on screen
	private List<Key> keys = new ArrayList<>();
	private List<Treasure> treasures = new ArrayList<>();
	private final Game game;
	private BufferedImage[][] sprites;
	private int inventorySize = 0;
	private long treasureAmount = 0;
	private HashMap<Colour, Color> colorAdapter = new HashMap<>();
	private boolean setFlag;
	Dimension windowSize = Toolkit.getDefaultToolkit().getScreenSize();

	private Timer playerAnimation = new Timer(500, unused -> {
		setFlag = !setFlag;
	});

	Audio audio = new Audio();

	public Renderer(Game game) {
		playerAnimation.start();

		this.splitSheet("SpriteSheet");
		focus = new Tile[tileWidth + 2][tileWidth];
		floorthings = new int[tileWidth][tileHeight];
		colorAdapter.put(Colour.YELLOW, new Color(255, 255, 0));
		colorAdapter.put(Colour.RED, new Color(255, 0, 0));
		colorAdapter.put(Colour.BLUE, new Color(0, 0, 255));
		colorAdapter.put(Colour.GREEN, new Color(0, 255, 0));

		// JPanel
		Dimension dimension = new Dimension(tileSize * focusRange, tileSize * focusRange);
		setPreferredSize(dimension);
		setLayout(new FlowLayout(FlowLayout.LEFT));

		// Setting game variables
		this.game = game;
		this.sprites = new BufferedImage[game.board().length][game.board().length];

		this.player = game.player();
		this.calculateSprites();
		this.updateLevel(game);

		//playMusic(6);
	}

	/**
	 * Update level each frame
	 * 
	 * @param game
	 */
	public void updateLevel(Game game) {
		player = game.player();
		actors = game.getRoamingActorList();
		keys = game.getKeyList();
		treasures = game.getTreasureList();

		setVision(game);
	}

	/**
	 * Setting the focus area for the game
	 * 
	 * @param game Game object
	 */
	private void setVision(Game game) {
		int w = (int) Math.floor(tileWidth / 2.0);
		int h = (int) Math.floor(tileHeight / 2.0);

		for (int x = -w; x < w + 1; x++) {
			for (int y = -h; y < h + 1; y++) {
				int xpos = player.location().x() + x;
				int ypos = player.location().y() + y;
				try {
					Tile t = game.getTile(new Point(xpos, ypos));
					focus[x + w][y + h] = t;
					floorthings[x + w][y + h] = (xpos * ypos + 3) % 11;
				} catch (IllegalArgumentException e) {
					focus[x + w][y + h] = null;
				}
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		repaint();
		updateUI();
	}

	@Override
	public void paintComponent(Graphics g) {
		try {
			super.paintComponent(g);

			Graphics2D g2d = (Graphics2D) g.create();
			int offsetX = 0;
			int offsetY = 0;

			// Draw full frame
			setVision(game);
			float scale = ((float) getHeight() / ((float) tileSize * tileHeight));
			g2d.scale(scale, scale);
			drawTiles(g2d, offsetX, offsetY, focus, floorthings);
			drawEntities(g2d, offsetX, offsetY);

			g2d.dispose();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Plays game music that loops continuosuly until told otherwise
	 * 
	 * @param i
	 */
	private void playMusic(int i) {
		if (audio.isRunning()) { return; }
		audio.setFile(i);
		audio.play();
		audio.loop();
	}

	/**
	 * Plays selected sound effect
	 * 
	 * @param i
	 */
	private void playSoundEffect(int i) {
		if (audio.isRunning()) { return; }
		audio.setFile(i);
		audio.play();
	}

	/**
	 * Stops the current sound track playing
	 */
	private void stopMusic() {
		audio.stop();
	}

	/**
	 * Sets all the relevant information for the infobox
	 * 
	 * 
	 */
	private void drawInfoText(int x, int y, int width, int height, Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		Color c = new Color(0,0,0, 180);
		g2.setColor(c);
		g2.fillRoundRect(x, y, width, height, 35, 35);

		c= new Color(255,255,255);
		g2.setColor(c);
		g2.setStroke(new BasicStroke(2));
		g2.drawRoundRect(x+5, y+5, width-10, height-10, 25, 25);
	}

	/**
	 * Draws the wrapped text for the infoBox
	 * 
	 * @param g Graphics
	 */
	private void drawWrappedText(Graphics g) {
		int x = tileSize;
		int y = tileSize / 2;
		int width = tileSize * 9;
		int height = tileSize * 2;

		drawInfoText(x, y, width, height, g);

		g.setFont(g.getFont().deriveFont(Font.PLAIN, 8));
		x += tileSize;
		y += tileSize;

		for (String line : game.getInfo().split("\\.")) {
			g.drawString(line, x, y);
			y += 20;
		}
	}

	/**
	 * First step of draw method, draws all tiles in players current vision.
	 *
	 * @param g         graphics object to allow drawing images together
	 * @param offsetX   offset in x direction
	 * @param offsetY   offset in y direction
	 * @param drawArray array of tiles to draw
	 * @throws IOException if file not found
	 */
	private void drawTiles(Graphics g, int offsetX, int offsetY, Tile[][] drawArray, int[][] floorIndices)
			throws IOException {

		for (int x = 0; x <= tileWidth - 1; x++) {
			for (int y = 0; y <= tileHeight - 1; y++) {
				BufferedImage toDraw;
				int width = game.board().length;

				if (drawArray[x][y] != null && this.getBoardX(x) < width && this.getBoardY(y) < width
						&& this.getBoardX(x) >= 0 && this.getBoardY(y) >= 0) {
					toDraw = sprites[this.getBoardX(x)][this.getBoardY(y)];
				} else {
					toDraw = spriteArray[3]; // Draw void
				}

				g.drawImage(toDraw, (x * tileSize) + offsetX, (y * tileSize) + offsetY, this);
			}
		}
	}

	/**
	 * Draws the entities on the screen
	 * 
	 * @param g       Graphics
	 * @param offsetX X offset
	 * @param offsetY Y offset
	 * @throws IOException if file not found
	 */
	private void drawEntities(Graphics g, int offsetX, int offsetY) throws IOException {
		Player p = this.game.player();
		// Draw Player
		g.drawImage(getPlayerSprite(), (getVisionX(game.player().location().x()) * tileSize),
				(getVisionY(player.location().y()) * tileSize), this);

		if (!game.isGameOver()) {

			// Draw actors
			for (RoamingActor actor : actors) {
				if (actorInVision(actor)) {
					g.drawImage(spriteArray[30], (getVisionX(actor.location().x()) * tileSize) + offsetX,
							(getVisionY(actor.location().y()) * tileSize) + offsetY, this);
				}
			}
			// Draw keys
			for (Key key : keys) {
				if (key.collected()) {
					continue;
				}
				if (actorInVision(key)) {
					g.drawImage(tintImage(spriteArray[14], colorAdapter.get(key.colour())),
							(getVisionX(key.location().x()) * tileSize) + offsetX,
							(getVisionY(key.location().y()) * tileSize) + offsetY, this);
				}
			}
			// Draw treasure
			for (Treasure treasure : treasures) {
				if (treasure.collected()) {
					continue;
				}
				if (actorInVision(treasure)) {
					g.drawImage(spriteArray[9], (getVisionX(treasure.location().x()) * tileSize) + offsetX,
							(getVisionY(treasure.location().y()) * tileSize) + offsetY, this);
				}
			}
		}
		// Plays sound effect depending on the tile
		try {
			if (game.playerDead()) {
				audio.stop();
				playSoundEffect(0);
			} else if (game.isWin()) {
				audio.stop();
				playSoundEffect(2);
			} else if (game.getTile(player.location()) instanceof Info) {
				playSoundEffect(7);
				drawWrappedText(g);
			} else if (game.treasuresCollected() > treasureAmount) {
				audio.stop();
				playSoundEffect(3);
				treasureAmount++;
			} else if (game.inventory().size() > inventorySize) {
				audio.stop();
				playSoundEffect(4);
				inventorySize++;
			} else {
				playSoundEffect(5); // Step sound by default
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Returns the focus X position
	 * 
	 * @param x X position
	 * @return
	 */
	private int getVisionX(int x) {
		return x - player.location().x() + this.tileWidth / 2;
	}

	/**
	 * Returns the focus Y position
	 * 
	 * @param y Y position
	 * @return
	 */
	private int getVisionY(int y) {
		return y - player.location().y() + this.tileHeight / 2;
	}

	/**
	 * Returns board X
	 * 
	 * @param x X Position
	 * @return
	 */
	private int getBoardX(int x) {
		return x - this.tileWidth / 2 + player.location().x();
	}

	/**
	 * Return board Y
	 * 
	 * @param y Y position
	 * @return
	 */
	private int getBoardY(int y) {
		return y - this.tileHeight / 2 + player.location().y();
	}

	/**
	 * Is the Actor in vision
	 * 
	 * @param actor actor to check
	 * @return
	 */
	public boolean actorInVision(Actor actor) {
		return (getVisionX(actor.location().x()) >= 0 && getVisionX(actor.location().x()) < tileWidth + 2
				&& getVisionY(actor.location().y()) >= 0 && getVisionY(actor.location().y()) < tileWidth);
	}

	// Wall sheet
	private int sheetWidth = 6;
	private int sheetHeight = 8;
	private BufferedImage sheet;
	private BufferedImage[] spriteArray = new BufferedImage[sheetWidth * sheetHeight];
	private List<Integer> floorImages = new ArrayList<Integer>();

	/**
	 * Splits the passed sheet into 35x35 slices for use
	 * 
	 * @param fileName Name of file
	 */
	private void splitSheet(String fileName) {
		try {
			sheet = ImageIO.read(new File(filePath + fileName + ".png"));

			for (int y = 0; y < sheetHeight; y++) {
				for (int x = 0; x < sheetWidth; x++) {
					BufferedImage img = cutSprite(sheet, new Rectangle(x * tileSize, y * tileSize, tileSize, tileSize));
					spriteArray[y * sheetWidth + x] = img;
				}
			}
			// Loading floor tile straight away
			floorImages.add(0);
			floorImages.add(1);
			floorImages.add(2);
			floorImages.add(6);
			floorImages.add(7);
			floorImages.add(12);
			floorImages.add(13);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * used for finding correct sprite
	 */
	private void calculateSprites() {
		for (int x = 0; x < this.game.board().length; x++) {
			for (int y = 0; y < this.game.board().length; y++) {
				sprites[x][y] = getWallSprite(x, y);
			}
		}
		for (int x = 0; x < this.game.board().length; x++) {
			for (int y = 0; y < this.game.board().length; y++) {
				if (!game.getTile(new Point(x, y)).isWall()) {
					getWallSprite(x, y);
				}
			}
		}
	}

	/**
	 * Returns the wall sprite to use
	 * 
	 * @param worldx X position
	 * @param worldy Y position
	 * @return
	 */
	private BufferedImage getWallSprite(int worldx, int worldy) {

		BufferedImage img = null;
		Tile t = null;

		try {
			t = this.game.getTile(new Point(worldx, worldy));
		} catch (IllegalArgumentException e) { e.printStackTrace(); }

		if (t instanceof Door) { // Drawing doors
			Door door = (Door) t;
			if(t.isWall() == false) {
				img = spriteArray[3];
			} else {
				img = tintImage(spriteArray[8], colorAdapter.get(door.key().colour())); // Doors
			}
		} else if (t instanceof ExitLock) { // Drawing exit lock
			img = spriteArray[10];
		} else if (t instanceof Exit) { // Drawing Exit
			img = spriteArray[16];
		} else if (t instanceof Info) { // Drawing InfoBox
			img = spriteArray[15];
		} else if (t.isWall()) { // Background
			img = spriteArray[3];
			if (t != null && !t.isWall()) {
				img = spriteArray[2];
			}
			img = spriteArray[4];
		} else { // Floor
			int floorIndex = floorImages.get((int) (Math.random() * floorImages.size()));
			img = spriteArray[floorIndex];
		}
		return img;
	}

	/**
	 * Tints the image for colouring the keys and doors accordingly
	 * 
	 * @param src   source image
	 * @param color color to tint
	 * @return image
	 */
	public static BufferedImage tintImage(BufferedImage src, Color color) {
		BufferedImage result = new BufferedImage(src.getWidth(), src.getHeight(), src.getType());
		for (int x = 0; x < src.getWidth(); x++) {
			for (int y = 0; y < src.getHeight(); y++) {
				Color pixelColor = new Color(src.getRGB(x, y), true);
				float originalPercent = pixelColor.equals(Color.WHITE) ? 0 : 2;
				float newPercent = pixelColor.equals(Color.WHITE) ? 2 : 0;
				int r = (int) (pixelColor.getRed() * originalPercent + color.getRed() * newPercent) / 2;
				int g = (int) (pixelColor.getGreen() * originalPercent + color.getGreen() * newPercent) / 2;
				int b = (int) (pixelColor.getBlue() * originalPercent + color.getBlue() * newPercent) / 2;
				int a = pixelColor.getAlpha();
				int rgba = (a << 24) | (r << 16) | (g << 8) | b;
				result.setRGB(x, y, rgba);
			}
		}
		return result;
	}

	/**
	 * returns the corrcet Player sprite
	 * 
	 * @return image
	 */
	private BufferedImage getPlayerSprite() {
		Player p = this.game.player();
		BufferedImage img = null;

		switch (p.facingDirection()) {
			case DOWN: // 36 & 37
				if (setFlag) {
					img = spriteArray[36];
				} else {
					img = spriteArray[37];
				}

				break;
			case UP: // 38 & 39
				if (setFlag) {
					img = spriteArray[38];
				} else {
					img = spriteArray[39];
				}
				break;
			case LEFT: // 44 & 45
				if (setFlag) {
					img = spriteArray[44];
				} else {
					img = spriteArray[45];
				}
				break;
			case RIGHT: // 42 & 43
				if (setFlag) {
					img = spriteArray[42];
				} else {
					img = spriteArray[43];
				}
				break;
			case NONE:
				break;
		}
		// setFlag = !setFlag;
		return img;
	}

	/**
	 * Cuts up the passed image into slices
	 * 
	 * @param src  source image
	 * @param rect Rectangle
	 * @return image
	 */
	private BufferedImage cutSprite(BufferedImage src, Rectangle rect) {
		BufferedImage img = src.getSubimage(rect.x, rect.y, rect.width, rect.height);
		return img;
	}

	/**
	 * Return colour hashmap
	 * 
	 * @return hashmap
	 */
	public HashMap<Colour, Color> getColorMap() {
		return colorAdapter;
	}

	/**
	 * Return the key image
	 * 
	 * @return image
	 */
	public BufferedImage getKeyImage() {
		return spriteArray[14];
	}
}

