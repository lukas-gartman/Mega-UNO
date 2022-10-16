package org.megauno.app.network;

import org.megauno.app.utility.BiHashMap;

public interface IServer {
    public BiHashMap<ClientHandler, Integer> getClientHandlers();
    void disconnect(ClientHandler client);
}
