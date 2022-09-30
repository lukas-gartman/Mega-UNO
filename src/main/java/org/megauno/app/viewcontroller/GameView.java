package org.megauno.app.viewcontroller;

import com.badlogic.gdx.scenes.scene2d.Stage;
import org.megauno.app.model.Player.Player;

import java.util.List;


// GameView views a set of UNO, delegates work to thisPlayer and otherPlayers
// to update their appearence when the game changes
// GameView is a view for a specific player, so it is tightly coupled with the
// playerID, which it uses to query information from the right player object in the game.
public class GameView extends Stage {
	
	public GameView(ThisPlayer thisPlayer, List<OtherPlayer> otherPlayers) {
		addActor(thisPlayer);
		for (OtherPlayer otherPlayer: otherPlayers) {
				addActor(otherPlayer);
		}

	}
}

