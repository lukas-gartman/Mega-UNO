package org.megauno.app.viewcontroller;

import org.megauno.app.model.cards.ICard;
import org.megauno.app.model.game.Game;
import org.megauno.app.model.player.Player;
import org.megauno.app.utility.Publisher.ISubscribable;
import org.megauno.app.utility.Tuple;

import java.util.List;

/**
 * Implemented by model ({@link Game}) and used by the network to subscribe
 * to events.
 */
public interface GamePublishers {
<<<<<<< Updated upstream
	/**
	 * Emitted when a new player joins.
	 */
    IPublisher<Player> onNewPlayer();

	/**
	 * Emitted when a new top card is placed.
	 */
    IPublisher<ICard> onNewTopCard();

	/**
	 * Emitted when cards are added to a player.
	 */
    IPublisher<Tuple<Player, List<ICard>>> onCardsAddedToPlayer();

	/**
	 * Emitted when cards are removed from a player.
	 */
    IPublisher<Tuple<Player, List<ICard>>> onCardsRemovedByPlayer();
=======
    ISubscribable<Player> onNewPlayer();

    ISubscribable<ICard> onNewTopCard();

    ISubscribable<Tuple<Player, List<ICard>>> onCardsAddedToPlayer();

    ISubscribable<Tuple<Player, List<ICard>>> onCardsRemovedByPlayer();
>>>>>>> Stashed changes
}
