package org.megauno.app;

import org.megauno.app.model.Deck;
import org.megauno.app.model.Game.Game;
import org.megauno.app.model.Game.PlayerCircle;
import org.megauno.app.model.Player.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Main {
    public static void main(String[] args ) {
        //Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        //config.setMaximized(true);
        //new Lwjgl3Application(new Application(), config);
        try {
            simulateGame();
        }
        catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
    }

    static Deck deck = new Deck();
    static PlayerCircle players = new PlayerCircle(generatePlayers(2));
    static Game game = new Game(players);

    public static void try_play() {
        Random rand = new Random();
        Player currentPlayer = players.getCurrent().getPlayer();
        int randomIndex = rand.nextInt(currentPlayer.numOfCards());
        currentPlayer.selectCard(currentPlayer.getCards().get(randomIndex));
        game.try_play();
    }

    public static void playRandomTurn() throws InterruptedException {
        for (int i = 0; i < 40; i++) {
            game.tryPlayTest();
        }
        //assert(game.tryPlayTest());
    }

    public static void simulateGame() throws InterruptedException {
        Scanner input = new Scanner(System.in);
        System.out.println("Starting the game");
        while(game.getPlayers().getPlayers().length > 1) {
            game.tryPlayTest();
            Thread.sleep(1000);
        }
    }

    public static List<Player> generatePlayers(int n) {
        List<Player> playerList = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            playerList.add(new Player(deck.dealHand(3)));
        }
        return playerList;
    }

}