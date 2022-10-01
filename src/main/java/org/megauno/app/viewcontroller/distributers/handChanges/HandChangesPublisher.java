package org.megauno.app.viewcontroller.distributers.handChanges;

import org.megauno.app.model.Cards.ICard;
import org.megauno.app.model.Player.Player;
import org.megauno.app.utility.ObserverPattern.Publisher;
import org.megauno.app.utility.ObserverPattern.Subscriber;
import org.megauno.app.viewcontroller.IGame;

import java.util.List;



public class HandChangesPublisher implements Subscriber<IGame> {
    private List<ICard> oldCards;
    private int playerId;
    private Publisher<HandChanges> publisher;

    public HandChangesPublisher(Publisher<HandChanges> publisher, Player playerAtStart) {
        this.oldCards = playerAtStart.getCards();
        this.playerId = playerAtStart.getId();
        this.publisher = publisher;
    }


    @Override
    public void delivery(IGame game) {
        Player player = game.getPlayerWithId(playerId);
        List<ICard> cards = player.getCards();
        //publisher.publish(new HandChanges(cardsDelta(oldCards,cards),cardsDelta(cards,oldCards)));
    }
}
