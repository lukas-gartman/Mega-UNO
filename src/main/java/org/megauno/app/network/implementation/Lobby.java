package org.megauno.app.network.implementation;

import org.megauno.app.network.IClientHandler;
import org.megauno.app.network.JSONReader;
import org.megauno.app.network.SendInfoToClients;
import org.megauno.app.utility.BiDicrectionalHashMap;
import org.megauno.app.utility.BiHashMap;
import org.megauno.app.utility.Publisher.normal.Publisher;
import org.megauno.app.utility.Tuple;

import java.util.List;
import java.util.Scanner;
import java.util.concurrent.CountDownLatch;

/**
 * A lobby that is used to host a game server which accepts incoming clients
 *
 * @author Lukas Gartman
 */
public class Lobby {
    private int port;
    private volatile BiDicrectionalHashMap<IClientHandler, Integer> clientHandlers;
    private volatile UnoServer server;
    private final Publisher<Tuple<IClientHandler, Integer>> serverPublisher = new Publisher<>();

    /**
     * Creates a lobby
     *
     * @param cdl a synchronisation barrier used to signal when the lobby is finished
     * @param jr  a JSONReader for interpreting JSON
     */
    public Lobby(int port, JSONReader jr) throws IllegalAccessException {
        this.port = port;
        host(jr);
    }

    private void delivery(Tuple<IClientHandler, Integer> event) {
        IClientHandler clientHandler = event.l;
        int id = event.r;
        if (clientHandlers.getLeftKeys().contains(clientHandler))
            clientHandlers.removeLeft(clientHandler);
        else
            clientHandlers.put(clientHandler, id);
    }

    /**
     * Creates a server and watches for incoming clients
     *
     * @param jsonReader a JSONReader for interpreting JSON
     * @return a list of client IDs
     * @throws IllegalAccessException when a server is already running
     */
    public void host(JSONReader jsonReader) throws IllegalAccessException {
        server = new UnoServer(port, serverPublisher, jsonReader); // Game host holds the server object
        serverPublisher.addSubscriber(this::delivery); // subscribe self to changes to client handlers
        new Thread(server).start(); // Start the server on a new thread to prevent blocking
        clientHandlers = server.getClientHandlers(); // initialise the map of clientHandlers
    }

    private void join() {
        // todo: implement logic for joining a lobby
    }

    /**
     * Close the lobby
     */
    public void close() {
        this.server.close();
    }

    /**
     * Get the info sender (server)
     *
     * @return the info sender
     */
    public SendInfoToClients getInfoSender() {
        return server;
    }

    public List<Integer> getIds() {
        return clientHandlers.getRightKeys().stream().toList();
    }


}
