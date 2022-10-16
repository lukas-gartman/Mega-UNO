package org.megauno.app.network;

import org.json.JSONObject;
import org.megauno.app.model.Cards.ICard;

public class UnoServer extends Server implements SendInfoToClients{

    public UnoServer(int port,JSONReader jsonReader) {
        super(port,jsonReader);
    }

    @Override
    public void currentPlayerNewId(int id) {
        broadcast(new JSONObject().put("Type","CurrentPlayerId").put("PlayerId",id));
    }

    @Override
    public void newTopCardOfPile(ICard topCard) {
        broadcast(new JSONObject().put("Type","NewTopCard").put("Card",topCard));
    }

    @Override
    public void playerWithIdAddedCards(PlayersCards pc) {
        JSONObject object = new JSONObject();
        object.put("Type","AddCards");
        object.put("PlayerId",pc.getId());
        object.put("Cards",pc.getCards());
        broadcast(object);
    }

    @Override
    public void playerWithIdRemovedCards(PlayersCards pc) {
        JSONObject object = new JSONObject();
        object.put("Type","RemoveCards");
        object.put("PlayerId",pc.getId());
        object.put("Cards",pc.getCards());
        broadcast(object);
    }


    @Override
    public void playerIdsAtStart(int playerId, int[] ids) {
        broadcast(new JSONObject().put("Type","Start").
                put("PlayerId",playerId).
                put("OtherPlayers",ids));
    }
}
