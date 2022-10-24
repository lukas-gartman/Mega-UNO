package org.megauno.app.viewcontroller.players.otherplayers;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import org.megauno.app.application.ClientApplication;
import org.megauno.app.viewcontroller.ViewPublisher;
import org.megauno.app.viewcontroller.IDrawable;

import java.util.ArrayList;
import java.util.List;

/**
 * Displays an other player than the view/controllers associated player.
 * These graphical elements does not show frontsides of cards.
 */
public class OtherPlayer implements IDrawable {

    static BitmapFont font = ClientApplication.minecraftFont;

    static Sprite cardBack = ClientApplication.backSideOfCard;
    private int playerID;
    private List<Sprite> cards = new ArrayList<>();

    public float x;
    public float y;

    public OtherPlayer(int playerID, ViewPublisher publishers) {
        this.playerID = playerID;
        publishers.onCardsAddedToPlayer().addSubscriberWithCondition(
                (np) -> addCards(np.getCards().size()),
                (np) -> np.getId() == playerID
        );

        publishers.onCardsRemovedByPlayer().addSubscriberWithCondition(
                (np) -> removeCards(np.getCards().size()),
                (np) -> np.getId() == playerID
        );
    }

    // Adds cards to the player
    public void addCards(int newCards) {
        for (int i = 0; i < newCards; i++) {
            Sprite card = cardBack;
            cards.add(card);
        }
    }

    // Removes cards from the player
    public void removeCards(int removedCards) {
        for (int i = 0; i < removedCards; i++) {
            cards.remove(cards.size() - 1);
        }
    }

    @Override
    public void draw(float delta, Batch batch) {
        for (int i = 0; i < cards.size(); i++) {
            Sprite card = cards.get(i);
            card.setX(x + i * 20);
            card.setY(y);
            card.draw(batch);
        }
        // Draw text as well
        font.draw(batch, "P" + playerID + ": " + Integer.toString(cards.size()), x, y);
    }
}
