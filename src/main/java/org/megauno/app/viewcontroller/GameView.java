package org.megauno.app.viewcontroller;

import com.badlogic.gdx.graphics.g2d.Batch;
import org.megauno.app.ClientApplication;
import org.megauno.app.model.Cards.CardType;
import org.megauno.app.model.Cards.Color;
import org.megauno.app.model.Cards.ICard;
import org.megauno.app.model.Cards.Impl.ActionCard;
import org.megauno.app.viewcontroller.datafetching.IDrawable;

import java.util.ArrayList;
import java.util.List;

// For now, GameView parses deltas from Game and calls the appropriate
public class GameView implements IDrawable {
    private int playerID;

    private Card top;
    private ThisPlayer thisPlayer;
    private List<OtherPlayer> otherPlayers = new ArrayList<>();
    private EndTurnButton endTurnButton;
    private GameController gameController;
    private SayUnoButton sayUnoButton;
    private DrawPile drawPile;
    private int currentPlayerId;

    public GameView(int playerID, int[] otherPlayersIds, ViewPublisher publishers, GameController gameController) {
        // Add this view's player
        this.playerID = playerID;
        this.gameController = gameController;

        thisPlayer = new ThisPlayer(playerID, publishers, gameController);

        // Add all other players
        // TODO: make the positions make sense regarding actual placing in the list
        int offset = 0;
        for (int i : otherPlayersIds) {
            OtherPlayer otherPlayer = new OtherPlayer(i, publishers);
            otherPlayer.y = 400;
            otherPlayer.x = offset * 200;
            otherPlayers.add(otherPlayer);
            //TODO: add position, do a top-row of OtherPlayers
            offset++;
        }

        endTurnButton = new EndTurnButton(200, 200, gameController, () -> currentPlayerId == playerID);
        sayUnoButton = new SayUnoButton(500, 30, gameController);
        drawPile = new DrawPile(350, 250, gameController);

        top = new Card(new ActionCard(o -> true, Color.NONE, CardType.WILDCARD), -1, gameController);
        top.x = 300;
        top.y = 250;

        publishers.onNewTopCard().addSubscriber(
                (np) -> updateTopCard(np)
        );
        publishers.onNewPlayer().addSubscriber(
                (np) -> {
                    currentPlayerId = np;
                }
        );
    }

    public int getPlayerID() {
        return playerID;
    }

    private void updateTopCard(ICard newTop) {
        top = new Card(newTop, -1, gameController);
        top.x = 300;
        top.y = 250;
    }

    //TODO: when a card is detected to be rmoved from hand, remove card from stage.
    // Deltas on game are checked here, called every frame by parent
    @Override
    public void draw(float delta, Batch batch) {
        // Draw background
        batch.draw(ClientApplication.background, 0, 0, 650, 500);

        thisPlayer.draw(delta, batch);

        top.draw(delta, batch);

        for (OtherPlayer op : otherPlayers) {
            op.draw(delta, batch);
        }

        // Draw end turn button
        endTurnButton.draw(delta, batch);
        // Draw say uno button
        sayUnoButton.draw(delta, batch);
        // Draw draw pile
        drawPile.draw(delta, batch);
    }


}
