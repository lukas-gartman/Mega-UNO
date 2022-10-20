package org.megauno.app.viewcontroller;

import org.megauno.app.model.cards.ICard;
import org.megauno.app.network.implementation.PlayersCards;
import org.megauno.app.utility.Publisher.IPublisher;
import org.megauno.app.utility.Publisher.condition.IConditionPublisher;

public interface ViewPublisher {
    IPublisher<Integer> onNewPlayer();

    IPublisher<ICard> onNewTopCard();

    IConditionPublisher<PlayersCards> onCardsAddedToPlayer();

    IConditionPublisher<PlayersCards> onCardsRemovedByPlayer();

}
