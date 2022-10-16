package org.megauno.app.viewcontroller;
import org.megauno.app.network.IdCard;
import org.megauno.app.network.PlayersCards;
import org.megauno.app.utility.Publisher.IPublisher;
import org.megauno.app.utility.Publisher.condition.IConPublisher;

public interface ViewPublisher {
    IPublisher<Integer> onNewPlayer();

    IPublisher<IdCard> onNewTopCard();

    IConPublisher<PlayersCards> onCardsAddedToPlayer();

    IConPublisher<PlayersCards> onCardsRemovedByPlayer();

}
