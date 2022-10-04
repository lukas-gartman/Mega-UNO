package org.megauno.app.network;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

public class Server implements IServer, Runnable {
    private ServerSocket server;
    private HashMap<ClientHandler, Integer> clientHandlers = new HashMap<>();
    private static int cid = 1;

    public Server(int port) {
        try {
            this.server = new ServerSocket(port);
        } catch (IOException ex) {
            System.out.println("A server is already running on port " + port);
        } catch (IllegalArgumentException ex) {
            System.out.println("Unable to create server on port " + port + ". Please use a port within the range 0-65535.");
        }
    }

    public void acceptConnections() {
        try {
            while (true) {
                Socket client = server.accept();
                int id = cid++;
                ClientHandler clientHandler = new ClientHandler(client, id, this);
                clientHandlers.put(clientHandler, id);
                updateClientHandlers();
                new Thread(clientHandler).start();

                System.out.println("client connected");
            }
        } catch (IOException ex) {
            throw new RuntimeException("Server socket is closed");
        } catch (NullPointerException ex) {} // server was not setup properly, ignore...
    }

    public void disconnect(ClientHandler client) {
        this.clientHandlers.remove(client);
        cid--;
        updateClientHandlers();
        System.out.println("client disconnected");
    }

    private void updateClientHandlers() {
        for (ClientHandler ch : clientHandlers.keySet())
            ch.updateClientHandlers(clientHandlers);
    }

    public HashMap<ClientHandler, Integer> getClientHandlers() {
        return this.clientHandlers;
    }

    @Override
    public void run() {
        acceptConnections();
    }
}
