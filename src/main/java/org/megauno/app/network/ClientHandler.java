package org.megauno.app.network;

import org.json.JSONObject;
import org.megauno.app.utility.BiHashMap;

import java.io.*;
import java.net.Socket;

/**
 * Extension of Server that handles client connections.
 * @author Lukas Gartman
 */
public class ClientHandler implements Runnable {
    private IServer server;
    private BiHashMap<ClientHandler, Integer> clientHandlers;
    private final Socket client;
    private BufferedReader br;
    private BufferedWriter bw;
    private final int id;
    private JSONReader jsonReader;

    /**
     * Store the client information and set up readers and writers
     * @param client the client's socket
     * @param id the client ID
     * @param server the server interface
     * @param jsonReader an interface used for reading JSON
     */
    public ClientHandler(Socket client, int id, IServer server, JSONReader jsonReader) {
        this.client = client;
        this.id = id;
        this.server = server;
        this.jsonReader = jsonReader;
        this.clientHandlers = new BiHashMap<>();
        try {
            this.bw = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
            this.br = new BufferedReader(new InputStreamReader(client.getInputStream()));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Send a JSON object to the client
     * @param json the object to send
     */
    public void send(JSONObject json) {
        try {
            bw.write(json.toString());
            bw.newLine();
            bw.flush();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Send a JSON object from one client to all other clients
     * @param json the object to send
     */
    private void broadcast(JSONObject json) {
        for (ClientHandler ch : clientHandlers.getLeftKeys()) {
            if (clientHandlers.getRight(ch) == this.id)
                continue;

            try {
                ch.bw.write(json.toString());
                ch.bw.newLine();
                ch.bw.flush();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    /**
     * Update the map of ClientHandlers
     * @param clientHandlers the map of ClientHandlers to update
     */
    public void updateClientHandlers(BiHashMap<ClientHandler, Integer> clientHandlers) {
        this.clientHandlers = clientHandlers;
    }

    private void disconnect() {
        try {
            client.close();
        } catch (IOException ex) {}
        server.disconnect(this);
    }

    @Override
    public void run() {
        String message;
        while (true) {
            try {
                message = br.readLine();
                JSONObject json = new JSONObject(message);
                jsonReader.read(json.put("ClientId",id));
            } catch (IOException ex) {
                try {
                    br.close();
                    disconnect();
                    break;
                } catch (IOException e) {

                }
                ex.printStackTrace();
            }
        }
    }
}
