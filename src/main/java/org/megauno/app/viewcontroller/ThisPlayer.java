package org.megauno.app.viewcontroller;

import java.util.ArrayList;
import java.util.List;

import org.megauno.app.model.Cards.ICard;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class ThisPlayer extends Image {
	private int playerID;
	// Model cards
	private List<ICard> cards;
	// Visual cards
	private List<Card> vCards = new ArrayList<>();

	public ThisPlayer(int playerID, List<ICard> cards) {
		this.playerID = playerID;
		this.cards = cards;
		for (int i = 0; i < cards.size(); i++) {
			ICard card = cards.get(i);
			Card vCard = new Card(card);
			vCard.setX(i * 50);
			vCard.setY(100);
			vCards.add(vCard);
		}
	}

	@Override
 	public void draw(Batch batch, float parentAlpha) {
		for (Card card : vCards) {
			card.draw(batch, parentAlpha);
		}
	}
}

