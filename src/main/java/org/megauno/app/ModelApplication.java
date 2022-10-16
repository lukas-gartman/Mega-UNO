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

import org.megauno.app.utility.BiHashMap;
import org.megauno.app.utility.Publisher.normal.Publisher;
import org.megauno.app.utility.Tuple;
import org.megauno.app.viewcontroller.GamePublishers;

public class ModelApplication extends ApplicationAdapter {
	private Lobby lobby;
	private Game game;
	private int lastCardId = 0;

	@Override
	public void create () {

		lobby = new Lobby(); // Create the lobby
		while (lobby.isSearchingForPlayers()) {} // Wait for the host to start the game
		System.out.println("Starting game!");
		PlayerCircle playerCircle = lobby.getPlayerCircle();
		BiHashMap<Integer, Player> playersWithID = lobby.getPlayersWithID();

		List<Integer> tmp = playersWithID.getLeftKeys().stream().toList();
		int[] playerIds = new int[tmp.size()];
		for (int i = 0; i < playerIds.length; i++) {
			playerIds[i] = tmp.get(i);
		}
		game = new Game(playerCircle, 7);

		BiHashMap<Integer, ICard> cardsWithID = new BiHashMap<>();

		addLobbySubscriptions(game,lobby.getInfoSender(),playersWithID,cardsWithID);

		lobby.host(jsonObject -> readJson(jsonObject,cardsWithID,playersWithID,game));
	}

	private void readJson(JSONObject object,
						  BiHashMap<Integer, ICard> cardsWithID,
						  BiHashMap<Integer, Player> playersWithID,
						  IGameImputs gameImputs)
	{
		String type = object.getString("Type");
		int clientId = object.getInt("ClientId");
		Player player = playersWithID.getRight(clientId);
		switch (type){
			case "SelectCard": {
				int cardId = object.getInt("CardId");
				if (player != null) {
					gameImputs.selectCard(player,cardsWithID.getRight(cardId));
				}
				break;
			}
			case "UnSelectCard": {
				int cardId = object.getInt("CardId");
				if (player != null) {
					gameImputs.unSelectCard(player,cardsWithID.getRight(cardId));
				}
			}
			case "CommenceForth":{
				gameImputs.commenceForth(player);
			}
			case "Uno":{
				gameImputs.sayUno(player);
			}
		}
	}





	private void addCards(List<ICard> cards, BiHashMap<Integer, ICard> cardsWithID){
		for(ICard card : cards){
			lastCardId++;
			cardsWithID.put(lastCardId,card);
		}
	}
	private void removeCards(List<ICard> cards, BiHashMap<Integer, ICard> cardsWithID){
		for(ICard card : cards){
			cardsWithID.removeRight(card);
		}
	}

	private List<IdCard> getIdCards(List<ICard> cards, BiHashMap<Integer, ICard> cardsWithID){
		List<IdCard> cardTuples = new ArrayList<>();
		for (ICard card: cards) {
			cardTuples.add(new IdCard(cardsWithID.getLeft(card),card));
		}
		return cardTuples;
	}

	private void addLobbySubscriptions(GamePublishers game, SendInfoToClients infoSender, BiHashMap<Integer, Player> playersWithID,
									   BiHashMap<Integer, ICard> cardsWithID){
		game.onCardsAddedToPlayer().addSubscriber(
				(t) -> {
					addCards(t.r,cardsWithID);
					int id = playersWithID.getLeft(t.l);
					infoSender.playerWithIdAddedCards(new PlayersCards(id,getIdCards(t.r,cardsWithID)));
				}
		);
		game.onCardsRemovedByPlayer().addSubscriber(
				(t) -> {
					removeCards(t.r,cardsWithID);
					int id = playersWithID.getLeft(t.l);
					infoSender.playerWithIdRemovedCards(new PlayersCards(id,getIdCards(t.r,cardsWithID)));
				}
		);
		game.onNewPlayer().addSubscriber(
				(player) -> infoSender.currentPlayerNewId(playersWithID.getLeft(player))
		);
		game.onNewTopCard().addSubscriber(
				(newTopCard) -> infoSender.newTopCardOfPile(newTopCard)
		);
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
