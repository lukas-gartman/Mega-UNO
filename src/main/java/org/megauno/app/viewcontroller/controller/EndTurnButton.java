package org.megauno.app.viewcontroller.controller;

import org.megauno.app.application.ClientScreen;
import com.badlogic.gdx.graphics.g2d.Batch;
import org.megauno.app.viewcontroller.Preposition;

public class EndTurnButton extends Button {
	private Preposition prep;

	public EndTurnButton(float x, float y, GameController gameController, Preposition prep) {
		super(x, y, gameController, ClientScreen.commenceForth);
		this.prep = prep;
	}

	@Override
	public void onClicked() {
		gameController.commenceForth();
	}

	@Override
	public void draw(float delta, Batch batch) {
		if (prep.value()) {
			sprite = ClientScreen.commenceForth;
		} else {
			sprite = ClientScreen.tomte;
		}
		super.draw(delta, batch);
	}
}
