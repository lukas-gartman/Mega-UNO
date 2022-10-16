package org.megauno.app.network;

import org.megauno.app.model.Game.PlayerCircle;
import org.megauno.app.model.Player.Player;
import org.megauno.app.utility.BiHashMap;
import org.megauno.app.utility.Publisher.normal.Publisher;
import org.megauno.app.utility.Tuple;

import java.util.*;
import java.util.concurrent.Phaser;

public class Lobby {
    private volatile BiHashMap<ClientHandler, Integer> clientHandlers;
    private volatile PlayerCircle players = new PlayerCircle();
    private BiHashMap<Integer, Player> playersWithID = new BiHashMap<>();
    private volatile UnoServer server;
    private Phaser phaser;
    private final Publisher<Tuple<ClientHandler, Integer>> serverPublisher = new Publisher<>();

    public Lobby(Phaser phaser) {
        this.phaser = phaser;
    }

    public void delivery(Tuple<ClientHandler, Integer> event) {
        ClientHandler clientHandler = event.l;
        int id = event.r;
        if (clientHandlers.getLeftKeys().contains(clientHandler))
            clientHandlers.removeLeft(clientHandler);
        else
            clientHandlers.put(clientHandler, id);
    }

    public void host(JSONReader jsonReader) throws IllegalAccessException {
        server = new UnoServer(1337, serverPublisher, jsonReader); // Game host holds the server object
        serverPublisher.addSubscriber(this::delivery); // subscribe self to changes to client handlers
        new Thread(server).start(); // Start the server on a new thread to prevent blocking
        clientHandlers = server.getClientHandlers(); // initialise the map of clientHandlers
        Client client = new Client("Host","localhost", 1337, jsonReader); // Create client for the host
        new Client("player 1","localhost", 1337, jsonReader); // dummy client
        new Client("player 2","localhost", 1337, jsonReader); // dummy client

        //Waiting for host to start
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

    public BiHashMap<Integer, Player> getPlayersWithID() {
        return this.playersWithID;
    }

    public SendInfoToClients getInfoSender(){
        return server;
    }

}
