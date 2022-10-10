package org.megauno.app.viewcontroller;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Stage;

import java.util.ArrayList;
import java.util.List;

import org.megauno.app.model.Cards.ICard;
import org.megauno.app.model.Game.Game;
import org.megauno.app.model.Player.Player;

import static org.megauno.app.utility.CardMethoodes.cardsDifference;

// For now, GameView parses deltas from Game and calls the appropriate
public class GameView implements IDrawable {
	private int playerID;
	private Game game;

	private ThisPlayer thisPlayer;
	private List<OtherPlayer> otherPlayers = new ArrayList<>();
	
	public GameView(Game game, int playerID) {
		this.game = game;
		// Add this view's player
		this.playerID = playerID;
		List<List<ICard>> allPlayerCards = game.getAllPlayerCards();   // All the different players' cards
		thisPlayer = new ThisPlayer(playerID, allPlayerCards.get(playerID), game, this);

		// Add all other players
		// TODO: make the positions make sense regarding actual placing in the list
		int playerAmount = game.getPlayersLeft();
		for (int id = 0; id < playerAmount; id++) {
			if (!(id == playerID)) {
				OtherPlayer otherPlayer = new OtherPlayer(id, playerAmount);
				otherPlayer.y = 400;
				otherPlayer.x = id * 200;
				otherPlayers.add(otherPlayer);
				//TODO: add position, do a top-row of OtherPlayers
			}
		}
	}

	//TODO: when a card is detected to be rmoved from hand, remove card from stage.
	// Deltas on game are checked here, called every frame by parent
	@Override
	public void draw(float delta, Batch batch) {
		thisPlayer.draw(delta, batch);
		for (OtherPlayer op: otherPlayers) {
			otherPlayerHandChanges(op);
			op.draw(delta, batch);
		}
	}

	private void otherPlayerHandChanges(OtherPlayer otherPlayer){
		Player[] players = game.getPlayers();
		for(int i = 0; i < players.length-1; i++){
			Player player = players[i];
			if(player.id== playerID){
				int newCards = player.numOfCards();
				int nCards = otherPlayer.getNrOfCard();
				if(nCards > newCards){
					otherPlayer.removeCards(nCards-newCards);
				}else if(nCards < newCards){
					otherPlayer.addCards(newCards-nCards);
				}
				nCards = newCards;
				break;
			}
		}
	}
}

