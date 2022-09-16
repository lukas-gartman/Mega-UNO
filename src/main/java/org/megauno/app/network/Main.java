package org.megauno.app.network;

import org.json.JSONObject;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        Thread server = new Thread(new Server(1337));
        server.start();

        Client client = new Client("localhost", 1337);

        JSONObject json = new JSONObject();
        json.put("type", "CONNECT");
        json.put("message", "suh, dude!");
        client.sendJSON(json);

        server.join();
    }
}
