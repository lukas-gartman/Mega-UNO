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

public class Server implements IServer, Runnable {
    private ServerSocket server;
    private BiHashMap<ClientHandler, Integer> clientHandlers = new BiHashMap<>();
    private static int cid = 0;
    private JSONReader jsonReader;

    public Server(int port,JSONReader jsonReader) {
        this.jsonReader = jsonReader;
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
                ClientHandler clientHandler = new ClientHandler(client, id, this,jsonReader);
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
        this.clientHandlers.removeLeft(client);
        cid--;
        updateClientHandlers();
        System.out.println("client disconnected");
    }

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

    @Override
    public void run() {
        acceptConnections();
    }

}
