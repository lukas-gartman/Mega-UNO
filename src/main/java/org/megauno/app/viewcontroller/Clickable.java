package org.megauno.app.viewcontroller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

// Bounding box, used to know if it was clicked or not
public class Clickable {
	public float width;
	public float height;

	public Clickable(float width, float height) {
		this.width = width;
		this.height = height;
	}

	public boolean wasClicked(float x, float y) {
		if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
			// Note that the input coordinate system is flipped in the y axis
			float input_x = Gdx.input.getX();
			float input_y = Gdx.graphics.getHeight() - Gdx.input.getY();

			float xLeft = x;
			float xRight = x + width;
			float yBottom = y;
			float yTop = y + height;

			return (input_x > xLeft && input_x < xRight) && (input_y > yBottom && input_y < yTop);
		}
		return false;
	}
}

