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
import java.util.Scanner;

public class ClientApplication extends ApplicationAdapter implements GameController {
    private final Client client;
    private Root root = null;

    public ClientApplication() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Nickname: ");
        String nickname = scanner.nextLine();
        System.out.print("Host name: ");
        String hostName = scanner.nextLine();
        System.out.print("Port (0-65535): ");
        int port = scanner.nextInt();
        this.client = new Client(nickname, hostName, port, this::respondToJSON);
    }

    void respondToJSON(JSONObject o){
        String type = o.getString("Type");
        if (root != null) {
            switch (type) {
                case "AddCards":
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
            }
        } else {
            if (type.equals("Start")) {
                List<Object> jsonArray = o.getJSONArray("OtherPlayers").toList();
                int[] otherPlayers = new int[jsonArray.size()];
                for (int i = 0; i < otherPlayers.length; i++) {
                    otherPlayers[i] = (int) jsonArray.get(i);
                }
                this.root = new Root(o.getInt("PlayerId"), otherPlayers, this);
            }
        }
    }

    private static PlayersCards parsePlayerCards(JSONObject o) {
        int playerID = o.getInt("PlayerId");
        JSONArray jsonArray = o.getJSONArray("Cards");
        List<IdCard> cards = new ArrayList<>();
        for (Object object : jsonArray) {
            cards.add((IdCard) object);
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
