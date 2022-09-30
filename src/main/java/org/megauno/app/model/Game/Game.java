package org.megauno.app.model.Game;

import org.megauno.app.model.Cards.ICard;
import org.megauno.app.model.Deck;
import org.megauno.app.model.Pile;
import org.megauno.app.model.Player.Player;
import org.megauno.app.utility.ObserverPattern.Publisher;
import org.megauno.app.viewcontroller.IGame;

public class Game implements IGame {
	Publisher<Game> gameChangePublisher;
	PlayerCircle players;
	// ICard top;
	Deck deck;
	Pile discarded;

	public Game(Publisher<Game> publisher) {
		discarded = new Pile();
		this.gameChangePublisher = publisher;
	}

	public void reverse() {
		players.changeRotation();
	}

	public void play() {
		ICard top = discarded.getTop();
		Node current = players.getCurrent();
		ICard choice = players.currentMakeTurn(top);
		boolean currentHasOnlyOneCard = current.getPlayer().numOfCards() == 1;

		if (choice.canBePlayed(top)) {
			// card effects here ....

			// change currentPlayer to next in line depending on game direction and position
			// in circle:
			players.nextTurn();

			// discard played card
			discarded.discard(choice);

			// check if the player has run out of cards
			if (players.playerOutOfCards(current)) {
				// if the player only had one card, and never said uno,
				if (currentHasOnlyOneCard && !current.getPlayer().uno()) {
					// penalise: draw 3 cards.
					current.giveCardToPlayer(deck.drawCard());
					current.giveCardToPlayer(deck.drawCard());
					current.giveCardToPlayer(deck.drawCard());
				} else {
					// removes player
					players.playerFinished(current);
				}
			}

			gameChangePublisher.publish(this);
		} else {
			players.giveCardToPlayer(choice);
		}
	}

	@Override
	public Player getCurrent() {
		return players.getCurrent().getPlayer();
	}

	@Override
	public Player getPlayerWithId(int id) {
		for (Player p:getPlayers()) {
			if(p.getId() == id){
				return p;
			}
		}
		return null;
	}

	@Override
	public Player[] getPlayers() {
		return players.getPlayers();
	}
}
