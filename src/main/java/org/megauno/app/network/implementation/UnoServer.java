package org.megauno.app.network.implementation;

import org.json.JSONObject;
import org.megauno.app.model.cards.ICard;
import org.megauno.app.network.IClientHandler;
import org.megauno.app.network.JSONReader;
import org.megauno.app.network.SendInfoToClients;
import org.megauno.app.utility.Publisher.normal.Publisher;
import org.megauno.app.utility.Tuple;

import java.net.ConnectException;
import java.util.HashMap;
import java.util.Set;

public class UnoServer extends Server implements SendInfoToClients {
    public UnoServer(int port, Publisher<Tuple<IClientHandler, Integer>> publisher, JSONReader jsonReader) throws ConnectException {
        super(port, publisher, jsonReader);
    }

    @Override
    public void currentPlayerNewId(int id) {
        broadcast(new JSONObject().put("Type", "CurrentPlayerId").put("PlayerId", id));
    }

    @Override
    public void newTopCardOfPile(ICard topCard) {
        JSONObject card = new JSONObject(topCard);
        broadcast(new JSONObject().put("Type", "NewTopCard").put("Card", card));
    }

    @Override
    public void playerWithIdAddedCards(PlayersCards pc) {
        JSONObject object = new JSONObject();
        object.put("Type", "AddCards");
        object.put("PlayerId", pc.getId());
        object.put("Cards", pc.getCards());
        broadcast(object);
    }

    @Override
    public void playerWithIdRemovedCards(PlayersCards pc) {
        JSONObject object = new JSONObject();
        object.put("Type", "RemoveCards");
        object.put("PlayerId", pc.getId());
        object.put("Cards", pc.getCards());
        broadcast(object);
    }


    @Override
    public void start(HashMap<Integer, String> playersIdWithNickname) {
        Set<Integer> ids = getClientHandlers().getRightKeys();
        for (int id : ids) {
            HashMap<Integer, String> otherPlayers = new HashMap<>();
            for (int innerId : ids) {
                if (id != innerId) {
                    otherPlayers.put(innerId, playersIdWithNickname.get(innerId));
                }
            }
            JSONObject json = new JSONObject();
            json.put("Type", "Start");
            json.put("PlayerId", id);
            json.put("OtherPlayers", new JSONObject(otherPlayers));
            getClientHandlers().getLeft(id).send(json);
        }
    }

}
