package org.megauno.app.viewcontroller;

import com.badlogic.gdx.scenes.scene2d.Stage;
import org.megauno.app.model.Player.Player;


// GameView views a set of UNO, delegates work to thisPlayer and otherPlayers
// to update their appearence when the game changes
// GameView is a view for a specific player, so it is tightly coupled with the
// playerID, which it uses to query information from the right player object in the game.
public class GameView extends Stage {
	private int playerID;
	private WilliamIGame game;
	
	public GameView(int playerID, IGame game) {
		// Add this view's player
		this.playerID = playerID;
		ThisPlayer thisPlayer = new ThisPlayer(game.getPlayerWithId(playerID));
		addActor(thisPlayer);

		// Add all other players
		Player[] players = game.getPlayers();
		int numberOfPlayers = players.length;
		for (Player p: players) {
			if (!(p.getId() == playerID)) {
				// TODO: add sprite and position
				addActor(new OtherPlayer(p.getId(),p.numOfCards()));
			}
		}

	}
}

