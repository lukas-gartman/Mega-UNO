package org.megauno.app.viewcontroller;

import org.megauno.app.model.cards.ICard;
import org.megauno.app.model.player.Player;
import org.megauno.app.utility.Publisher.IPublisher;
import org.megauno.app.utility.Tuple;

import java.util.List;

/**
 * Implemented by network layer and used by view to recieve updates about
 * game.
 */
public interface GamePublishers {
    IPublisher<Player> onNewPlayer();

    IPublisher<ICard> onNewTopCard();

    IPublisher<Tuple<Player, List<ICard>>> onCardsAddedToPlayer();

    IPublisher<Tuple<Player, List<ICard>>> onCardsRemovedByPlayer();
}
