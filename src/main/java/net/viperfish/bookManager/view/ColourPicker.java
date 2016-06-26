package net.viperfish.bookManager.view;

import java.awt.Color;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public final class ColourPicker {

	private Set<Color> colours;
	private Random rand;

	public ColourPicker() {
		colours = new HashSet<>();
		rand = new Random();
	}

	private Color generate() {
		final float hue = rand.nextFloat();
		// Saturation between 0.1 and 0.3
		final float saturation = (rand.nextInt(2000) + 1000) / 10000f;
		final float luminance = 0.9f;
		final Color color = Color.getHSBColor(hue, saturation, luminance);
		return color;
	}

	public Color pick() {
		Color result = generate();
		while (colours.contains(result)) {
			result = generate();
		}
		return result;
	}

	public void reset() {
		colours.clear();
	}

}
