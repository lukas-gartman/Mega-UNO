package org.megauno.app.application;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import org.json.JSONArray;
import org.json.JSONObject;
import org.megauno.app.model.cards.CardType;
import org.megauno.app.model.cards.Color;
import org.megauno.app.model.cards.ICard;
import org.megauno.app.model.cards.implementation.ActionCard;
import org.megauno.app.model.cards.implementation.NumberCard;
import org.megauno.app.model.game.actions.ReverseAction;
import org.megauno.app.model.game.actions.TakeFourAction;
import org.megauno.app.model.game.actions.TakeTwoAction;
import org.megauno.app.model.game.actions.WildCardAction;
import org.megauno.app.network.implementation.Client;
import org.megauno.app.network.implementation.IdCard;
import org.megauno.app.network.implementation.PlayersCards;
import org.megauno.app.utility.Publisher.ISubscribable;
import org.megauno.app.utility.Publisher.condition.ConditionPublisher;
import org.megauno.app.utility.Publisher.normal.Publisher;
import org.megauno.app.utility.dataFetching.DataFetcher;
import org.megauno.app.utility.dataFetching.PathDataFetcher;
import org.megauno.app.viewcontroller.controller.GameController;
import org.megauno.app.viewcontroller.Root;
import org.megauno.app.viewcontroller.ViewPublisher;

import java.net.ConnectException;
import java.util.*;

public class ClientScreen extends ScreenAdapter implements GameController, ViewPublisher {
    static public Sprite blueCard;
    static public Sprite backSideOfCard;
    static public Sprite greenCard;
    static public Sprite redCard;
    static public Sprite reverse;
    static public Sprite take2;
    static public Sprite take4;
    static public Sprite tomte;
    static public Sprite whiteCard;
    static public Sprite wildCard;
    static public Sprite yellowCard;
    static public BitmapFont minecraftFont;
    static public Sprite background;
    static public Sprite commenceForth;
    static public Sprite drawPile;
    static public Sprite sayUnoButton;

    private MegaUNO megaUNO;
    private Viewport viewport;
    private final String nickname;
    private final String hostname;
    private final int port;

    private Publisher<Integer> onNewPlayer = new Publisher<>();
    private Publisher<ICard> onNewTopCard = new Publisher<>();
    private ConditionPublisher<PlayersCards> onCardsAddedToPlayer = new ConditionPublisher<>();
    private ConditionPublisher<PlayersCards> onCardsRemovedByPlayer = new ConditionPublisher<>();
    private Client client;
    private Root root;

    public ClientScreen(MegaUNO megaUNO, String nickname, String hostname, int port) throws ConnectException {
        this.megaUNO = megaUNO;
        this.nickname = nickname;
        this.hostname = hostname;
        this.port = port;
        this.viewport = new ExtendViewport(megaUNO.WINDOW_WIDTH, megaUNO.WINDOW_HEIGHT);

        client = new Client(hostname, port, o ->
        {
            if (o.getString("Type").equals("Start")) {
                JSONObject otherPlayersObject = o.getJSONObject("OtherPlayers");
                HashMap<Integer, String> otherPlayers = new HashMap<>();
                for (String key : otherPlayersObject.keySet())
                    otherPlayers.put(Integer.parseInt(key), (String) otherPlayersObject.get(key));

                root.start(o.getInt("PlayerId"), otherPlayers, this, this);
            } else {
                respondToJSON(o);
            }
        });
    }

