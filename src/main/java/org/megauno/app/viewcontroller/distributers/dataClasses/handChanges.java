package org.megauno.app.viewcontroller.distributers.dataClasses;

import org.megauno.app.model.Cards.ICard;

import java.util.List;

public class handChanges {
    public handChanges(List<ICard> addedCards, List<ICard> removedCards) {
        this.addedCards = addedCards;
        this.removedCards = removedCards;
    }

    public List<ICard> addedCards;
    public List<ICard> removedCards;

}
