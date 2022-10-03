package org.megauno.app.network;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

public class Server implements IServer, Runnable {
    private final ServerSocket server;
    private HashMap<ClientHandler, Integer> clientHandlers = new HashMap<>();
    private static int cid = 1;

    public Server(int port) {
        try {
            this.server = new ServerSocket(port);
        } catch (IOException e) {
            throw new RuntimeException(e);
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
            ex.printStackTrace();
        }
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
