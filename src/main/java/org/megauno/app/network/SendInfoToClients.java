package org.megauno.app.network;

import org.megauno.app.model.Cards.ICard;
import org.megauno.app.network.Implementation.PlayersCards;

public interface SendInfoToClients {
    void currentPlayerNewId(int id);

    void newTopCardOfPile(ICard topCard);

    void playerWithIdAddedCards(PlayersCards pc);

    void playerWithIdRemovedCards(PlayersCards pc);

    void start();


}
