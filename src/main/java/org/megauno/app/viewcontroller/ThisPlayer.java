package org.megauno.app.viewcontroller;

import com.badlogic.gdx.scenes.scene2d.ui.Image;
import org.megauno.app.model.Cards.ICard;
import org.megauno.app.model.Player.Player;
import org.megauno.app.utility.ObserverPattern.Subscriber;
import org.megauno.app.viewcontroller.distributers.handChanges.HandChanges;
import org.megauno.app.viewcontroller.distributers.handChanges.HandChangesSubscriber;

import java.util.List;

import static org.megauno.app.utility.CardMethodes.cardsDifference;

public class ThisPlayer extends Image implements Subscriber<IGame> {
	private int playerId;
	private List<ICard> cards;

	public ThisPlayer(Player player) {
		this.playerId = player.getId();
		this.cards = player.getCards();
	}


	@Override
	public void delivery(IGame game) {
		Player player = game.getPlayerWithId(playerId);
		List<ICard> newCards = player.getCards();
		addCards(cardsDifference(cards,newCards));
		removeCards(cardsDifference(newCards,cards));
	}

	private void addCards(List<ICard> cards){

	}

	private void removeCards(List<ICard> cards){

	}
}

