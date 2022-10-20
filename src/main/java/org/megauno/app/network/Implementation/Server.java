package org.megauno.app.network.Implementation;

import org.json.JSONObject;
import org.megauno.app.network.IClientHandler;
import org.megauno.app.network.IServer;
import org.megauno.app.network.JSONReader;
import org.megauno.app.utility.BiHashMap;
import org.megauno.app.utility.Publisher.normal.Publisher;
import org.megauno.app.utility.Tuple;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.concurrent.Semaphore;

public class Server implements IServer, Runnable {
    private ServerSocket server;
    private Publisher<Tuple<IClientHandler, Integer>> publisher;
    private BiHashMap<IClientHandler, Integer> clientHandlers = new BiHashMap<>();
    private HashMap<IClientHandler, Thread> clientHandlerThreads = new HashMap<>();
    private final Semaphore semaphore = new Semaphore(1);
    private static int cid = 0;
    private JSONReader jsonReader;

    public Server(int port, Publisher<Tuple<IClientHandler, Integer>> publisher, JSONReader jsonReader) throws IllegalAccessException {
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

    @Override
    public void run() {
        acceptConnections();
    }

    private void acceptConnections() {
        try {
            while (true) {
                Socket client = server.accept(); // accept incoming connections (blocking call)
                // set up the client connection (critical section)
                try {
                    semaphore.acquire();
                } catch (InterruptedException ex) {
                }
                int id = cid++; // acquire the next available client ID
                IClientHandler clientHandler = new ClientHandler(client, id, this, jsonReader);
                this.publisher.publish(new Tuple<>(clientHandler, id)); // notify Lobby
                this.clientHandlers.put(clientHandler, id);

                this.semaphore.release(); // end of critical section
                Thread chThread = new Thread(clientHandler);
                clientHandlerThreads.put(clientHandler, chThread);
                chThread.start();

                System.out.println("client connected");
            }
        } catch (IOException ex) {
//            throw new RuntimeException("Server socket is closed");
        } catch (NullPointerException ex) {
        } // server was not set up properly, ignore...
    }

    public void disconnectClient(ClientHandler clientHandler) {
        // disconnect the client (critical section)
        try {
            this.semaphore.acquire();
        } catch (InterruptedException ex) {
        }

        this.publisher.publish(new Tuple<>(clientHandler, -1)); // notify Lobby
        this.clientHandlerThreads.get(clientHandler).interrupt();

        clientHandler.disconnect();
        this.clientHandlerThreads.remove(clientHandler);
        this.clientHandlers.removeLeft(clientHandler);
//        this.cid--; // free up the client ID
        System.out.println("client disconnected");

        this.semaphore.release(); // end of critical section
    }

    public void close() {
        BiHashMap<ClientHandler, Integer> clients = new BiHashMap(clientHandlers);
        for (ClientHandler client : clients.getLeftKeys())
            disconnectClient(client);

        try {
            this.server.close();
            System.out.println("closed server");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void updateAllClientsJsonReaders(JSONReader jr) {
        for (IClientHandler ch : clientHandlers.getLeftKeys()) {
            ch.updateJSONReader(jr);
        }
    }

    @Override
    public BiHashMap<IClientHandler, Integer> getClientHandlers() {
        return this.clientHandlers;
    }

    @Override
    public void broadcast(JSONObject json) {
        for (IClientHandler ch : clientHandlers.getLeftKeys()) {
            ch.send(json);
        }
    }
}
