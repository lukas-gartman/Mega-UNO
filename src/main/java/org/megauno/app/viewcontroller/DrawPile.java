package org.megauno.app.viewcontroller;

import org.megauno.app.ClientApplication;

public class DrawPile extends Button {
	public DrawPile(float x, float y, GameController gameController) {
		super(x, y, gameController, ClientApplication.DrawPile);
	}

	@Override
	public void onClicked() {
		gameController.drawCard();
	}
}

