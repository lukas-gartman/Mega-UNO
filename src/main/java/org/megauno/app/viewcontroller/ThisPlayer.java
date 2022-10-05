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

public class ThisPlayer extends Image {
	private int playerID;
	// Model cards
	private List<ICard> cards;
	// Visual cards
	private List<Card> vCards = new ArrayList<>();

	public ThisPlayer(int playerID, List<ICard> cards, Game game) {
		this.playerID = playerID;
		this.cards = cards;
		for (int i = 0; i < cards.size(); i++) {
			ICard card = cards.get(i);
			Card vCard = new Card(card);
			vCard.setX(i * 50);
			vCard.setY(100);
			// Add controller for card
			vCard.addListener(new CardListener(i, game));
			vCards.add(vCard);
		}
	}

	public List<Card> getVCards() {
		return vCards;
	}

	// Controller attached to cards
	public class CardListener extends ClickListener {
		private int cardID;
		private Game game;
		public CardListener(int cardID, Game game) {
			this.cardID = cardID;
			this.game = game;
		}
		@Override
		public void clicked(InputEvent event, float x, float y) {
			System.out.println("Clicked card with ID: " + Integer.toString(cardID));
			// Flip card selection in model and visually
			//game.choices[cardID] = !game.choices[cardID];
			vCards.get(cardID).selected = !vCards.get(cardID).selected;
		}
	}
}
