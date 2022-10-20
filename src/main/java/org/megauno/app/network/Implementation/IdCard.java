package org.megauno.app.network.Implementation;

import org.megauno.app.model.Cards.ICard;
import org.megauno.app.utility.Tuple;

public class IdCard {
    private final int id;
    private final ICard card;

    public IdCard(int id, ICard card) {
        this.id = id;
        this.card = card;
    }

    public int getId() {
        return id;
    }

    public ICard getCard() {
        return card;
    }
}
