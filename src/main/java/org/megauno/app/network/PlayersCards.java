package org.megauno.app.network;

import java.util.List;

public class PlayersCards {
    private final int id;
    private final List<IdCard> cards;

    public PlayersCards(int id, List<IdCard> cards) {
        this.id = id;
        this.cards = cards;
    }

    public int getId() {
        return id;
    }

    public List<IdCard> getCards() {
        return cards;
    }
}
