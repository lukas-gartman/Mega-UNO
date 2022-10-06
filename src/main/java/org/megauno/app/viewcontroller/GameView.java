package org.megauno.app.viewcontroller;

import com.badlogic.gdx.scenes.scene2d.Stage;

import java.util.ArrayList;
import java.util.List;

import org.megauno.app.model.Cards.ICard;
import org.megauno.app.model.Game.Game;
import org.megauno.app.model.Player.Player;

import static org.megauno.app.utility.CardMethoodes.cardsDifference;

// For now, GameView parses deltas from Game and calls the appropriate
public class GameView extends Stage {
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
		System.out.println("NUMBER OF cards: " + allPlayerCards.get(playerID).size());
		addActor(thisPlayer);


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
		thisPlayerHandCHnages();

		for (OtherPlayer op: otherPlayers) {
			otherPlayerHandChanges(op);
		}
	}

	//Deals with teh changes to the players hand
	private void thisPlayerHandCHnages(){
		Player player = game.getPlayerWithId(playerID);
		List<ICard> newCards = player.getCards();
		List<ICard> currentCards = thisPlayer.getCards();
		thisPlayer.addCards(cardsDifference(currentCards,newCards));
		thisPlayer.removeCards(cardsDifference(newCards,currentCards));
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

