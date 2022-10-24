package org.megauno.app.viewcontroller.players;

import com.badlogic.gdx.graphics.g2d.Batch;
import org.megauno.app.application.ClientScreen;
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
import java.util.HashMap;
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

    public GameView(int playerID, HashMap<Integer, String> otherPlayersIdsWithNicknames, ViewPublisher publishers, GameController gameController) {
        // Add this view's player
        this.playerID = playerID;
        this.gameController = gameController;

        thisPlayer = new ThisPlayer(playerID, publishers, gameController);

        // Add all other players
        // TODO: make the positions make sense regarding actual placing in the list
        int offset = 0;
        for (int id : otherPlayersIdsWithNicknames.keySet()) {
            String nickname = otherPlayersIdsWithNicknames.get(id);
            OtherPlayer otherPlayer = new OtherPlayer(id, nickname, publishers);
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

    public int getPlayerID() {
        return playerID;
    }

    private void updateTopCard(ICard newTop) {
        top = new Card(newTop, -1, gameController);
        top.x = 300;
        top.y = 250;
    }

    // Deltas on game are checked here, called every frame by parent
    @Override
    public void draw(float delta, Batch batch) {
        // Draw background
        batch.draw(ClientScreen.background, 0, 0, 650, 500);

        thisPlayer.draw(delta, batch);

        top.draw(delta, batch);

        for (OtherPlayer op : otherPlayers) {
            op.draw(delta, batch);
        }

        endTurnButton.draw(delta, batch);
        sayUnoButton.draw(delta, batch);
        drawPile.draw(delta, batch);
    }
}
