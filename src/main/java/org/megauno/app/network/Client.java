package org.megauno.app.network;

import org.json.JSONObject;
import org.megauno.app.viewcontroller.Root;

import java.io.*;
import java.net.Socket;


public class Client {
    private final String hostname;
    private final int port;

    private Socket server;
    private BufferedReader br;
    private BufferedWriter bw;
    private JSONReader jsonReader;

    public Client(String hostname, int port, JSONReader jsonReader){
        this.hostname = hostname;
        this.port = port;
        this.jsonReader = jsonReader;
        if (connect())
            listen();
    }

    private boolean connect() {
        try {
            this.server = new Socket(hostname, port);
            this.bw = new BufferedWriter(new OutputStreamWriter(server.getOutputStream()));
            this.br = new BufferedReader(new InputStreamReader(server.getInputStream()));
            return true;
        } catch (IOException e) {
            System.out.println("Unable to connect to server @ " + hostname + ":" + port);
            return false;
        }
    }

    private void listen() {
        new Thread(() -> {
            String message;
            while (true) {
                try {
                    message = this.br.readLine();
                    JSONObject json = new JSONObject(message);
                    jsonReader.read(json);
                } catch (IOException ex) {
                    System.out.println("Client lost connection to server @ " + this.hostname + ":" + this.port);
                    break;
                }
            }
        }).start();
    }

    public void sendJSON(JSONObject json) {
        try {
            this.bw.write(json.toString());
            this.bw.newLine();
            this.bw.flush();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
