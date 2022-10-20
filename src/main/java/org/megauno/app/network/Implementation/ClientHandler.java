package org.megauno.app.network.Implementation;

import org.json.JSONObject;
import org.megauno.app.network.IServer;
import org.megauno.app.network.JSONReader;

import java.io.*;
import java.net.Socket;

public class ClientHandler implements IClientHandler {
    private IServer server;
    private final Socket client;
    private BufferedReader br;
    private BufferedWriter bw;
    private final int id;
    private JSONReader jsonReader;

    /**
     * Store the client information and set up readers and writers
     *
     * @param client     the client's socket
     * @param id         the client ID
     * @param server     the server interface
     * @param jsonReader an interface used for reading JSON
     */
    public ClientHandler(Socket client, int id, IServer server, JSONReader jsonReader) {
        this.client = client;
        this.id = id;
        this.server = server;
        this.jsonReader = jsonReader;
        try {
            this.bw = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
            this.br = new BufferedReader(new InputStreamReader(client.getInputStream()));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void send(JSONObject json) {
        try {
            bw.write(json.toString());
            bw.newLine();
            bw.flush();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void disconnect() {
        try {
            client.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void updateJSONReader(JSONReader jr) {
        this.jsonReader = jr;
    }

    @Override
    public void run() {
        String message;
        while (true) {
            try {
                message = br.readLine();
                JSONObject json = new JSONObject(message);
                jsonReader.read(json.put("ClientId", id));

            } catch (IOException ex) {
                try {
                    br.close();
                    break;
                } catch (IOException e) {
                }
                ex.printStackTrace();
            }
        }
    }
}
