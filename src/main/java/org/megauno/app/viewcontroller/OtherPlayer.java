package org.megauno.app.viewcontroller;

import org.megauno.app.model.Player.Player;
import org.megauno.app.utility.ObserverPattern.Subscriber;
import org.megauno.app.viewcontroller.DataFetching.FontLoader;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import org.megauno.app.viewcontroller.distributers.otherPlayerHandChanges.OtherPlayersNrOfCards;

// Graphical element to represent another player
public class OtherPlayer extends Image implements Subscriber<IGame> {
	private int playerId;
	private int nCards;
	private WilliamIGame game;

	static BitmapFont font = new FontLoader("assets/").getDataFromPath("assets/minecraft.fnt");

	public OtherPlayer(int playerId, int nCards) {
		this.playerId = playerId;
		this.nCards = nCards;
		updateCardsShown();
	}

	// At the moment, have a text indicator
	private void updateCardsShown() {
		
	}

	@Override
 	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
		// Draw text as well
		batch.begin();
		font.draw(batch, Integer.toString(nCards), getX(), getY());
		batch.end();
	}

	@Override
	public void delivery(IGame game) {
		handChanges(game);
	}

	private void handChanges(IGame game){
		Player[] players = game.getPlayers();
		for(int i = 0; i < players.length-1; i++){
			Player player = players[i];
			if(player.getId() == playerId){
				int newCards = player.numOfCards();
				if(nCards > newCards){
					removeCards(nCards-newCards);
				}else if(nCards < newCards){
					addCards(newCards-nCards);
				}
				nCards = newCards;
				break;
			}
		}
	}

	private void addCards(int newCards){

	}
	private void removeCards(int removedCards){

	}
}
