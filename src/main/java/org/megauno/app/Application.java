package org.megauno.app;

import org.megauno.app.viewcontroller.*;
import org.megauno.app.model.Game.Game;
import com.badlogic.gdx.ApplicationAdapter;

public class Application extends ApplicationAdapter {
	ViewController viewController;
	Game game;
	
	@Override
	public void create () {
		game = new Game();
		viewController = new ViewController(game);
	}

	@Override
	public void resize (int width, int height) {
		// See below for what true means.
		viewController.resize(width, height);
	}
	@Override
	public void render () {
		viewController.draw();
	}
	
	@Override
	public void dispose () {
		viewController.teardown();
	}

	public static void testFunc () {
		System.out.println("Wow!");
	}
}
