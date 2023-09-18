package src.main.java.nz.ac.vuw.ecs.swen225.gp22.domain;

import java.awt.Color;

/**
 * Colour enum represents the Colours of a Key.
 * @author Takumi Grainger (300583202)
 *
 */
public enum Colour {
	YELLOW(Color.yellow),
	RED(Color.red),
	GREEN(Color.green),
	BLUE(Color.blue);
	
	/**
	 * The java.awt.Color equivalent of this enum.
	 */
	public final Color awtColor;
	
	/**
	 * Get the java.awt.Color equivalent of this enum.
	 * @return awtColor
	 */
	public Color awtColor() { return awtColor; }
	Colour(Color c) { this.awtColor = c;}
}