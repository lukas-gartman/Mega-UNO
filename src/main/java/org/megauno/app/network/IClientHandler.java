package org.megauno.app.network;

import org.json.JSONObject;

/**
 * Extension of Server that handles client connections.
 * @author Lukas Gartman
 */
public interface IClientHandler extends Runnable {
    /**
     * Send a JSON object to the client
     * @param json the object to send
     */
    void send(JSONObject json);

    /**
     * Disconnect the client
     */
    void disconnect();

    /**
     * Update the JSONReader
     * @param jr the JSONReader to be updated
     */
    void updateJSONReader(JSONReader jr);
}
