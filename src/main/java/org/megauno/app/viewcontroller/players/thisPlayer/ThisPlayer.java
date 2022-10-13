package org.megauno.app.viewcontroller.players.thisPlayer;

import java.util.ArrayList;
import java.util.List;

import org.megauno.app.model.Cards.ICard;

import com.badlogic.gdx.graphics.g2d.Batch;
import org.megauno.app.viewcontroller.GamePublishers;
import org.megauno.app.viewcontroller.datafetching.IDrawable;
import org.megauno.app.viewcontroller.players.GameView;

import static org.megauno.app.utility.CardMethoodes.copyCards;

public class ThisPlayer implements IDrawable {
	private int playerID;
	// Visual cards
	private List<Card> vCards = new ArrayList<>();
	// Game

	private GameView gv;

	public ThisPlayer(int playerID,  GamePublishers publishers) {
		this.playerID = playerID;

		publishers.onCardsAddedToId().addSubscriberWithCondition(
				(np) -> addCards(np.r),
				(np) -> np.l == playerID
		);

		publishers.onCardsRemovedAtId().addSubscriberWithCondition(
				(np) -> removeCards(np.r),
				(np) -> np.l == playerID
		);
	}

	// Rethink this
	public List<ICard> getCards() {
        List<ICard> cards = new ArrayList<>();
        for(Card vCard:vCards){
            cards.add(vCard.getCard());
        }
        return copyCards(cards);
    }



	void addCards(List<ICard> cards) {
		for (int i = 0; i < cards.size(); i++) {
			ICard card = cards.get(i);
			// Note, cardID is just the index in the list of cards
			Card vCard = new Card(card);
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
