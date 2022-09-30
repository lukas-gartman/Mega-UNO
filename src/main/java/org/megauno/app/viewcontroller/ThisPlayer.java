package org.megauno.app.viewcontroller;

import com.badlogic.gdx.scenes.scene2d.ui.Image;
import org.megauno.app.model.Cards.ICard;
import org.megauno.app.model.Player.Player;
import org.megauno.app.utility.ObserverPattern.Subscriber;
import org.megauno.app.viewcontroller.distributers.handChanges.HandChanges;
import org.megauno.app.viewcontroller.distributers.handChanges.HandChangesSubscriber;

import java.util.ArrayList;
import java.util.List;

import static org.megauno.app.utility.CardMethodes.cardsDifference;
import static org.megauno.app.utility.CardMethodes.getRealCards;

public class ThisPlayer extends Image implements Subscriber<IGame> {
	private int playerId;
	private List<Card> cards = new ArrayList<>();

	public ThisPlayer(Player player) {
		this.playerId = player.getId();
		for(ICard card : player.getCards()){
			cards.add(new Card(card));
		}

	}


	@Override
	public void delivery(IGame game) {
		handChanges(game);
	}

	private void handChanges(IGame game){
		Player player = game.getPlayerWithId(playerId);
		List<ICard> newCards = player.getCards();
		List<ICard> currentCards = getRealCards(cards);
		addCards(cardsDifference(currentCards,newCards));
		removeCards(cardsDifference(newCards,currentCards));
	}

	private void addCards(List<ICard> cards){

	}

	private void removeCards(List<ICard> cards){

	}
}

