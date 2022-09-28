package org.megauno.app.viewcontroller;

import org.megauno.app.model.Cards.CardType;
import org.megauno.app.model.Cards.Color;
import org.megauno.app.utility.ObserverPattern.Subscriber;

// Interface of the currently played game used by the view
// If the current game is played on "this" computer, Game is provided
// under this interface, otherwise it will be a class handling network
// trafic.
// Players are identified by their playerID, an index from 0 to nPlayer-1
// The Controller-object on the server representing one player should
// determine if it actually returns, say the player's cards, because
// one client might ask for a player's cards which does not belong to them.
// NOTE: in the context of networking, any querry would result in a
// network request, try avoiding those if the information is available in
// getInitialState().
// TODO: add an interface for controllers
public interface IGame {
	GameInitialState getInitialState();

	// Event registration
	
	// Sent with which player recieved a card, and which position in hand it now has
	void registerOnCardAddedToThisPlayerHand(Subscriber<ThisPlayerCardsAdded> subscriber);
	void registerOnCardAddedToOtherPlayerHand(Subscriber<OtherPlayerCardsAdded> subscriber);

	// Card type in these messages
	public class Card {
		// This will be maped to images
		public CardType cardType;
		public Color color;
		// Nullable
		public Integer number;
	}

	// Data classes for subscribers, might be benificial to wrapp a single
	// type, so there are no confusions of witch interface you are implementing
	// with a given method.

	public class ThisPlayerCardsAdded {
		public Card[] cards;
	}

	public class OtherPlayerCardsAdded {
		public int playerID;
		public int numberOfNewCards;  // Of course does not show the cards added
	}

	// Used by the view to get information that does not change during a game
	// (say a player's name) and information such as the starting number
	// of cards for each player.
	public class GameInitialState {
		public int numberOfPlayers;
		public String[] playerNames;
		public int[] playerStartingCardAmount;   // Number of cards for each player
		// This only needs to be one array, since you can't look at other
		// players' cards.
		public Card[] playerCards;
	}
}

