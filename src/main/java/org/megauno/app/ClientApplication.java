package org.megauno.app;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import org.json.JSONArray;
import org.json.JSONObject;
import org.megauno.app.model.Cards.AbstractCard;
import org.megauno.app.model.Cards.CardType;
import org.megauno.app.model.Cards.Color;
import org.megauno.app.model.Cards.ICard;
import org.megauno.app.model.Cards.Impl.ActionCard;
import org.megauno.app.model.Cards.Impl.NumberCard;
import org.megauno.app.model.Game.Actions.ReverseAction;
import org.megauno.app.model.Game.Actions.TakeFourAction;
import org.megauno.app.model.Game.Actions.TakeTwoAction;
import org.megauno.app.model.Game.Actions.WildCardAction;
import org.megauno.app.network.Client;
import org.megauno.app.network.IdCard;
import org.megauno.app.network.PlayersCards;
import org.megauno.app.utility.Publisher.condition.ConPublisher;
import org.megauno.app.utility.Publisher.dataFetching.DataFetcher;
import org.megauno.app.utility.Publisher.dataFetching.PathDataFetcher;
import org.megauno.app.utility.Publisher.normal.Publisher;
import org.megauno.app.viewcontroller.GameController;
import org.megauno.app.viewcontroller.Root;
import org.megauno.app.viewcontroller.ViewPublisher;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class ClientApplication extends ApplicationAdapter implements GameController, ViewPublisher {
    private Publisher<Integer> onNewPlayer = new Publisher<>();
    private Publisher<IdCard> onNewTopCard = new Publisher<>();
    private ConPublisher<PlayersCards> onCardsAddedToPlayer = new ConPublisher<>();
    private ConPublisher<PlayersCards> onCardsRemovedByPlayer = new ConPublisher<>();
    private Client client;
    private Root root = null;
    public static DataFetcher<String, Sprite> spriteFetcher;
    public static DataFetcher<String, BitmapFont> bitmapFontFetcher;
    public Texture t = new Texture("assets/Tomte.png");



    public ClientApplication() {

    }

    @Override
    public void create() {

        Scanner scanner = new Scanner(System.in);
        System.out.print("Nickname: ");
        //String nickname = scanner.nextLine();
        System.out.print("Host name: ");
        //String hostName = scanner.nextLine();
        System.out.print("Port (0-65535): ");
        //int port = scanner.nextInt();
        client = new Client("dude","localhost",1337,o->
        {
            if(o.getString("Type").equals("Start")){
                List<Object> jsonArray = o.getJSONArray("OtherPlayers").toList();
                int[] otherPlayers = new int[jsonArray.size()];
                for (int i = 0; i < otherPlayers.length; i++) {
                    otherPlayers[i] = (int) jsonArray.get(i);
                }
                root = new Root(o.getInt("PlayerId"),otherPlayers,this,this);
            }else {
                respondToJSON(o);
            }
        });
        spriteFetcher = new PathDataFetcher<>(
                (key) ->  {
                    return new Sprite(new Texture(key));
                },"./assets/");
        bitmapFontFetcher = new PathDataFetcher<BitmapFont>(
                (key) ->  {
                    return new BitmapFont(Gdx.files.internal(key));
                },"./assets/");
    }

    @Override
    public void render() {
        if (root != null)
            root.draw();
    }

     void respondToJSON(JSONObject o) {
         String type = o.getString("Type");

         if (root != null) {

             System.out.println(type.equals("AddCards"));

             if (type.equals("AddCards")) {
                 System.out.println("c");
             }
             if (type.equals("Start")) {
                 System.out.println("c");
             }


             switch (type) {
                 case "AddCards":
                     onCardsAddedToPlayer.publish(parsePlayerCards(o));
                     break;
                 case "RemoveCards":
                     onCardsRemovedByPlayer.publish(parsePlayerCards(o));
                     break;
                 case "CurrentPlayerId":
                     onNewPlayer.publish(o.getInt("PlayerId"));
                     break;
                 case "NewTopCard":
                     onNewTopCard().publish((IdCard) o.get("Card"));
                     break;
             }
         } else {
             if (type.equals("Start")) {
                 List<Object> jsonArray = o.getJSONArray("OtherPlayers").toList();
                 int[] otherPlayers = new int[jsonArray.size()];
                 for (int i = 0; i < otherPlayers.length; i++) {
                     otherPlayers[i] = (int) jsonArray.get(i);
                 }
                 root = new Root(o.getInt("PlayerId"),otherPlayers,this,this);
             }

         }
     }

    private static PlayersCards parsePlayerCards(JSONObject o) {
        int playerID = o.getInt("PlayerId");

        JSONArray jsonArray = o.getJSONArray("Cards");
        List<IdCard> cards = new ArrayList<>();
        System.out.println("My id: " + playerID + jsonArray.toString());
        for (Object object : jsonArray) {
            cards.add(idCardMaker(object));
        }
        return new PlayersCards(playerID, cards);
    }

    static private IdCard idCardMaker(Object object){
        JSONObject jsonObject = ((JSONObject )object);
        int cardId = jsonObject.getInt("id");
        jsonObject = jsonObject.getJSONObject("card");
        Color c = Color.NONE;
        switch (jsonObject.getString("color")){
            case "BLUE":{
                c = Color.BLUE;
                break;
            }
            case "GREEN":{
                c = Color.GREEN;
                break;
            }
            case "RED":{
                c = Color.RED;
                break;
            }
            case "YELLOW":{
                c = Color.YELLOW;
                break;
            }
            case "NONE":{
                c = Color.NONE;
                break;
            }
        }
        ICard icard = new NumberCard(Color.NONE,2);
        switch (jsonObject.getString("type")){
            case "NUMBERCARD": {
                icard = new NumberCard(c,(int)jsonObject.get("number"));
                break;
            }
            case "REVERSECARD": {
                icard = new ActionCard(new ReverseAction(),c,CardType.REVERSECARD);
                break;
            }
            case "WILDCARD":{
                icard = new ActionCard(new WildCardAction(),c,CardType.WILDCARD);
                break;
            }
            case "TAKETWO":{
                icard = new ActionCard(new TakeTwoAction(),c,CardType.TAKETWO);
                break;
            }
            case "TAKEFOUR":{
                icard = new ActionCard(new TakeFourAction(),c,CardType.TAKEFOUR);
                break;
            }
        }
        return new IdCard(cardId,icard);
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

    @Override
    public Publisher<Integer> onNewPlayer() {
        return onNewPlayer;
    }

    @Override
    public Publisher<IdCard> onNewTopCard() {
        return onNewTopCard;
    }

    @Override
    public ConPublisher<PlayersCards> onCardsAddedToPlayer() {
        return onCardsAddedToPlayer;
    }

    @Override
    public ConPublisher<PlayersCards> onCardsRemovedByPlayer() {
        return onCardsRemovedByPlayer;
    }
}
