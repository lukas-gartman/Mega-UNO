package org.megauno.app.network;

import org.megauno.app.utility.BiHashMap;
import org.megauno.app.utility.Publisher.normal.Publisher;
import org.megauno.app.utility.Tuple;

import java.util.*;
import java.util.concurrent.CountDownLatch;

/**
 * A lobby that is used to host a game server which accepts incoming clients
 * @author Lukas Gartman
 */
public class Lobby {
    private volatile BiHashMap<ClientHandler, Integer> clientHandlers;
    private volatile UnoServer server;
    private CountDownLatch countDownLatch;
    private final Publisher<Tuple<ClientHandler, Integer>> serverPublisher = new Publisher<>();


    /**
     * Creates a lobby
     * @param cdl a synchronisation barrier used to signal when the lobby is finished
     * @param jr a JSONReader for interpreting JSON
     */
    public Lobby(CountDownLatch cdl, JSONReader jr) throws IllegalAccessException {
        this.countDownLatch = cdl;
        host(jr);
    }

    private void delivery(Tuple<ClientHandler, Integer> event) {
        ClientHandler clientHandler = event.l;
        int id = event.r;
        if (clientHandlers.getLeftKeys().contains(clientHandler))
            clientHandlers.removeLeft(clientHandler);
        else
            clientHandlers.put(clientHandler, id);
    }

    /**
     * Creates a server and watches for incoming clients
     * @param jsonReader a JSONReader for interpreting JSON
     * @return a list of client IDs
     * @throws IllegalAccessException when a server is already running
     */
    public void host(JSONReader jsonReader) throws IllegalAccessException {
        server = new UnoServer(1337, serverPublisher, jsonReader); // Game host holds the server object
        serverPublisher.addSubscriber(this::delivery); // subscribe self to changes to client handlers
        new Thread(server).start(); // Start the server on a new thread to prevent blocking
        clientHandlers = server.getClientHandlers(); // initialise the map of clientHandlers
        //Client client = new Client("Host","localhost", 1337, o->{}); // Create client for the host
        //new Client("player 1","localhost", 1337, o->{}); // dummy client
        //new Client("player 2","localhost", 1337, o->{}); // dummy client

        //Waiting for host to start
        new Thread(() -> {
            Scanner scanner = new Scanner(System.in); // Get input from console
            System.out.print("enter start: ");
            while (!scanner.nextLine().equals("start") || clientHandlers.size() == 0) // Start the game by typing "start"
                System.out.print("enter start: ");

            this.countDownLatch.countDown(); // The task is finished
        }).start();
    }

    private void join() {
        // todo: implement logic for joining a lobby
    }

    /**
     * Get the info sender (server)
     * @return the info sender
     */
    public SendInfoToClients getInfoSender(){
        return server;
    }

    public List<Integer> getIds(){
        return clientHandlers.getRightKeys().stream().toList();
    }


}
