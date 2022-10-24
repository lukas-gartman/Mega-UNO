package org.megauno.app.viewcontroller.players;

import com.badlogic.gdx.graphics.g2d.Batch;
import org.megauno.app.application.ClientApplication;
import org.megauno.app.model.cards.CardType;
import org.megauno.app.model.cards.Color;
import org.megauno.app.model.cards.ICard;
import org.megauno.app.model.cards.implementation.ActionCard;
import org.megauno.app.viewcontroller.*;
import org.megauno.app.viewcontroller.controller.DrawPile;
import org.megauno.app.viewcontroller.controller.EndTurnButton;
import org.megauno.app.viewcontroller.controller.GameController;
import org.megauno.app.viewcontroller.controller.SayUnoButton;
import org.megauno.app.viewcontroller.IDrawable;
import org.megauno.app.viewcontroller.players.otherplayers.OtherPlayer;
import org.megauno.app.viewcontroller.players.thisPlayer.Card;
import org.megauno.app.viewcontroller.players.thisPlayer.ThisPlayer;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the game-view of a player, that is, everything the player
 * with {@link GameView#currentPlayerId} sees on the screen.
 * This class is composed of the different elements on the screen, such
 * as ThisPlayer, DrawPile etc.
 */
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
        int offset = 0;
        for (int i : otherPlayersIds) {
            OtherPlayer otherPlayer = new OtherPlayer(i, publishers);
            otherPlayer.y = 400;
            otherPlayer.x = offset * 200;
            otherPlayers.add(otherPlayer);
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

    private void updateTopCard(ICard newTop) {
        top = new Card(newTop, -1, gameController);
        top.x = 300;
        top.y = 250;
    }

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
