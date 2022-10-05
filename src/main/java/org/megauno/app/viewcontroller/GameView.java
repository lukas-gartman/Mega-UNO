package org.megauno.app.viewcontroller;

import com.badlogic.gdx.scenes.scene2d.Stage;

import java.util.ArrayList;
import java.util.List;

import org.megauno.app.model.Cards.ICard;
import org.megauno.app.model.Game.Game;

// For now, GameView parses deltas from Game and calls the appropriate
public class GameView extends Stage {
	private int playerID;
	private Game game;

	private ThisPlayer thisPlayer;
	private List<OtherPlayer> otherPlayers = new ArrayList<>();
	
	public GameView(Game game, int playerID) {
		// Add this view's player
		this.playerID = playerID;
		List<List<ICard>> allPlayerCards = game.getAllPlayerCards();   // All the different players' cards
		thisPlayer = new ThisPlayer(playerID, allPlayerCards.get(playerID), game);
		System.out.println("NUMBER OF cards: " + allPlayerCards.get(playerID).size());
		addActor(thisPlayer);
		// Add the card to the stage
		for (Card vCard : thisPlayer.getVCards()) {
			addActor(vCard);
		}

		// Add all other players
		// TODO: make the positions make sense regarding actual placing in the list
		int playerAmount = game.getPlayersLeft();
		System.out.println("NUMBER OF PLAYERS: " + Integer.toString(playerAmount));
		for (int id = 0; id < playerAmount; id++) {
			if (!(id == playerID)) {
				OtherPlayer otherPlayer = new OtherPlayer(id, playerAmount);
				otherPlayer.setY(400);
				otherPlayer.setX(id * 200);
				otherPlayers.add(otherPlayer);
				addActor(otherPlayer);
				//TODO: add position, do a top-row of OtherPlayers
			}
		}
	}

	//TODO: when a card is detected to be rmoved from hand, remove card from stage.
	// Deltas on game are checked here, called every frame by parent
	public void update() {
		
	}
}

