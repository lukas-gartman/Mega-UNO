package org.megauno.app.network;

import org.megauno.app.model.cards.ICard;
import org.megauno.app.network.implementation.PlayersCards;

public interface SendInfoToClients {
    void currentPlayerNewId(int id);

    void newTopCardOfPile(ICard topCard);

    void playerWithIdAddedCards(PlayersCards pc);

    void playerWithIdRemovedCards(PlayersCards pc);

    void start();


}
