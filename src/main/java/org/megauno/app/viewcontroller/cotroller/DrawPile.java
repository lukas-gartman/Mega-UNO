package org.megauno.app.viewcontroller.cotroller;

import org.megauno.app.application.ClientApplication;

public class DrawPile extends Button {
	public DrawPile(float x, float y, GameController gameController) {
		super(x, y, gameController, ClientApplication.drawPile);
	}

	@Override
	public void onClicked() {
		gameController.drawCard();
	}
}

