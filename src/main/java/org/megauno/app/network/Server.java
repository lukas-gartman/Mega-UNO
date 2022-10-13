package org.megauno.app.network;

import org.megauno.app.utility.Tuple;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Semaphore;

public class Server implements IServer, Runnable {
    public interface Observer {
        void update(Tuple<ClientHandler, Integer> clientHandler);
    }

    private final List<Observer> observers = new ArrayList<>();
    private ServerSocket server;
    private HashMap<ClientHandler, Integer> clientHandlers = new HashMap<>();
    private final Semaphore semaphore = new Semaphore(1);
    private static int cid = 0;

    public Server(int port) throws Exception {
        try {
            this.server = new ServerSocket(port);
        } catch (IOException ex) {
//            System.out.println("A server is already running on port " + port);
            throw new Exception("A server is already running on port " + port);
        } catch (IllegalArgumentException ex) {
            System.out.println("Unable to create server on port " + port + ". Please use a port within the range 0-65535.");
        }
    }

    private void notifyObservers(ClientHandler clientHandler, int id) {
        observers.forEach(observer -> observer.update(new Tuple<>(clientHandler, id)));
    }

    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    public void acceptConnections() {
        try {
            while (true) {
                Socket client = server.accept();
                try { semaphore.acquire(); } catch (InterruptedException ex) { }
                int id = cid++;
                ClientHandler clientHandler = new ClientHandler(client, id, this);
                clientHandlers.put(clientHandler, id);
                updateClientHandlers(); // update the ClientHandlers in ClientHandler
                notifyObservers(clientHandler, id);
                semaphore.release();
                new Thread(clientHandler).start();

                System.out.println("client connected");
            }
        } catch (IOException ex) {
            throw new RuntimeException("Server socket is closed");
        } catch (NullPointerException ex) {} // server was not setup properly, ignore...
    }

    public void disconnect(ClientHandler client) {
        try { semaphore.acquire(); } catch (InterruptedException ex) { }
        this.clientHandlers.remove(client);
        cid--;
        updateClientHandlers();
        notifyObservers(client, -1);
        System.out.println("client disconnected");
        semaphore.release();
    }

    // Make it the server's responsibility to keep track of ClientHandlers
    private void updateClientHandlers() {
        for (ClientHandler ch : clientHandlers.keySet())
            ch.updateClientHandlers(clientHandlers);
    }

    public HashMap<ClientHandler, Integer> getClientHandlers() {
        return this.clientHandlers;
    }

    @Override
    public void run() {
        acceptConnections();
    }
}
