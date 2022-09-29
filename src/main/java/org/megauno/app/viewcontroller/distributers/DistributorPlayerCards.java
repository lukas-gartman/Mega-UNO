package org.megauno.app.viewcontroller.distributers;

import org.megauno.app.model.Cards.ICard;
import org.megauno.app.model.Player.Player;
import org.megauno.app.utility.ObserverPattern.Distributor;
import org.megauno.app.utility.ObserverPattern.Publisher;
import org.megauno.app.viewcontroller.IGame;
import org.megauno.app.viewcontroller.distributers.dataClasses.handChanges;

import java.util.List;

import static org.megauno.app.utility.CardMethodes.cardsDelta;

public class DistributorPlayerCards extends Distributor<IGame, handChanges> {
    private List<ICard> oldCards;
    private int playerId;

    public DistributorPlayerCards(Publisher<handChanges> publisher, Player playerAtStart) {
        super(publisher);
        this.oldCards = playerAtStart.getCards();
        this.playerId = playerAtStart.getId();
    }

    @Override
    public handChanges extractStory(IGame game) {
        Player player = game.getPlayerWithId(playerId);
        List<ICard> cards = player.getCards();
        return new handChanges(cardsDelta(oldCards,cards),cardsDelta(cards,oldCards));
    }

}
