package org.megauno.app.model;

import org.megauno.app.model.Cards.ICard;

import java.util.List;

public interface IDeck {
    ICard drawCard();

    List<ICard> dealHand(int sizeHand);
}
