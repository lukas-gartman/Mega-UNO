package org.megauno.app.viewcontroller.controller;

import org.megauno.app.application.ClientScreen;

public class DrawPile extends Button {
	public DrawPile(float x, float y, GameController gameController) {
		super(x, y, gameController, ClientScreen.drawPile);
	}

	@Override
	public void onClicked() {
		gameController.drawCard();
	}
}

