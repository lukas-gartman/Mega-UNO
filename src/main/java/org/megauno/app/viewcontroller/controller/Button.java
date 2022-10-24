package org.megauno.app.viewcontroller.controller;


import org.megauno.app.viewcontroller.IDrawable;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;

/**
 *  Abstract class for buttons. When extending, override onClicked.
 */
public abstract class Button implements IDrawable {
	// Sprite can be change by sub-class
	protected Sprite sprite;
	private float x;
	private float y;
	// Used by sub-class
	protected GameController gameController;
	private Clickable clickable;
	
	public Button(float x, float y, GameController gameController, Sprite sprite) {
		this.x = x;
		this.y = y;
		this.gameController = gameController;
		this.sprite = sprite;

		clickable = new Clickable(sprite.getWidth(), sprite.getHeight());
	}

	// Draws the button and calls onClicked if clicked
	public void draw(float delta, Batch batch) {
		if (clickable.wasClicked(x, y)) {
			onClicked();
		}

		batch.draw(sprite, x, y);
	}

	public abstract void onClicked();
}

