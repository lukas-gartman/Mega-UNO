package org.megauno.app.model.Game;

import org.json.JSONObject;
import org.megauno.app.model.Player.Player;
import org.megauno.app.network.Client;
import org.megauno.app.network.ClientHandler;
import org.megauno.app.network.Server;

import java.util.*;

public class Lobby {
    private volatile HashMap<ClientHandler,Integer> clientHandlers;
    private volatile PlayerCircle players = new PlayerCircle();
    HashMap<Integer, Player> playersWithID = new HashMap<>();
    private volatile boolean searchingForPlayers = true;

    public Lobby() {
        try {
            host();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            searchingForPlayers = false;
        }
    }

    private void host() throws Exception {
        Server server = new Server(1337); // Game host holds the server object
        new Thread(server).start(); // Start the server on a new thread to prevent blocking
        Client client = new Client("localhost", 1337); // Create client for the host

        // Searching for new connections
        new Thread(() -> {
            HashMap<ClientHandler,Integer> chs = new HashMap<>(); // The old clientHandler HashMap

            // Continue searching until host starts the game
            while (this.searchingForPlayers) {
                clientHandlers = server.getClientHandlers(); // Get the latest clientHandlers from the server
                // Check if there is a change in size (issue: a new player can join while another disconnects...)
                if (clientHandlers.size() != chs.size()) {
                    chs = new HashMap<>(clientHandlers);
                }

                try { // Check once every 100 milliseconds
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();

        // *** TEMPORARY *** //
        new Thread(() -> {
            Scanner scanner = new Scanner(System.in); // Get input from console
            System.out.print("enter: ");
            while (!scanner.nextLine().equals("start")) // Start the game by typing "start"
                System.out.print("enter: ");

            // Create the players
            for (int id : clientHandlers.values()) {
                Player p = new Player();
                players.addNode(p);
                playersWithID.put(id, p);
            }

            this.searchingForPlayers = false; // Tell the loop to stop searching for players
        }).start();
    }

    private void join() {
        // todo: implement logic for joining a lobby
    }

    public boolean isSearchingForPlayers() {
        return this.searchingForPlayers;
    }

    public PlayerCircle getPlayerCircle() {
        return this.players;
    }

    public HashMap getPlayersWithID() {
        return this.playersWithID;
    }
}
