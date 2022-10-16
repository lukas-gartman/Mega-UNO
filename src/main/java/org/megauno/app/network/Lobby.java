package org.megauno.app.network;

import org.json.JSONObject;
import org.lwjgl.system.CallbackI;
import org.megauno.app.model.Cards.ICard;
import org.megauno.app.model.Game.PlayerCircle;
import org.megauno.app.model.Player.Player;
import org.megauno.app.network.Client;
import org.megauno.app.network.ClientHandler;
import org.megauno.app.network.Server;
import org.megauno.app.utility.BiHashMap;
import org.megauno.app.utility.Tuple;

import java.util.*;

public class Lobby{
    private volatile BiHashMap<ClientHandler, Integer> clientHandlers;
    private volatile PlayerCircle players = new PlayerCircle();
    private BiHashMap<Integer, Player> playersWithID = new BiHashMap<>();
    private volatile boolean searchingForPlayers = true;
    private volatile UnoServer server;


    public Lobby(){
    }

    public void host(JSONReader jsonReader) {
        server = new UnoServer(1337,jsonReader); // Game host holds the server object
        new Thread(server).start(); // Start the server on a new thread to prevent blocking
        Client client = new Client("localhost", 1337,o->{}); // Create client for the host
        new Client("localhost", 1337,o->{});
        new Client("localhost", 1337,o->{});

        // Searching for new connections
        new Thread(() -> {
            BiHashMap<ClientHandler,Integer> chs = new BiHashMap<>(); // The old clientHandler HashMap

            // Continue searching until host starts the game
            while (this.searchingForPlayers) {
                clientHandlers = server.getClientHandlers(); // Get the latest clientHandlers from the server
                // Check if there is a change in size (issue: a new player can join while another disconnects...)
                if (clientHandlers.size() != chs.size()) {
                    chs = new BiHashMap<ClientHandler, Integer>(clientHandlers);
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
            for (int id : clientHandlers.getRightKeys()) {
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

    public BiHashMap<Integer, Player> getPlayersWithID() {
        return this.playersWithID;
    }

    public SendInfoToClients getInfoSender(){
        return server;
    }

}
