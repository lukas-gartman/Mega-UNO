package org.megauno.app.network;

import org.megauno.app.model.Cards.ICard;
import org.megauno.app.utility.Tuple;

import java.util.List;

public interface SendInfoToClients {
    void currentPlayerNewId(int id);

    void newTopCardOfPile(ICard topCard);

    void playerWithIdAddedCards(PlayersCards pc);

    void playerWithIdRemovedCards(PlayersCards pc);

    void playerIdsAtStart(int playerId,int[] ids);


}
