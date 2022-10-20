package org.megauno.app.viewcontroller;

import org.megauno.app.model.Cards.ICard;
import org.megauno.app.model.Player.Player;
import org.megauno.app.utility.Publisher.IPublisher;
import org.megauno.app.utility.Tuple;

import java.util.List;

public interface GamePublishers {
    IPublisher<Player> onNewPlayer();

    IPublisher<ICard> onNewTopCard();

    IPublisher<Tuple<Player, List<ICard>>> onCardsAddedToPlayer();

    IPublisher<Tuple<Player, List<ICard>>> onCardsRemovedByPlayer();
}
