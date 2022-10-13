package org.megauno.app.network;

import org.megauno.app.utility.Tuple;

import java.util.HashMap;

public interface IServer {
    HashMap<ClientHandler, Integer> getClientHandlers();
    void disconnect(ClientHandler client);
}
