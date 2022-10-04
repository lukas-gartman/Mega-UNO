package org.megauno.app.viewcontroller;

import org.megauno.app.model.Game.Game;

import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class ThisPlayer extends Image {
	private Game game;

	public ThisPlayer(Game game) {
		this.game = game;
	}
}

