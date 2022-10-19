package org.megauno.app.viewcontroller;

import org.megauno.app.ClientApplication;
import org.megauno.app.model.Game.Game;
import org.megauno.app.viewcontroller.datafetching.SpriteLoader;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class DrawPile {
	static private Sprite sprite;
	private float x;
	private float y;
	private GameController gameController;
	private Clickable clickable;

	public DrawPile(float x, float y, GameController gameController) {
		this.x = x;
		this.y = y;
		this.gameController = gameController;
		sprite = ClientApplication.DrawPile;

		clickable = new Clickable(sprite.getWidth(), sprite.getHeight());
	}

	public void draw(float delta, Batch batch) {
		if (clickable.wasClicked(x, y)) {
			gameController.drawCard();
		}

		batch.draw(sprite, x, y);
	}

}

