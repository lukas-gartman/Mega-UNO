package org.megauno.app;

import org.megauno.app.model.Game.Game;
import com.badlogic.gdx.ApplicationAdapter;
import org.megauno.app.model.Game.Lobby;
import org.megauno.app.model.Game.PlayerCircle;
import org.megauno.app.model.Player.Player;

import java.util.HashMap;

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

		game = new Game(playerCircle);
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