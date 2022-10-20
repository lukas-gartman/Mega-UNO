package org.megauno.app.network;

import org.json.JSONObject;
import org.megauno.app.network.Implementation.ClientHandler;
import org.megauno.app.utility.BiHashMap;

/**
 * A server that handles incoming Socket requests.
 */
public interface IServer {
    /**
     * Get a map of all the active client handlers
     * @return a list of client handlers
     */
    BiHashMap<ClientHandler, Integer> getClientHandlers();

    /**
     * Disconnect the given client from the server
     * @param client the client to disconnect
     */
    void disconnectClient(ClientHandler client);

    /**
     * Send a JSON object to all connected clients
     * @param json the object to send
     */
    void broadcast(JSONObject json);
}
