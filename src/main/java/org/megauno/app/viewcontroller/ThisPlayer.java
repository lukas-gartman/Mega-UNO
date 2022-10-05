package org.megauno.app.viewcontroller;

import java.util.ArrayList;
import java.util.List;

import org.megauno.app.model.Cards.ICard;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

import static org.megauno.app.utility.CardMethoodes.copyCards;

public class ThisPlayer extends Image {
	private int playerID;
	// Model cards
	private List<ICard> cards;
	// Visual cards
	private List<Card> vCards = new ArrayList<>();

	public ThisPlayer(int playerID, List<ICard> cards) {
		this.playerID = playerID;
		this.cards = cards;
		addCards(cards);
	}



	void addCards(List<ICard> cards){
		for(ICard card : cards){
			vCards.add(new Card(card));
		}

	}

	//Removes all the view cards from the player which are equal to the argumnet cards
	void removeCards(List<ICard> cards){
		List<Card> toRemove = new ArrayList<>();
		for(ICard card : cards){
			for(Card visualCard : vCards){
				if(visualCard.getCard().equals(card)){
					toRemove.add(visualCard);
				}
			}
		}
		for(Card visualCard: toRemove){
			vCards.remove(visualCard);
		}
	}

	public List<ICard> getCards() {
		return copyCards(cards);
	}

	@Override
 	public void draw(Batch batch, float parentAlpha) {
		for (int i = 0; i < vCards.size(); i++) {
			Card vCard = vCards.get(i);
			vCard.setX(i * 50);
			vCard.setY(100);
			vCard.draw(batch, parentAlpha);
		}
	}
}

