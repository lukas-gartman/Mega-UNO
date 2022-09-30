package org.megauno.app;

import org.megauno.app.model.Cards.ICard;
import org.megauno.app.model.Game.PlayerCircle;
import org.megauno.app.model.Player.Player;
import org.megauno.app.utility.ObserverPattern.Publisher;
import org.megauno.app.viewcontroller.*;
import org.megauno.app.model.Game.Game;
import com.badlogic.gdx.ApplicationAdapter;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.megauno.app.viewcontroller.GameViewsFactory.createGameViews;

public class Application extends ApplicationAdapter {
	Game game;
	int nrOfPlayers;

	public Application(int nrOfPlayers) {
		this.nrOfPlayers = nrOfPlayers;
	}

	@Override
	public void create() {
		Player[] players = new Player[nrOfPlayers];
		for(int i = 0; i < nrOfPlayers; i++){
			players[i] = new Player(new ArrayList<>(),i);
		}
		PlayerCircle playerCircle = new PlayerCircle(Arrays.stream(players).toList());
		Publisher<IGame> publisher = new Publisher<>();
		game = new Game(publisher,playerCircle);

		List<GameView> gameViews = createGameViews(players,publisher);


	}

	@Override
	public void resize (int width, int height) {
		// See below for what true means.
		//viewController.resize(width, height);
	}
	@Override
	public void render () {
		//viewController.draw();
	}
	
	@Override
	public void dispose () {
		//viewController.teardown();
	}

	public static void testFunc () {
		System.out.println("Wow!");
	}
}
