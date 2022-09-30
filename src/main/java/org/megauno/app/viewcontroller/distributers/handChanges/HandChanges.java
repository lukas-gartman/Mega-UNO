package org.megauno.app.viewcontroller.distributers.handChanges;

import org.megauno.app.model.Cards.ICard;

import java.util.List;

public class HandChanges {
    public HandChanges(List<ICard> addedCards, List<ICard> removedCards) {
        this.addedCards = addedCards;
        this.removedCards = removedCards;
    }

    public List<ICard> addedCards;
    public List<ICard> removedCards;

}
