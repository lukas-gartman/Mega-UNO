package org.megauno.app.network;

import org.json.JSONObject;

import java.io.OutputStreamWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

//public class Client implements Runnable {
public class Client {
    private String hostname;
    private int port;

    public Client(String hostname, int port) {
        this.hostname = hostname;
        this.port = port;
    }

    public void sendMessage(String msg) {
        try {
            Socket socket = new Socket(hostname, port);

            OutputStreamWriter out = new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8);
            out.write(msg);
            out.close();

            socket.close();
        } catch (Exception ex) {
            System.out.println("[Client Exception] " + ex.getMessage());
        }
    }

    public void sendJSON(JSONObject json) {
        try {
            Socket socket = new Socket(hostname, port);

            OutputStreamWriter out = new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8);
            out.write(json.toString());
            out.close();

            socket.close();
        } catch (Exception ex) {
            System.out.println("[Client Exception] " + ex.getMessage());
        }
    }

//    @Override
//    public void run() {
//        try {
//            start(hostname, port);
//        } catch (Exception ex) {
//            System.out.println("[Client Exception] " + ex.getMessage());
//        }
//    }
}
