package org.megauno.app.viewcontroller;

import com.badlogic.gdx.graphics.g2d.Batch;
import org.megauno.app.network.Implementation.IdCard;
import org.megauno.app.viewcontroller.datafetching.IDrawable;

import java.util.ArrayList;
import java.util.List;

public class ThisPlayer implements IDrawable {
    private int playerID;
    // Visual cards
    private List<Card> vCards = new ArrayList<>();

    private GameController gameController;

    public ThisPlayer(int playerID, ViewPublisher publishers, GameController gameController) {
        this.playerID = playerID;
        this.gameController = gameController;

        publishers.onCardsAddedToPlayer().addSubscriberWithCondition(
                (np) -> addCards(np.getCards()),
                (np) -> np.getId() == playerID
        );

        publishers.onCardsRemovedByPlayer().addSubscriberWithCondition(
                (np) -> removeCards(np.getCards()),
                (np) -> np.getId() == playerID
        );
    }


    void addCards(List<IdCard> cards) {
        int cardIndex = 0;
        int vCardSize = vCards.size();
        for (int i = vCards.size(); i < vCardSize + cards.size(); i++) {
            IdCard card = cards.get(cardIndex);
            // Note, cardID is just the index in the list of cards
            Card vCard = new Card(card.getCard(), card.getId(), gameController);
            vCard.x = i * 50;
            vCard.y = 100;
            // Add controller for card
            // vCard.addListener(new CardListener(i, game));
            vCards.add(vCard);
            cardIndex += 1;
        }
    }

    // Removes all the view cards from the player which are equal to the argumnet
    // cards
    void removeCards(List<IdCard> cards) {
        List<Card> toRemove = new ArrayList<>();
        for (IdCard t : cards) {
            int id = t.getId();
            for (Card visualCard : vCards) {
                if (visualCard.getCardId() == id) {
                    toRemove.add(visualCard);
                }
            }
        }
        for (Card visualCard : toRemove) {
            vCards.remove(visualCard);
        }
    }

    // For now, card's positions don't get updated when a player's hand changes,
    // they are just drawn in order in the list every frame
    @Override
    public void draw(float delta, Batch batch) {
        for (int i = 0; i < vCards.size(); i++) {
            Card c = vCards.get(i);
            c.x = i * 50;
            c.y = 100;
            c.draw(delta, batch);
        }
    }

}
