package org.megauno.app.network;

import org.megauno.app.utility.Tuple;

import java.util.HashMap;

public interface IServer {
    interface Observer { void update(Tuple<ClientHandler, Integer> clientHandler); }
    void addObserver(Server.Observer observer);
    HashMap<ClientHandler, Integer> getClientHandlers();
    void disconnect(ClientHandler client);
}
