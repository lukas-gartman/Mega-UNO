package org.megauno.app.network;

import org.megauno.app.model.cards.ICard;
import org.megauno.app.utility.Tuple;

import java.util.List;

public interface ClientReceivedInfo {
    int currentPlayerNewId();

    ICard newTopCardOfPile();

    Tuple<Integer, List<ICard>> playerWithIdAddedCards();

    Tuple<Integer, List<ICard>> playerWithIdRemovedCards();
}

