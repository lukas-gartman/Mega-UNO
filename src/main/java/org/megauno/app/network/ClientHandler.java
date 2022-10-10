package org.megauno.app.network;

import org.json.JSONObject;

import java.io.*;
import java.net.Socket;
import java.util.HashMap;

public class ClientHandler implements Runnable {
    private IServer server;
    private HashMap<ClientHandler, Integer> clientHandlers;
    private final Socket client;
    private BufferedReader br;
    private BufferedWriter bw;
    private final int id;

    public ClientHandler(Socket client, int id, IServer server) {
        this.client = client;
        this.id = id;
        this.server = server;
        this.clientHandlers = new HashMap<>();
        try {
            this.bw = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
            this.br = new BufferedReader(new InputStreamReader(client.getInputStream()));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void broadcast(JSONObject json) {
        for (ClientHandler ch : clientHandlers.keySet()) {
            if (clientHandlers.get(ch) == this.id)
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

    public void updateClientHandlers(HashMap<ClientHandler, Integer> clientHandlers) {
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
                // todo: do something with json object
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
