package org.megauno.app.viewcontroller.controller;

import org.megauno.app.application.ClientApplication;

/**
 * Draws a card for the current player when clicked.
 */
public class DrawPile extends Button {
	public DrawPile(float x, float y, GameController gameController) {
		super(x, y, gameController, ClientApplication.drawPile);
	}

	@Override
	public void onClicked() {
		gameController.drawCard();
	}
}

