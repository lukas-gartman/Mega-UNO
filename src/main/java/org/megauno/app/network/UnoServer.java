package org.megauno.app.network;

import org.json.JSONObject;
import org.megauno.app.model.Cards.ICard;
import org.megauno.app.utility.Publisher.normal.Publisher;
import org.megauno.app.utility.Tuple;

import java.util.Set;

public class UnoServer extends Server implements SendInfoToClients{

    public UnoServer(int port, Publisher<Tuple<ClientHandler, Integer>> publisher,JSONReader jsonReader) throws IllegalAccessException {
        super(port, publisher, jsonReader);
    }

    @Override
    public void currentPlayerNewId(int id) {
        broadcast(new JSONObject().put("Type","CurrentPlayerId").put("PlayerId",id));
    }

    @Override
    public void newTopCardOfPile(ICard topCard) {
        JSONObject card = new JSONObject(topCard);
        broadcast(new JSONObject().put("Type","NewTopCard").put("Card",card));
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
    public void start() {
        Set<Integer> ids = getClientHandlers().getRightKeys();
        for (Integer id: ids) {
            int[] otherplayers = new int[ids.size()-1];
            int i = 0;
            for (int innerId :ids) {
                if(id != innerId){
                    otherplayers[i] = innerId;
                    i++;
                }
            }
            JSONObject json = new JSONObject();
            json.put("Type","Start");
            json.put("PlayerId",id);
            json.put("OtherPlayers",otherplayers);
            getClientHandlers().getLeft(id).send(json);
        }
    }

}