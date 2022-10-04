package org.megauno.app.model;

import org.megauno.app.model.Game.Game;
import org.megauno.app.model.Game.PlayerCircle;
import org.megauno.app.model.Player.Player;

import java.util.ArrayList;
import java.util.List;

public class GameFactory {
    //PlayerCircle players;
    //Deck deck; due to the reference to the game needed in the deck, this is created in game
    //Pile pile;

    public Game createGame(int numberOfPlayers) {
       PlayerCircle players = new PlayerCircle(generatePlayers(numberOfPlayers));
       return new Game(players);
    }

    private List<Player> generatePlayers(int n) {
        List<Player> playerList = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            playerList.add(new Player());
        }
        return playerList;
    }

}
