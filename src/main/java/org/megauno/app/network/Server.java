package org.megauno.app.network;

import org.json.JSONObject;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server implements Runnable {
    private static ServerSocket server;
    private int port;

    public Server(int port) {
        this.port = port;
    }

    private void start(int port) throws IOException {
        server = new ServerSocket(port);

        while (true) {
            Socket socket = server.accept();

            BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String line = br.readLine();
            JSONObject jsonObject = new JSONObject(line);

            System.out.println(jsonObject.get("message"));

            br.close();
            socket.close();
        }
    }


    @Override
    public void run() {
        try {
            start(port);
        } catch (Exception ex) {
            System.out.println("[Server Exception] " + ex.getMessage());
        }
    }
}
