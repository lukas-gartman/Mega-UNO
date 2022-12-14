package org.megauno.app.viewcontroller;

import org.megauno.app.model.cards.ICard;
import org.megauno.app.network.implementation.PlayersCards;
import org.megauno.app.utility.Publisher.ISubscribable;
import org.megauno.app.utility.Publisher.condition.IConditionPublisher;

/**
 * Implemented by network layer to emmit events to the view.
 */
public interface ViewPublisher {

	/**
	 * Emitted when new player joins.
	 */
    ISubscribable<Integer> onNewPlayer();
	/**
	 * Emitted when there is a new card at the top of the pile.
	 */
    ISubscribable<ICard> onNewTopCard();

	/**
	 * Emitted when cards are added to player.
	 */
    IConditionPublisher<PlayersCards> onCardsAddedToPlayer();

	/**
	 * Emitted when cards are removed from player.
	 */
    IConditionPublisher<PlayersCards> onCardsRemovedByPlayer();

}
