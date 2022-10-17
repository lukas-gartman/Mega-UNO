package org.megauno.app;

import org.json.JSONObject;
import org.megauno.app.model.Cards.ICard;
import org.megauno.app.model.Game.Game;
import com.badlogic.gdx.ApplicationAdapter;
import org.megauno.app.model.Game.IGameImputs;
import org.megauno.app.network.*;
import org.megauno.app.model.Game.PlayerCircle;
import org.megauno.app.model.Player.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Phaser;

import org.megauno.app.utility.BiHashMap;
import org.megauno.app.utility.Publisher.normal.Publisher;
import org.megauno.app.utility.Tuple;
import org.megauno.app.viewcontroller.GamePublishers;

public class ModelApplication extends ApplicationAdapter {
	private Game game;
	private int lastCardId = 0;
	private BiHashMap<Integer, ICard> cardsWithID = new BiHashMap<>();
	private BiHashMap<Integer, Player> playersWithID = new BiHashMap<>();

	@Override
	public void create () {
		Phaser phaser = new Phaser(1); // Used to signal when the lobby is done searching for players
		Lobby lobby = new Lobby(phaser); // Create the lobby
		try {
			phaser.awaitAdvance(0); // Wait for the host to start the game (blocking call)
		} catch (IllegalStateException ex) {
			System.out.println("The lobby was closed");
		}
		System.out.println("Starting game!");



		try {
			lobby.host((ids) -> {
				createGame(ids);
				return (o -> readJson(o, game));});
		} catch (IllegalAccessException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}

		addLobbySubscriptions(game, lobby.getInfoSender(),cardsWithID,playersWithID);
	}

	private void createGame(List<Integer> ids){
		for (Integer id: ids) {
			Player p = new Player();
			playersWithID.put(id,p);
		}
		PlayerCircle pc = new PlayerCircle(playersWithID.getRightKeys().stream().toList());
		game = new Game(pc);
	}

	private void readJson(JSONObject object, IGameImputs gameInputs)
	{
		String type = object.getString("Type");
		int clientId = object.getInt("ClientId");
		Player player = playersWithID.getRight(clientId);
		switch (type) {
			case "SelectCard": {
				int cardId = object.getInt("CardId");
				if (player != null) {
					gameInputs.selectCard(player,cardsWithID.getRight(cardId));
				}
				break;
			}
			case "UnSelectCard": {
				int cardId = object.getInt("CardId");
				if (player != null) {
					gameInputs.unSelectCard(player,cardsWithID.getRight(cardId));
				}
			}
			case "CommenceForth":{
				gameInputs.commenceForth(player);
			}
			case "Uno":{
				gameInputs.sayUno(player);
			}
		}
	}





	private void addCards(List<ICard> cards, BiHashMap<Integer, ICard> cardsWithID) {
		for(ICard card : cards){
			lastCardId++;
			cardsWithID.put(lastCardId,card);
		}
	}
	private void removeCards(List<ICard> cards, BiHashMap<Integer, ICard> cardsWithID) {
		for(ICard card : cards){
			cardsWithID.removeRight(card);
		}
	}

	private List<IdCard> getIdCards(List<ICard> cards, BiHashMap<Integer, ICard> cardsWithID) {
		List<IdCard> cardTuples = new ArrayList<>();
		for (ICard card: cards) {
			cardTuples.add(new IdCard(cardsWithID.getLeft(card),card));
		}
		return cardTuples;
	}

	private void addLobbySubscriptions(GamePublishers game, SendInfoToClients infoSender,
									   BiHashMap<Integer, ICard> cardsWithID,
									   BiHashMap<Integer, Player> playersWithID) {
		game.onCardsAddedToPlayer().addSubscriber((t) -> {
			addCards(t.r,cardsWithID);
			int id = playersWithID.getLeft(t.l);
			infoSender.playerWithIdAddedCards(new PlayersCards(id,getIdCards(t.r,cardsWithID)));
		});

		game.onCardsRemovedByPlayer().addSubscriber((t) -> {
			removeCards(t.r, cardsWithID);
			int id = playersWithID.getLeft(t.l);
			infoSender.playerWithIdRemovedCards(new PlayersCards(id, getIdCards(t.r, cardsWithID)));
		});

		game.onNewPlayer().addSubscriber( (player) -> infoSender.currentPlayerNewId(playersWithID.getLeft(player)) );
		game.onNewTopCard().addSubscriber( (newTopCard) -> infoSender.newTopCardOfPile(newTopCard) );
	}

	@Override
	public void render() {
		game.update();
	}

	//@Override
	//public void dispose() {
	//	viewController.teardown();
	//}

	public static void testFunc() {
		System.out.println("Wow!");
	}
}
