package org.megauno.app;

import org.megauno.app.model.Game.Game;
import com.badlogic.gdx.ApplicationAdapter;
import org.megauno.app.model.Game.Lobby;
import org.megauno.app.model.Game.PlayerCircle;
import org.megauno.app.model.Player.Player;
import org.megauno.app.utility.Publisher;
import org.megauno.app.viewcontroller.ViewController;

import java.util.HashMap;
import java.util.concurrent.Phaser;

public class Application extends ApplicationAdapter {
	private Lobby lobby;
	private Game game;
	private ViewController viewController;

	@Override
	public void create() {
		Phaser phaser = new Phaser(1); // Used to signal when the lobby is done searching for players
		lobby = new Lobby(phaser); // Create the lobby
		try {
			phaser.awaitAdvance(0); // Wait for the host to start the game (blocking call)
		} catch (IllegalStateException ex) {
			System.out.println("The lobby was closed");
		}

		System.out.println("Starting game!");
		PlayerCircle playerCircle = lobby.getPlayerCircle();
		HashMap<Integer, Player> playersWithID = lobby.getPlayersWithID();

		Publisher<Game> publisher = new Publisher<>();
		game = new Game(playerCircle, 7,publisher);
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
