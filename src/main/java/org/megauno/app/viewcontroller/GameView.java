package org.megauno.app.viewcontroller;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import org.megauno.app.model.Cards.ICard;
import org.megauno.app.model.Game.Game;
import org.megauno.app.model.Player.Player;
import org.megauno.app.utility.dataFetching.PathDataFetcher;
import org.megauno.app.viewcontroller.datafetching.SpriteLoader;

import static org.megauno.app.utility.CardMethoodes.cardsDifference;

// For now, GameView parses deltas from Game and calls the appropriate
public class GameView implements IDrawable {
	private int playerID;
	private Game game;
	private Card top;
	private ThisPlayer thisPlayer;
	private List<OtherPlayer> otherPlayers = new ArrayList<>();
	private EndTurnButton endTurnButton;
	
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

		endTurnButton = new EndTurnButton(200, 200);
	}

	public int getPlayerID(){
		return playerID;
	}

	//TODO: when a card is detected to be rmoved from hand, remove card from stage.
	// Deltas on game are checked here, called every frame by parent
	@Override
	public void draw(float delta, Batch batch) {
		thisPlayer.draw(delta, batch);

		// TODO: fix ID
		top = new Card(game.getTopCard(), game, playerID, -1);
		top.x = 300;
		top.y = 250;
		top.draw(delta, batch);

		//System.out.println(game.getPlayerWithId(game.getCurrentPlayer()).numOfCards());
		thisPlayer.thisPlayerHandCHnages();

		for (OtherPlayer op : otherPlayers) {
			otherPlayerHandChanges(op);
			op.draw(delta, batch);
		}

		// Draw end turn button
		endTurnButton.draw(delta, batch);
	}

	private void otherPlayerHandChanges (OtherPlayer otherPlayer){

		int newCards = game.getPlayerWithId(otherPlayer.getPlayerID()).numOfCards();
		//System.out.println(otherPlayer.getPlayerID() + " has " + newCards);

		int nCards = otherPlayer.getNrOfCard();
		if (nCards > newCards) {
			otherPlayer.removeCards(nCards - newCards);
		} else if (nCards < newCards) {
			otherPlayer.addCards(newCards - nCards);
		}
	}

	// TODO: YES
	class EndTurnListener extends ClickListener {
		@Override
		public void clicked(InputEvent event, float x, float y) {
			System.out.println("placed card");
			//System.out.println(game.getPlayerWithId(playerID).getSelectedCards().get(0).toString());
			game.commence_forth = true;
		}
	}

	class EndTurnButton implements IDrawable {
		public float x;
		public float y;

		private Clickable clickable;

		static private Sprite sprite = new SpriteLoader().retrieveData("assets/Tomte.png");

		public EndTurnButton(float x, float y) {
			this.x = x;
			this.y = y;

			clickable = new Clickable(sprite.getWidth(), sprite.getHeight());
		}

		@Override
		public void draw(float delta, Batch batch) {
			if (clickable.wasClicked(x, y)) {
				game.commence_forth = true;
			}

			batch.draw(sprite, x, y);
		}
	}
}



