package org.megauno.app.viewcontroller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

// Bounding box, used to know if it was clicked or not
public class Clickable {
	public float width;
	public float height;

	private static float TARGET_WINDOW_WIDTH = 640;
	private static float TARGET_WINDOW_HEIGHT = 480;

	public Clickable(float width, float height) {
		this.width = width;
		this.height = height;
	}

	public boolean wasClicked(float x, float y) {
		if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
			// Note that the input coordinate system is flipped in the y axis
			float screenWidth = Gdx.graphics.getWidth();
			float screenHeight = Gdx.graphics.getHeight();

			// Coordinates are translated to screen coordinates with this modifier
			float widthMod = screenWidth / TARGET_WINDOW_WIDTH;
			float heightMod = screenHeight / TARGET_WINDOW_HEIGHT;

			float input_x = Gdx.input.getX();
			float input_y = (Gdx.graphics.getHeight() - Gdx.input.getY());

			float xLeft = x * widthMod;
			float xRight = (x + width) * widthMod;
			float yBottom = y * heightMod;
			float yTop = (y + height) * heightMod;

			return (input_x > xLeft && input_x < xRight) && (input_y > yBottom && input_y < yTop);
		}
		return false;
	}
}

