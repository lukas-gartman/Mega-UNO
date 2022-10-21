package org.megauno.app.viewcontroller.controller;

import org.megauno.app.application.ClientApplication;

public class SayUnoButton extends Button {
	public SayUnoButton(float x, float y, GameController gameController) {
		super(x, y, gameController, ClientApplication.sayUnoButton);
	}

	@Override
	public void onClicked() {
		gameController.sayUno();
	}
}

