package org.megauno.app.application;

import org.megauno.app.model.game.Game;
import org.megauno.app.model.game.utilities.Deck;
import org.megauno.app.model.game.utilities.PlayerCircle;
import org.megauno.app.model.player.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

/**
 * A "viewless" game that makes it possible to run the game through the terminal.
 * Uses almost all the functionalities of the model.
 */
public class ViewlessGame {

    static Deck deck = new Deck();
    static PlayerCircle players = new PlayerCircle(generatePlayers(2));
    static Game game = new Game(players);

    public void run() {
        try {
            simulateGame();
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void try_play() {
        Random rand = new Random();
        Player currentPlayer = players.getCurrent();
        int randomIndex = rand.nextInt(currentPlayer.numOfCards());
        currentPlayer.selectCard(currentPlayer.getCards().get(randomIndex));
        game.try_play();
    }

    public static void playRandomTurn() throws InterruptedException {
        for (int i = 0; i < 40; i++) {
            // game.tryPlayTest();
        }
    }

    public static void simulateGame() throws InterruptedException {
        Scanner input = new Scanner(System.in);
        System.out.println("Starting the game");
        while (game.getPlayerCircle().getPlayers().length > 1) {
            //game.tryPlayTest();
            Thread.sleep(1000);
            System.out.println("\n|||||||||| New round |||||||||| \n");
            Thread.sleep(1000);
        }
    }

    public static List<Player> generatePlayers(int n) {
        List<Player> playerList = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            playerList.add(new Player());
        }
        return playerList;
    }
}
