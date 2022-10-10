package org.megauno.app.viewcontroller;

import java.util.ArrayList;
import java.util.List;

import org.megauno.app.model.Cards.ICard;
import org.megauno.app.model.Game.Game;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import static org.megauno.app.utility.CardMethoodes.copyCards;

public class ThisPlayer implements IDrawable {
	private int playerID;
	// Visual cards
	private List<Card> vCards = new ArrayList<>();
	// Game
	private Game game;

	private GameView gv;

	public ThisPlayer(int playerID, List<ICard> cards, Game game, GameView gv) {
		this.playerID = playerID;
		this.game = game;
		this.gv = gv;
		addCards(cards);
	}

	void addCards(List<ICard> cards) {
		for (int i = 0; i < cards.size(); i++) {
			ICard card = cards.get(i);
			// Note, cardID is just the index in the list of cards
			Card vCard = new Card(card, game, playerID, i);
			vCard.x = i * 50;
			vCard.y = 100;
			// Add controller for card
			// vCard.addListener(new CardListener(i, game));
			vCards.add(vCard);
		}
	}

	// Removes all the view cards from the player which are equal to the argumnet
	// cards
	void removeCards(List<ICard> cards) {
		List<Card> toRemove = new ArrayList<>();
		for (ICard card : cards) {
			for (Card visualCard : vCards) {
				if (visualCard.getCard().equals(card)) {
					toRemove.add(visualCard);
				}
			}
		}
		for (Card visualCard : toRemove) {
			//TODO: update id:s on cards when cards are moved around/removed/added
			vCards.remove(visualCard);
		}
	}

	// For now, card's positions don't get updated when a player's hand changes,
	// they are just drawn in order in the list every frame
	@Override
	public void draw(float delta, Batch batch) {
		for (int i = 0; i < vCards.size(); i++) {
			Card c = vCards.get(i);
			c.x = i * 50;
			c.y = 100;
			c.draw(delta, batch);
		}
	}
}
