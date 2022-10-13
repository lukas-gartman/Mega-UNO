package org.megauno.app.network;

import java.util.HashMap;

public interface IServer {
    HashMap<ClientHandler, Integer> getClientHandlers();
    void disconnect(ClientHandler client);
}
