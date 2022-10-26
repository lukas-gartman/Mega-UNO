package org.megauno.app.application;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import org.megauno.app.viewcontroller.LobbyScreen;

public class MegaUNO extends Game {
    public final int WINDOW_WIDTH = 1600;
    public final int WINDOW_HEIGHT = 900;
    private LobbyScreen lobbyScreen;

    @Override
    public void create() {
        lobbyScreen = new LobbyScreen(this);
        setScreen(lobbyScreen);
    }

    /**
     * Set the screen to show the lobby
     */
    public void goToLobby() {
        setScreen(lobbyScreen);
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void dispose() {
        lobbyScreen.dispose();
        super.dispose();
    }
}
