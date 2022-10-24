package org.megauno.app.viewcontroller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import org.lwjgl.opengl.GL20;
import org.megauno.app.application.ClientApplication;
import org.megauno.app.viewcontroller.controller.GameController;
import org.megauno.app.viewcontroller.players.GameView;

/**
 * This class exists so that something can be drawn before the game starts.
 */
public class Root {
	private Batch batch;
	private IDrawable drawable;

	public Root() {
		drawable = ((delta, batch) -> {
			ClientApplication.tomte.draw(batch);
		});
		batch = new SpriteBatch();
	}

	// Create GameView
	public void start(int playerID, int[] otherPlayersIds, GameController gameController, ViewPublisher viewPublisher) {
		drawable = new GameView(playerID, otherPlayersIds, viewPublisher, gameController);
	}

	public void draw() {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		// Draw a game view
		batch.begin();
		drawable.draw(Gdx.graphics.getDeltaTime(), batch);
		batch.end();
	}
}
