package org.megauno.app.viewcontroller;

import org.megauno.app.ClientApplication;
import org.megauno.app.model.Game.Game;
import org.megauno.app.model.Player.Player;
import org.megauno.app.viewcontroller.datafetching.IDrawable;
import org.megauno.app.viewcontroller.datafetching.SpriteLoader;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;


public class SayUnoButton implements IDrawable {
	private Sprite sprite;
	private float x;
	private float y;
	private GameController gameController;
	private int playerID;
	private Clickable clickable;

	public SayUnoButton(float x, float y, GameController gameController) {
		this.x = x;
		this.y = y;
		this.gameController = gameController;
		sprite = ClientApplication.SayUnoButton;

		clickable = new Clickable(sprite.getWidth(), sprite.getHeight());
	}

	public void draw(float delta, Batch batch) {
		if (clickable.wasClicked(x, y)) {
			//TODO: add call to game
			gameController.sayUno();
		}

		batch.draw(sprite, x, y);
	}
}

