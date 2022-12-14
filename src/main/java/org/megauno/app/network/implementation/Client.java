package org.megauno.app.network.implementation;

import org.json.JSONObject;
import org.megauno.app.network.JSONReader;

import java.io.*;
import java.net.ConnectException;
import java.net.Socket;

/**
 * A client used to create a connection to a ServerSocket.
 *
 * @author Lukas Gartman
 */
public class Client {
    private String nickname;
    private final String hostname;
    private final int port;
    private Socket server;
    private BufferedReader br;
    private BufferedWriter bw;
    private JSONReader jsonReader;
    private static int numOfRetryConnect = 0;

    /**
     * Set up client connection and listen to incoming messages from the server
     *
     * @param hostname   the hostname to connect to
     * @param port       the port to connect to
     * @param jsonReader an interface used for reading JSON
     */
    public Client(String hostname, int port, JSONReader jsonReader) throws ConnectException {
        this.hostname = hostname;
        this.port = port;
        this.jsonReader = jsonReader;
        if (connect())
            listen();
    }

    private boolean connect() throws ConnectException {
        try {
            this.server = new Socket(hostname, port); // Connect to the server
            this.bw = new BufferedWriter(new OutputStreamWriter(server.getOutputStream())); // Used for sending messages
            this.br = new BufferedReader(new InputStreamReader(server.getInputStream()));   // Used for reading messages
            return true;
        } catch (IOException e) {
            throw new ConnectException("Unable to connect to server @ " + hostname + ":" + port);
//            return false;
        }
    }

    public void disconnect() {
        try {
            this.server.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void listen() {
        // Create a new thread to prevent blocking
        new Thread(() -> {
            String message;
            while (true) {
                try {
                    message = this.br.readLine(); // Read input from server (blocking call)
                    if (message == null)
                        break;

                    JSONObject json = new JSONObject(message);
                    jsonReader.read(json);
                } catch (IOException ex) { // Unable to read from server
                    System.out.println("Client lost connection to server @ " + this.hostname + ":" + this.port);
                    break;
                }
            }
        }).start();
    }

    /**
     * Send a JSON object to the server
     *
     * @param json the object to send
     */
    public void sendJSON(JSONObject json) throws ConnectException {
        try {
            this.bw.write(json.toString());
            this.bw.newLine();
            this.bw.flush();
        } catch (Exception ex) {
            if (numOfRetryConnect++ <= 3) {
                System.out.println("Unable to reach server. Trying again...");
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                sendJSON(json);
            } else {
                throw new ConnectException("Unable to reach server");
            }
        }
    }

    /**
     * Get the client's nickname
     *
     * @return the nickname
     */
    public String getNickname() {
        return this.nickname;
    }
}
