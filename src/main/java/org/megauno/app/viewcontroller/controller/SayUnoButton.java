package org.megauno.app.viewcontroller.controller;

import org.megauno.app.application.ClientScreen;

public class SayUnoButton extends Button {
	public SayUnoButton(float x, float y, GameController gameController) {
		super(x, y, gameController, ClientScreen.sayUnoButton);
	}

	@Override
	public void onClicked() {
		gameController.sayUno();
	}
}

