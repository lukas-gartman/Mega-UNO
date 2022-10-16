package org.megauno.app.network;

import org.json.JSONObject;
import org.megauno.app.model.Cards.ICard;
import org.megauno.app.utility.BiHashMap;
import org.megauno.app.utility.Tuple;

import javax.sql.rowset.BaseRowSet;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import org.megauno.app.utility.Publisher;
import org.megauno.app.utility.Tuple;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.concurrent.Semaphore;




public class Server implements IServer, Runnable {
    private ServerSocket server;
    private Publisher<Tuple<ClientHandler, Integer>> publisher;
    private BiHashMap<ClientHandler, Integer> clientHandlers = new BiHashMap<>();
    private final Semaphore semaphore = new Semaphore(1);
    private static int cid = 0;
    private JSONReader jsonReader;
        
    public Server(int port, Publisher<Tuple<ClientHandler, Integer>> publisher, JSONReader jsonReader) throws IllegalAccessException {
        this.jsonReader = jsonReader;
        try {
            this.server = new ServerSocket(port);
        } catch (IOException ex) {
            throw new IllegalAccessException("A server is already running on port " + port);
        } catch (IllegalArgumentException ex) {
            System.out.println("Unable to create server on port " + port + ". Please use a port within the range 0-65535.");
        }
        this.publisher = publisher;
    }

    private void acceptConnections() {
        try {
            while (true) {
                Socket client = server.accept(); // accept incoming connections (blocking call)
                // set up the client connection (critical section)
                try { semaphore.acquire(); } catch (InterruptedException ex) { }
                int id = cid++; // acquire the next available client ID
                ClientHandler clientHandler = new ClientHandler(client, id, this);
                this.publisher.publish(new Tuple<>(clientHandler, id)); // notify Lobby
                this.clientHandlers.put(clientHandler, id);
                updateClientHandlers(); // update the ClientHandlers in ClientHandler
                this.semaphore.release(); // end of critical section
                new Thread(clientHandler).start();

                System.out.println("client connected");
            }
        } catch (IOException ex) {
            throw new RuntimeException("Server socket is closed");
        } catch (NullPointerException ex) {} // server was not set up properly, ignore...
    }

    public void disconnect(ClientHandler client) {
        // disconnect the client (critical section)
        try { this.semaphore.acquire(); } catch (InterruptedException ex) { }
        this.clientHandlers.remove(client);
        this.cid--; // free up the client ID
        updateClientHandlers(); // notify all other clients that client disconnected
        this.publisher.publish(new Tuple<>(client, -1)); // notify Lobby
        System.out.println("client disconnected");
        this.semaphore.release(); // end of critical section
    }

    // Make it the server's responsibility to keep track of ClientHandlers
    private void updateClientHandlers() {
        for (ClientHandler ch : clientHandlers.getLeftKeys())
            ch.updateClientHandlers(clientHandlers);
    }

    public BiHashMap<ClientHandler, Integer> getClientHandlers() {
        return this.clientHandlers;
    }


    public void broadcast(JSONObject json){
        for (ClientHandler ch:clientHandlers.getLeftKeys()) {
            ch.send(json);
        }
    }

    public void close() {
        try {
            server.close();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void run() {
        acceptConnections();
    }

}
