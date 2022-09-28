package org.megauno.app.network;

import org.json.JSONObject;
import java.io.*;
import java.net.Socket;

public class Client {
    private final Socket server;
    private final BufferedReader br;
    private final BufferedWriter bw;

    public Client(String hostname, int port) {
        try {
            this.server = new Socket(hostname, port);
            this.bw = new BufferedWriter(new OutputStreamWriter(server.getOutputStream()));
            this.br = new BufferedReader(new InputStreamReader(server.getInputStream()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        listen();
    }

    private void listen() {
        new Thread(() -> {
            String message;
            while (true) {
                try {
                    message = this.br.readLine();
                    JSONObject json = new JSONObject(message);
                    // todo: do something with json object
                } catch (IOException ex) {
                    ex.printStackTrace();
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
