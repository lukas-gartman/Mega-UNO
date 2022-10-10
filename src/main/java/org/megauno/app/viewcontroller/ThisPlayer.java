package org.megauno.app.viewcontroller;

import java.util.ArrayList;
import java.util.List;

import org.megauno.app.model.Cards.ICard;
import org.megauno.app.model.Game.Game;
import org.megauno.app.model.Player.Player;

import com.badlogic.gdx.graphics.g2d.Batch;

import static org.megauno.app.utility.CardMethoodes.cardsDifference;

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

	// Rethink this
	public List<ICard> getCards() {
        List<ICard> cards = new ArrayList<>();
        for(Card vCard:vCards){
            cards.add(vCard.getCard());
        }
        return copyCards(cards);
    }

	//Deals with the changes to the players hand
	void thisPlayerHandCHnages() {
		Player player = game.getPlayerWithId(playerID);
		List<ICard> newCards = player.getCards();
		List<ICard> currentCards = getCards();
		addCards(cardsDifference(currentCards, newCards));
		removeCards(cardsDifference(newCards, currentCards));
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
