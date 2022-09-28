package org.megauno.app.viewcontroller;

import com.badlogic.gdx.scenes.scene2d.Stage;

// GameView views a set of UNO, delegates work to thisPlayer and otherPlayers
// to update their appearence when the game changes
// GameView is a view for a specific player, so it is tightly coupled with the
// playerID, which it uses to query information from the right player object in the game.
public class GameView extends Stage {
	private int playerID;
	private IGame game;
	
	public GameView(int playerID) {
		// Add this view's player
		this.playerID = playerID;
		ThisPlayer thisPlayer = new ThisPlayer(game);
		addActor(thisPlayer);

		// Add all other players
		int numberOfPlayers = game.getInitialState().numberOfPlayers;
		for (int id = 0; id < numberOfPlayers; id++) {
			if (!(id == playerID)) {
				// TODO: add sprite and position
				addActor(new OtherPlayer(id, game));
			}
		}
	}
}

