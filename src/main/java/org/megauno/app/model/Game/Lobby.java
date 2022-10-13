package org.megauno.app.model.Game;

import org.megauno.app.model.Player.Player;
import org.megauno.app.network.Client;
import org.megauno.app.network.ClientHandler;
import org.megauno.app.network.IServer;
import org.megauno.app.network.Server;
import org.megauno.app.utility.Tuple;

import java.util.*;
import java.util.concurrent.Phaser;

public class Lobby implements IServer.Observer {
    private volatile HashMap<ClientHandler,Integer> clientHandlers;
    private volatile PlayerCircle players = new PlayerCircle();
    private HashMap<Integer, Player> playersWithID = new HashMap<>();
    private Phaser phaser;

    public Lobby() {
        try {
            host();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public Lobby(Phaser phaser) {
        this();
        this.phaser = phaser;
    }

    @Override
    public void update(Tuple<ClientHandler, Integer> event) {
        ClientHandler clientHandler = event.l;
        int id = event.r;
        if (clientHandlers.containsKey(clientHandler))
            clientHandlers.remove(clientHandler);
        else
            clientHandlers.put(clientHandler, id);
    }

    private void host() throws Exception {
        Server server = new Server(1337); // Game host holds the server object
        new Thread(server).start(); // Start the server on a new thread to prevent blocking
        clientHandlers = server.getClientHandlers();
        server.addObserver(event -> { });

        Client client = new Client("localhost", 1337); // Create client for the host
        new Client("localhost", 1337);
        new Client("localhost", 1337);

        // *** TEMPORARY *** //
        new Thread(() -> {
            Scanner scanner = new Scanner(System.in); // Get input from console
            System.out.print("enter: ");
            while (!scanner.nextLine().equals("start")) // Start the game by typing "start"
                System.out.print("enter: ");

            // Create the players
            for (int id : clientHandlers.values()) {
                Player p = new Player(id);
                players.addNode(p);
                playersWithID.put(id, p);
            }

            this.phaser.arrive(); // The task is finished
        }).start();
    }

    private void join() {
        this.phaser.register(); // Indicate the user is not ready to play
        // todo: implement logic for joining a lobby
        this.phaser.arrive(); // Indicate the user is now ready to play
    }

    public PlayerCircle getPlayerCircle() {
        return this.players;
    }

    public HashMap getPlayersWithID() {
        return this.playersWithID;
    }
}
