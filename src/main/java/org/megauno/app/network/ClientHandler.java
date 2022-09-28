package org.megauno.app.network;

import org.json.JSONObject;

import java.io.*;
import java.net.Socket;
import java.util.HashMap;

public class ClientHandler implements Runnable {
    private static final HashMap<ClientHandler, Integer> clientHandlers = new HashMap<>();
    private final Socket client;
    private BufferedReader br;
    private BufferedWriter bw;
    private final int id;

    public ClientHandler(Socket client, int id) {
        this.client = client;
        this.id = id;
        try {
            this.bw = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
            this.br = new BufferedReader(new InputStreamReader(client.getInputStream()));
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        clientHandlers.put(this, id);
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

    private void disconnect() {
        clientHandlers.remove(this);
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
                ex.printStackTrace();
            }
        }
    }
}
