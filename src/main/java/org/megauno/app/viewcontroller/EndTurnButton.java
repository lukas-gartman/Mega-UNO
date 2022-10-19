package org.megauno.app.viewcontroller;

import org.megauno.app.model.Game.Game;
import org.megauno.app.viewcontroller.datafetching.SpriteLoader;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;

class EndTurnButton implements IDrawable {
	public float x;
	public float y;
	private Game game;

	private Clickable clickable;

	static private Sprite sprite = new SpriteLoader().retrieveData("assets/CommenceForth.png");

	public EndTurnButton(float x, float y, Game game) {
		this.x = x;
		this.y = y;
		this.game = game;

		clickable = new Clickable(sprite.getWidth(), sprite.getHeight());
	}

	@Override
	public void draw(float delta, Batch batch) {
		if (clickable.wasClicked(x, y)) {
			game.commence_forth = true;
		}

		batch.draw(sprite, x, y);
	}
}
