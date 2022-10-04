package org.megauno.app;

import java.util.ArrayList;
import java.util.List;

import org.megauno.app.model.Game.Game;
import com.badlogic.gdx.ApplicationAdapter;
import org.megauno.app.model.Game.PlayerCircle;
import org.megauno.app.model.Player.Player;
import org.megauno.app.viewcontroller.ViewController;

public class Application extends ApplicationAdapter {
	ViewController viewController;
	Game game;

	@Override
	public void create() {
		// Simple creation of a game
		//TODO: discuss proper place to initalize the player objects/assumptions
		// about ID:s when creating Game

		// Add players
		Player p1 = new Player();
		p1.id = 0;
		Player p2 = new Player();
		p2.id = 1;
		Player p3 = new Player();
		p3.id = 2;

		List<Player> tmpList = new ArrayList<>();
		tmpList.add(p1);
		tmpList.add(p1);
		tmpList.add(p1);
		PlayerCircle players = new PlayerCircle(tmpList);

		game = new Game(players, 3);
		viewController = new ViewController(game);
	}

	@Override
	public void render() {
		viewController.draw();
		game.update();
	}

	@Override
	public void dispose() {
		viewController.teardown();
	}

	public static void testFunc() {
		System.out.println("Wow!");
	}
}
