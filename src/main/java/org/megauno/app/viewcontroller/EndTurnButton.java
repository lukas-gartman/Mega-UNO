package org.megauno.app.viewcontroller;

import org.megauno.app.ClientApplication;
import com.badlogic.gdx.graphics.g2d.Batch;

class EndTurnButton extends Button {
	private Preposition prep;

	public EndTurnButton(float x, float y, GameController gameController, Preposition prep) {
		super(x, y, gameController, ClientApplication.commenceForth);
		this.prep = prep;
	}

	@Override
	public void onClicked() {
		gameController.commenceForth();
	}

	@Override
	public void draw(float delta, Batch batch) {
		if (prep.value()) {
			sprite = ClientApplication.commenceForth;
		} else {
			sprite = ClientApplication.tomte;
		}
		super.draw(delta, batch);
	}
}
