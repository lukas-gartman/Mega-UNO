package org.megauno.app.viewcontroller;

import org.megauno.app.network.IdCard;
import org.megauno.app.network.PlayersCards;
import org.megauno.app.utility.Publisher.condition.ConPublisher;
import org.megauno.app.utility.Publisher.normal.Publisher;

public interface ViewPublisher {
    Publisher<Integer> onNewPlayer();

    Publisher<IdCard> onNewTopCard();
    ConPublisher<PlayersCards> onCardsAddedToPlayer();

    ConPublisher<PlayersCards> onCardsRemovedByPlayer();

}
