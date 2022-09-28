package org.megauno.app.network;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server implements Runnable {
    private final ServerSocket server;
    private static int cid = 1;

    public Server(int port) {
        try {
            this.server = new ServerSocket(port);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void startServer() {
        try {
            while (true) {
                Socket client = server.accept();
                System.out.println("client connected");
                ClientHandler clientHandler = new ClientHandler(client, cid++);
                new Thread(clientHandler).start();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void run() {
        startServer();
    }
}
