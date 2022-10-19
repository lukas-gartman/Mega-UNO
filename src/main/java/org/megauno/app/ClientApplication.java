package org.megauno.app;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import org.json.JSONArray;
import org.json.JSONObject;
import org.lwjgl.system.CallbackI;
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
import org.megauno.app.utility.Publisher.IPublisher;
import org.megauno.app.utility.Publisher.condition.ConPublisher;
import org.megauno.app.utility.Publisher.normal.Publisher;
import org.megauno.app.utility.dataFetching.DataFetcher;
import org.megauno.app.utility.dataFetching.PathDataFetcher;
import org.megauno.app.viewcontroller.GameController;
import org.megauno.app.viewcontroller.LoadedData;
import org.megauno.app.viewcontroller.Root;
import org.megauno.app.viewcontroller.ViewPublisher;


import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ClientApplication extends ApplicationAdapter implements GameController, ViewPublisher {
    static public Sprite BlueCard;
    static public Sprite BackSideOfCard;
    static public Sprite GreenCard;
    static public Sprite RedCard;
    static public Sprite Reverse;
    static public Sprite Take2;
    static public Sprite Take4;
    static public Sprite Tomte;
    static public Sprite WhiteCard;
    static public Sprite WildCard;
    static public Sprite YellowCard;
    static public BitmapFont Minecraft;
    static public Sprite Background;
    static public Sprite CommenceForth;
    static public Sprite DrawPile;
    static public Sprite SayUnoButton;

    private Publisher<Integer> onNewPlayer = new Publisher<>();
    private Publisher<ICard> onNewTopCard = new Publisher<>();
    private ConPublisher<PlayersCards> onCardsAddedToPlayer = new ConPublisher<>();
    private ConPublisher<PlayersCards> onCardsRemovedByPlayer = new ConPublisher<>();
    private Client client;
    private Root root;


    public ClientApplication() {

    }

    @Override
    public void create() {
        DataFetcher<String, Sprite> spriteDataFetcher = new PathDataFetcher<>(
                key -> new Sprite(new Texture(key)), "assets/"
        );
        DataFetcher<String, BitmapFont> bitmapFontDataFetcher = new PathDataFetcher<>(
                key -> new BitmapFont(Gdx.files.internal(key)), "assets/"
        );

        this.BlueCard = spriteDataFetcher.tryGetDataUnSafe("BlueCard.png");
        this.BackSideOfCard = spriteDataFetcher.tryGetDataUnSafe("Card.png");
        this.GreenCard = spriteDataFetcher.tryGetDataUnSafe("GreenCard.png");
        this.RedCard= spriteDataFetcher.tryGetDataUnSafe("RedCard.png");
        this.Reverse= spriteDataFetcher.tryGetDataUnSafe("Reverse.png");
        this.Take2= spriteDataFetcher.tryGetDataUnSafe("Take2.png");
        this.Take4= spriteDataFetcher.tryGetDataUnSafe("Take4.png");
        this.Tomte= spriteDataFetcher.tryGetDataUnSafe("Tomte.png");
        this.WhiteCard= spriteDataFetcher.tryGetDataUnSafe("WhiteCard.png");
        this.WildCard= spriteDataFetcher.tryGetDataUnSafe("WildCard.png");
        this.YellowCard= spriteDataFetcher.tryGetDataUnSafe("YellowCard.png");
        this.Minecraft = bitmapFontDataFetcher.tryGetDataUnSafe("minecraft.fnt");
        this.Background = spriteDataFetcher.tryGetDataUnSafe("Background.png");
        this.CommenceForth = spriteDataFetcher.tryGetDataUnSafe("CommenceForth.png");
        this.DrawPile = spriteDataFetcher.tryGetDataUnSafe("DrawPile.png");
        this.SayUnoButton =  spriteDataFetcher.tryGetDataUnSafe("SayUnoButton.png");
        root = new Root();


        System.out.println("");
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
                root.start(o.getInt("PlayerId"),otherPlayers,this,this);
            }else {
                respondToJSON(o);
            }
        });

    }

    @Override
    public void render() {
        root.draw();
    }

     void respondToJSON(JSONObject o) {
         String type = o.getString("Type");

         if (root != null) {

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
                     onNewTopCard.publish((iCardMaker(o.getJSONObject("Card"))));
                     break;
             }
         } else {
             if (type.equals("Start")) {
                 List<Object> jsonArray = o.getJSONArray("OtherPlayers").toList();
                 int[] otherPlayers = new int[jsonArray.size()];
                 for (int i = 0; i < otherPlayers.length; i++) {
                     otherPlayers[i] = (int) jsonArray.get(i);
                 }
             }

         }
     }

    private static PlayersCards parsePlayerCards(JSONObject o) {
        int playerID = o.getInt("PlayerId");

        JSONArray jsonArray = o.getJSONArray("Cards");
        List<IdCard> cards = new ArrayList<>();
        for (Object object : jsonArray) {
            JSONObject jsonObject = (JSONObject) object;
            int id = jsonObject.getInt("id");
            JSONObject card = jsonObject.getJSONObject("card");
            cards.add(new IdCard(id, iCardMaker(card)));
        }
        return new PlayersCards(playerID, cards);
    }


    static private ICard iCardMaker(JSONObject jsonObject){
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
        ICard icard = new NumberCard(Color.RED,2);
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
        return icard;
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
    public void setColor(Color color) {
        client.sendJSON(new JSONObject().put("Type", "SetColor").put("Color",color));
    }

    @Override
    public void drawCard() {
        System.out.println("drawing card");
        client.sendJSON(new JSONObject().put("Type","DrawCard"));
    }

    @Override
    public Publisher<Integer> onNewPlayer() {
        return onNewPlayer;
    }

    @Override
    public IPublisher<ICard> onNewTopCard() {
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
