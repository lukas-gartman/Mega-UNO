package org.megauno.app.application;

import org.json.JSONObject;
import org.megauno.app.model.cards.Color;
import org.megauno.app.model.cards.ICard;
import org.megauno.app.model.game.Game;

import org.megauno.app.model.game.utilities.PlayerCircle;
import org.megauno.app.model.player.Player;
import org.megauno.app.network.SendInfoToClients;
import org.megauno.app.network.implementation.IdCard;
import org.megauno.app.network.implementation.Lobby;
import org.megauno.app.network.implementation.PlayersCards;
import org.megauno.app.utility.BiDicrectionalHashMap;
import org.megauno.app.utility.BiHashMap;
import org.megauno.app.utility.Publisher.TupleHashMap;
import org.megauno.app.viewcontroller.GamePublishers;

import java.util.ArrayList;
import java.util.List;

public class ServerHost {
    private Lobby lobby;
    private Game game;
    private int lastCardId = 0;
    private BiDicrectionalHashMap<Integer, ICard> cardsWithID = new TupleHashMap<>();
    private BiDicrectionalHashMap<Integer, Player> playersWithID = new BiHashMap<>();

    public void createLobby(int port) throws IllegalAccessException {
        this.lobby = new Lobby(port, (this::readJson));
    }

    public void start() {
        createGame(lobby.getIds());
        addLobbySubscriptions(game, lobby.getInfoSender(), cardsWithID, playersWithID);
        lobby.getInfoSender().start();
        game.start(7);

        System.out.println("Starting game!");
    }

    public void close() {
        if (lobby != null)
            lobby.close();
    }

    private void createGame(List<Integer> ids) {
        for (int id : ids) {
            Player p = new Player();
            playersWithID.put(id, p);
        }
        PlayerCircle pc = new PlayerCircle(playersWithID.getRightKeys().stream().toList());
        game = new Game(pc);
    }

    private void readJson(JSONObject object) {
        System.out.println(object.toString());
        String type = object.getString("Type");
        int clientId = object.getInt("ClientId");
        Player player = playersWithID.getRight(clientId);
        switch (type) {
            case "SelectCard": {
                int cardId = object.getInt("CardId");
                if (player != null) {
                    game.selectCard(player, cardsWithID.getRight(cardId));
                }
                break;
            }
            case "UnSelectCard": {
                int cardId = object.getInt("CardId");
                if (player != null) {
                    game.unSelectCard(player, cardsWithID.getRight(cardId));
                }
                break;
            }
            case "CommenceForth": {
                game.commenceForth(player);
                break;
            }
            case "Uno": {
                game.sayUno(player);
                break;
            }
            case "DrawCard": {
                game.playerDraws(player);
                break;
            }
            case "SetColor": {
                game.setColor(player, object.getEnum(Color.class, "Color"));
                break;
            }
        }
    }

    private void addCards(List<ICard> cards, BiDicrectionalHashMap<Integer, ICard> cardsWithID) {
        for (ICard card : cards) {
            lastCardId++;
            if (!cardsWithID.put(lastCardId, card)) {
                throw new RuntimeException("Card could not be added!");
            }
        }
    }

    private void removeCards(List<ICard> cards, BiDicrectionalHashMap<Integer, ICard> cardsWithID) {
        for (ICard card : cards) {
            cardsWithID.removeRight(card);
        }
    }

    private List<IdCard> getIdCards(List<ICard> cards, BiDicrectionalHashMap<Integer, ICard> cardsWithID) {
        List<IdCard> cardTuples = new ArrayList<>();
        for (ICard card : cards) {
            cardTuples.add(new IdCard(cardsWithID.getLeft(card), card));
        }
        return cardTuples;
    }

    private void addLobbySubscriptions(GamePublishers game, SendInfoToClients infoSender,
                                       BiDicrectionalHashMap<Integer, ICard> cardsWithID,
                                       BiDicrectionalHashMap<Integer, Player> playersWithID) {

        game.onCardsAddedToPlayer().addSubscriber((t) -> {

            addCards(t.r, cardsWithID);
            int id = playersWithID.getLeft(t.l);
            infoSender.playerWithIdAddedCards(new PlayersCards(id, getIdCards(t.r, cardsWithID)));
        });

        game.onCardsRemovedByPlayer().addSubscriber((t) -> {
            int id = playersWithID.getLeft(t.l);
            infoSender.playerWithIdRemovedCards(new PlayersCards(id, getIdCards(t.r, cardsWithID)));
            removeCards(t.r, cardsWithID);
        });

        game.onNewPlayer().addSubscriber((player) -> infoSender.currentPlayerNewId(playersWithID.getLeft(player)));
        game.onNewTopCard().addSubscriber(infoSender::newTopCardOfPile);
    }

//    @Override
//    public void render() {
//
//    }
//
//    // todo: gracefully shut down application
//    @Override
//    public void dispose() {
////		viewController.teardown();
//        lobby.close();
//    }

    public static void testFunc() {
        System.out.println("Wow!");
    }
}
