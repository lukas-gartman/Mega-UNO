package org.megauno.app.model.game.utilities;

import org.junit.Before;
import org.junit.Test;
import org.megauno.app.model.game.Game;
import org.megauno.app.model.player.Player;


import static org.junit.Assert.*;
import static org.megauno.app.model.GeneralTestMethods.generatePlayers;

public class PlayerCircleTest {

    public PlayerCircle players;
    public Game game;

    public Player p1;

    @Before
    public void setUp() {
        players = new PlayerCircle(generatePlayers(5));
        game = new Game(players);
        game.start(7);
        p1 = game.getCurrentPlayer();
    }

    @Test
    public void testIsPlayerOutOfCards() {
        assertFalse(players.isPlayerOutOfCards(p1));
    }

    @Test
    public void testPlayerFinished() {
        int playersBefore = players.playersLeft();
        players.playerFinished(p1);
        assert(players.playersLeft() == playersBefore - 1);
    }

    @Test
    public void testGetNextPlayer() {
        players.changeRotation();
        Player first = players.getNextPlayer();
        players.moveOnToNextTurn();
        players.changeRotation();
        players.moveOnToNextTurn();
        players.changeRotation();
        Player second = players.getNextPlayer();
        assert(first == second);
    }

    @Test
    public void testGetNextPlayer2() {
        Player first = players.getCurrent();
        players.changeRotation();
        players.moveOnToNextTurn();
        Player second = players.getNextPlayer();
        assert(first != second);
    }

    @Test
    public void testPlayerCircleWithOnePlayer() {
        PlayerCircle playerCircle = new PlayerCircle(generatePlayers(1));
        Player first = playerCircle.getNextPlayer();
        Player second = playerCircle.getNextPlayer();
        assert(first == second);
    }

    @Test
    public void testGetNextOtherDirection() {
        Player first = players.getCurrent();
        players.moveOnToNextTurn();
        players.changeRotation();
        players.moveOnToNextTurn();
        Player second = players.getCurrent();
        assert(first == second);
    }
}