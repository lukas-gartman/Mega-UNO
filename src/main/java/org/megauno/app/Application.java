package org.megauno.app;

import org.megauno.app.model.Game.Game;
import com.badlogic.gdx.ApplicationAdapter;
import org.megauno.app.model.Game.PlayerCircle;
import org.megauno.app.viewcontroller.ViewController;

public class Application extends ApplicationAdapter {
	ViewController viewController;
	Game game;

	@Override
	public void create () {
		PlayerCircle players = new PlayerCircle();
		// Add players!!!

		game = new Game(players, 10);
		viewController = new ViewController(game);
	}

	@Override
	public void render () {
		viewController.draw();
		game.update();
	}

	@Override
	public void dispose () {
		viewController.teardown();
	}

	public static void testFunc () {
		System.out.println("Wow!");
	}
}
