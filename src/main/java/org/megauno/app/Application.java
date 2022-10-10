package org.megauno.app;

import java.util.ArrayList;
import java.util.List;

import org.megauno.app.model.Game.Game;
import com.badlogic.gdx.ApplicationAdapter;
import org.megauno.app.model.Game.PlayerCircle;
import org.megauno.app.model.Player.Player;
import org.megauno.app.utility.Publisher;
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
		List<Player> tmpList = new ArrayList<>();
		for (int i = 0; i <3; i++){
			tmpList.add(new Player(i));
		}

		PlayerCircle players = new PlayerCircle(tmpList);


		Publisher<Game> publisher = new Publisher<>();

		game = new Game(players, 7,publisher);
		viewController = new ViewController(game);
		publisher.addSubscriber(viewController);
	}

	@Override
	public void render() {
		game.update();
		viewController.draw();
	}

	@Override
	public void dispose() {
		viewController.teardown();
	}

	public static void testFunc() {
		System.out.println("Wow!");
	}
}
