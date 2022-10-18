package org.megauno.app;

import com.badlogic.gdx.ApplicationAdapter;
import org.json.JSONArray;
import org.json.JSONObject;
import org.megauno.app.network.Client;
import org.megauno.app.network.IdCard;
import org.megauno.app.network.PlayersCards;
import org.megauno.app.viewcontroller.GameController;
import org.megauno.app.viewcontroller.Root;

import java.util.ArrayList;
import java.util.List;

public class ClientApplication extends ApplicationAdapter implements GameController {
    private Client client;
    private Root root = null;
    private int id;
    private int[] ids;


    public ClientApplication() {

    }

    @Override
    public void create() {
        this.root = new Root(id, ids, this);

    }

    @Override
    public void render() {
        if (root != null)
            root.draw();
    }

     void respondToJSON(JSONObject o) {
        String type = o.getString("Type");
        if (root != null) {
            switch (type) {
                case "AddCards":
                    System.out.println("Add cards");
                    root.onCardsAddedToPlayer().publish(parsePlayerCards(o));
                    break;
                case "RemoveCards":
                    root.onCardsRemovedByPlayer().publish(parsePlayerCards(o));
                    break;
                case "CurrentPlayerId":
                    root.onNewPlayer().publish(o.getInt("PlayerId"));
                    break;
                case "NewTopCard":
                    root.onNewTopCard().publish((IdCard)o.get("Card"));
                    break;
            }
        } else {
            if (type.equals("Start")) {
                List<Object> jsonArray = o.getJSONArray("OtherPlayers").toList();
                int[] otherPlayers = new int[jsonArray.size()];
                for (int i = 0; i < otherPlayers.length; i++) {
                    otherPlayers[i] = (int) jsonArray.get(i);
                }
                this.id = o.getInt("PlayerId");
                this.ids = otherPlayers;
            }
        }
    }

    private static PlayersCards parsePlayerCards(JSONObject o) {
        int playerID = o.getInt("PlayerId");

        JSONArray jsonArray = o.getJSONArray("Cards");
        List<IdCard> cards = new ArrayList<>();
        System.out.println("My id: " + playerID);
        for (Object object : jsonArray) {
            cards.add((IdCard) object);
            System.out.println(((IdCard) object).toString());
        }
        return new PlayersCards(playerID, cards);
    }

    @Override
    public void selectCard(int cardId) {
        client.sendJSON(new JSONObject().put("Type","SelectCard").put("CardId",cardId));
    }

    @Override
    public void unSelectCard(int cardId) {
        client.sendJSON(new JSONObject().put("Type","UnSelectCard").put("CardId",cardId));
    }

    @Override
    public void commenceForth() {
        client.sendJSON(new JSONObject().put("Type","CommenceForth"));
    }

    @Override
    public void sayUno() {
        client.sendJSON(new JSONObject().put("Type","Uno"));
    }
}
