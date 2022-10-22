package org.megauno.app.application;

import com.badlogic.gdx.ApplicationAdapter;
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
import org.megauno.app.utility.Rrbdhm;
import org.megauno.app.viewcontroller.GamePublishers;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.CountDownLatch;

public class ModelApplication extends ApplicationAdapter {
    private Lobby lobby;
    private Game game;
    private int lastCardId = 0;
    private BiDicrectionalHashMap<Integer, ICard> cardsWithID = new Rrbdhm<>();
    private BiDicrectionalHashMap<Integer, Player> playersWithID = new BiHashMap<>();

    @Override
    public void create() {
        CountDownLatch countDownLatch = new CountDownLatch(1); // Used to signal when the lobby is done searching for players
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Enter port number (0-65535): ");
            int port = scanner.nextInt();
            while (port < 0 || port > 65535) {
                System.out.println("invalid port");
                port = scanner.nextInt();
            }

            // Create the lobby
            lobby = new Lobby(port, countDownLatch, (this::readJson));
            countDownLatch.await(); // Wait for the host to start the game (blocking call)
            createGame(lobby.getIds());
            addLobbySubscriptions(game, lobby.getInfoSender(), cardsWithID, playersWithID);

            System.out.println("How many cards?");
            lobby.getInfoSender().start();
            int numOfCards = scanner.nextInt();
            game.start(numOfCards);

            System.out.println("Starting game!");
        } catch (IllegalAccessException | InterruptedException e) {
            System.out.println("The lobby was closed");
        }
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
                game.drawCard(player);
                break;
            }
            case "SetColor": {
                game.setColor(player, object.getEnum(Color.class, "Color"));
            }
        }
    }

    private void addCards(List<ICard> cards, BiDicrectionalHashMap<Integer, ICard> cardsWithID) {
        for (ICard card : cards) {
            lastCardId++;
            cardsWithID.put(lastCardId, card);
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
//		game.onNewTopCard().addSubscriber( (newTopCard) -> infoSender.newTopCardOfPile(newTopCard) );
    }

    @Override
    public void render() {
        game.update();
    }

    // todo: gracefully shut down application
    @Override
    public void dispose() {
//		viewController.teardown();
        lobby.close();
    }

    public static void testFunc() {
        System.out.println("Wow!");
    }
}
