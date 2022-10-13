package org.megauno.app.viewcontroller;

import org.megauno.app.model.Cards.ICard;
import org.megauno.app.utility.Publisher.condition.ConPublisher;
import org.megauno.app.utility.Publisher.normal.Publisher;
import org.megauno.app.utility.Tuple;

import java.util.List;

public interface GamePublishers {
    Publisher<int> onNewPlayer();

    Publisher<ICard> onNewTopCard();

    ConPublisher<Tuple<int,List<ICard>>> onCardsAddedToId();

    ConPublisher<Tuple<int,List<ICard>>> onCardsRemovedAtId();
}
