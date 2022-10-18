package org.megauno.app.viewcontroller.players;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;

import java.util.ArrayList;
import java.util.List;

import org.megauno.app.ClientApplication;
import org.megauno.app.model.Cards.ICard;
import org.megauno.app.viewcontroller.Clickable;
import org.megauno.app.viewcontroller.GameController;
import org.megauno.app.viewcontroller.ViewPublisher;
import org.megauno.app.viewcontroller.datafetching.IDrawable;
import org.megauno.app.viewcontroller.players.otherplayers.OtherPlayer;
import org.megauno.app.viewcontroller.players.thisPlayer.Card;
import org.megauno.app.viewcontroller.players.thisPlayer.ThisPlayer;

// For now, GameView parses deltas from Game and calls the appropriate
public class GameView implements IDrawable {
	private int playerID;

	private Card top;
	private ThisPlayer thisPlayer;
	private List<OtherPlayer> otherPlayers = new ArrayList<>();
	private EndTurnButton endTurnButton;
	private GameController gameController;
	
	public GameView(int playerID, int[] otherPlayersIds, ViewPublisher publishers, GameController gameController) {
		// Add this view's player
		this.playerID = playerID;
		this.gameController = gameController;

		thisPlayer = new ThisPlayer(playerID, publishers,gameController);

		// Add all other players
		// TODO: make the positions make sense regarding actual placing in the list
		int offset = 0;
		for (int i: otherPlayersIds) {
			OtherPlayer otherPlayer = new OtherPlayer(i, publishers);
			otherPlayer.y = 400;
			otherPlayer.x = offset * 200;
			otherPlayers.add(otherPlayer);
			//TODO: add position, do a top-row of OtherPlayers
			offset++;
		}

		endTurnButton = new EndTurnButton(200, 200);


		publishers.onNewTopCard().addSubscriber(
				(np) -> updateTopCard(np.getCard(),np.getId())
		);
	}

	public int getPlayerID(){
		return playerID;
	}

	private void updateTopCard(ICard newTop, int id){
		top = new Card(newTop,id, gameController);
		top.x = 300;
		top.y = 250;

	}

	//TODO: when a card is detected to be rmoved from hand, remove card from stage.
	// Deltas on game are checked here, called every frame by parent
	@Override
	public void draw(float delta, Batch batch) {
		thisPlayer.draw(delta, batch);

		if(top != null)
			top.draw(delta, batch);

		for (OtherPlayer op : otherPlayers) {
			op.draw(delta, batch);
		}

		// Draw end turn button
		endTurnButton.draw(delta, batch);
	}



	class EndTurnButton implements IDrawable {
		public float x;
		public float y;

		private Clickable clickable;

		private Sprite sprite;

		public EndTurnButton(float x, float y) {

			sprite = ClientApplication.spriteFetcher.tryGetDataUnSafe("Tomte.png");
			this.x = x;
			this.y = y;

			clickable = new Clickable(sprite.getWidth(), sprite.getHeight());
		}

		@Override
		public void draw(float delta, Batch batch) {
			if (clickable.wasClicked(x, y)) {
				gameController.commenceForth();
			}

			batch.draw(sprite, x, y);
		}
	}
}



