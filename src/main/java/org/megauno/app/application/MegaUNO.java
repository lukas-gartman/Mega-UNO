package org.megauno.app.application;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MegaUNO extends Game {
    public final int WINDOW_WIDTH = 1600;
    public final int WINDOW_HEIGHT = 900;
    private LobbyScreen lobbyScreen;

    public SpriteBatch batch;

    @Override
    public void create() {
        batch = new SpriteBatch();
        lobbyScreen = new LobbyScreen(this);
        setScreen(lobbyScreen);
    }

    public void goToLobby() {
        setScreen(lobbyScreen);
        Gdx.graphics.setWindowedMode(1600, 900);
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void dispose() {
        batch.dispose();
        lobbyScreen.dispose();
        super.dispose();
    }
}
