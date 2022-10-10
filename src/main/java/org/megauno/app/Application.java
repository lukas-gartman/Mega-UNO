package org.megauno.app;

import java.util.ArrayList;
import java.util.List;

import org.megauno.app.model.Game.Game;
import com.badlogic.gdx.ApplicationAdapter;
import org.megauno.app.model.Game.Lobby;
import org.megauno.app.model.Game.PlayerCircle;
import org.megauno.app.model.Player.Player;

import java.util.HashMap;
import org.megauno.app.utility.Publisher;
import org.megauno.app.viewcontroller.ViewController;

public class Application extends ApplicationAdapter {
	private Lobby lobby;
	private Game game;
	private ViewController viewController;

	@Override
	public void create () {
		lobby = new Lobby(); // Create the lobby
		while (lobby.isSearchingForPlayers()) {} // Wait for the host to start the game
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
