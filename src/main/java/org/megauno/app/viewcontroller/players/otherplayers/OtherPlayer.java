package org.megauno.app.viewcontroller.players.otherplayers;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import org.megauno.app.application.ClientScreen;
import org.megauno.app.viewcontroller.ViewPublisher;
import org.megauno.app.viewcontroller.IDrawable;

import java.util.ArrayList;
import java.util.List;

// Graphical element to represent another player
public class OtherPlayer implements IDrawable {

    static BitmapFont font = ClientScreen.minecraftFont;

    static Sprite cardBack = ClientScreen.backSideOfCard;
    private int playerID;
    private String nickname;
    private List<Sprite> cards = new ArrayList<>();

    public float x;
    public float y;

    public OtherPlayer(int playerID, String nickname, ViewPublisher publishers) {
        this.playerID = playerID;
        this.nickname = nickname;
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

    public int getNrOfCard() {
        return cards.size();
    }

    public int getPlayerID() {
        return playerID;
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
        font.draw(batch, nickname + ": " + cards.size(), x, y);
    }
}
