package org.megauno.app.viewcontroller;

import org.megauno.app.model.Game.Game;
import org.megauno.app.model.Player.Player;
import org.megauno.app.utility.ObserverPattern.Publisher;

import java.util.ArrayList;
import java.util.List;

public class GameViewsFactory {
    public static List<GameView> createGameViews(Player[] players, Publisher<IGame> publisher){
        ThisPlayer[] thesePlayers = new ThisPlayer[players.length];
        OtherPlayer[] otherPlayers = new OtherPlayer[players.length];
        for(int i = 0; i < players.length; i++){
            thesePlayers[i] = new ThisPlayer(players[i]);
            otherPlayers[i] = new OtherPlayer(players[i].getId(),players.length);
            publisher.addSubscriber(thesePlayers[i]);
            publisher.addSubscriber(otherPlayers[i]);
        }

        return createGameViewHelper(thesePlayers,otherPlayers);
    }

    private static List<GameView> createGameViewHelper(ThisPlayer[] thesePlayers, OtherPlayer[] otherPlayers){
        List<GameView> gameViews = new ArrayList<>();
        for(int i = 0; i < thesePlayers.length; i++){
            List<OtherPlayer> thisPlayersOtherPlayers = new ArrayList<>();
            for(int j = 0; j < otherPlayers.length; j++){
                if(i != j) {
                    thisPlayersOtherPlayers.add(otherPlayers[j]);
                }
            }
            gameViews.add(new GameView(thesePlayers[i],thisPlayersOtherPlayers));
        }
        return gameViews;
    }
}
