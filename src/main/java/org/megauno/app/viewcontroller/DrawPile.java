package org.megauno.app.viewcontroller;

import org.megauno.app.model.Game.Game;
import org.megauno.app.viewcontroller.datafetching.SpriteLoader;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class DrawPile {
	static private Sprite sprite = new SpriteLoader().retrieveData("assets/SayUnoButton.png");
	private float x;
	private float y;
	private Game game;
	private Clickable clickable;

	public DrawPile(float x, float y, Game game) {
		this.x = x;
		this.y = y;
		this.game = game;

		clickable = new Clickable(sprite.getWidth(), sprite.getHeight());
	}

	public void draw(float delta, Batch batch) {
		if (clickable.wasClicked(x, y)) {
			//TODO: add call to game
			game.playerDraws();
		}

		batch.draw(sprite, x, y);
	}

}