    @Override
    public void show() {
        DataFetcher<String, Sprite> spriteDataFetcher = new PathDataFetcher<>(
                key -> new Sprite(new Texture(key)), "assets/"
        );
        DataFetcher<String, BitmapFont> bitmapFontDataFetcher = new PathDataFetcher<>(
                key -> new BitmapFont(Gdx.files.internal(key)), "assets/"
        );

        this.blueCard = spriteDataFetcher.tryGetDataUnSafe("BlueCard.png");
        this.backSideOfCard = spriteDataFetcher.tryGetDataUnSafe("Card.png");
        this.greenCard = spriteDataFetcher.tryGetDataUnSafe("GreenCard.png");
        this.redCard = spriteDataFetcher.tryGetDataUnSafe("RedCard.png");
        this.reverse = spriteDataFetcher.tryGetDataUnSafe("Reverse.png");
        this.take2 = spriteDataFetcher.tryGetDataUnSafe("Take2.png");
        this.take4 = spriteDataFetcher.tryGetDataUnSafe("Take4.png");
        this.tomte = spriteDataFetcher.tryGetDataUnSafe("Tomte.png");
        this.whiteCard = spriteDataFetcher.tryGetDataUnSafe("WhiteCard.png");
        this.wildCard = spriteDataFetcher.tryGetDataUnSafe("WildCard.png");
        this.yellowCard = spriteDataFetcher.tryGetDataUnSafe("YellowCard.png");
        this.minecraftFont = bitmapFontDataFetcher.tryGetDataUnSafe("minecraft.fnt");
        this.background = spriteDataFetcher.tryGetDataUnSafe("Background.png");
        this.commenceForth = spriteDataFetcher.tryGetDataUnSafe("CommenceForth.png");
        this.drawPile = spriteDataFetcher.tryGetDataUnSafe("DrawPile.png");
        this.sayUnoButton = spriteDataFetcher.tryGetDataUnSafe("SayUnoButton.png");
        root = new Root(megaUNO);

    }

    @Override
    public void render(float delta) {
        root.draw();
        super.render(delta);
    }

    @Override
    public void resize(int width, int height) {
        viewport.setWorldSize(width, height);
        root.resize(width, height);
//        stage.getViewport().update(width, height);
    }

    @Override
    public void dispose() {
        if (client != null)
            client.disconnect();
        super.dispose();
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

    static private ICard iCardMaker(JSONObject jsonObject) {
        Color c = Color.NONE;
        switch (jsonObject.getString("color")) {
            case "BLUE":
                c = Color.BLUE;
                break;
            case "GREEN":
                c = Color.GREEN;
                break;
            case "RED":
                c = Color.RED;
                break;
            case "YELLOW":
                c = Color.YELLOW;
                break;
            case "NONE":
                c = Color.NONE;
                break;
        }

        ICard icard = new NumberCard(Color.RED, 2);
        switch (jsonObject.getString("type")) {
            case "NUMBERCARD":
                icard = new NumberCard(c, (int) jsonObject.get("number"));
                break;
            case "REVERSECARD":
                icard = new ActionCard(new ReverseAction(), c, CardType.REVERSECARD);
                break;
            case "WILDCARD":
                icard = new ActionCard(new WildCardAction(), c, CardType.WILDCARD);
                break;
            case "TAKETWO":
                icard = new ActionCard(new TakeTwoAction(), c, CardType.TAKETWO);
                break;
            case "TAKEFOUR":
                icard = new ActionCard(new TakeFourAction(), c, CardType.TAKEFOUR);
                break;
        }
        return icard;
    }

    @Override
    public void selectCard(int cardId) {
        client.sendJSON(new JSONObject().put("Type", "SelectCard").put("CardId", cardId));
    }

    @Override
    public void unSelectCard(int cardId) {
        client.sendJSON(new JSONObject().put("Type", "UnSelectCard").put("CardId", cardId));
    }

    @Override
    public void commenceForth() {
        client.sendJSON(new JSONObject().put("Type", "CommenceForth"));
    }

    @Override
    public void sayUno() {
        client.sendJSON(new JSONObject().put("Type", "Uno"));
    }

    @Override
    public void setColor(Color color) {
        client.sendJSON(new JSONObject().put("Type", "SetColor").put("Color", color));
    }

    @Override
    public void drawCard() {
        client.sendJSON(new JSONObject().put("Type", "DrawCard"));
    }

    @Override
    public void sendNickname(String nickname) {
        client.sendJSON(new JSONObject().put("Type", "PlayerNickname").put("Nickname", nickname));
    }

    @Override
    public Publisher<Integer> onNewPlayer() {
        return onNewPlayer;
    }

    @Override
    public ISubscribable<ICard> onNewTopCard() {
        return onNewTopCard;
    }

    @Override
    public ConditionPublisher<PlayersCards> onCardsAddedToPlayer() {
        return onCardsAddedToPlayer;
    }

    @Override
    public ConditionPublisher<PlayersCards> onCardsRemovedByPlayer() {
        return onCardsRemovedByPlayer;
    }
}
