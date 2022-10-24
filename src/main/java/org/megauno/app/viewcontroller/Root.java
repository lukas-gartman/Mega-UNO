package org.megauno.app.viewcontroller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import org.lwjgl.opengl.GL20;
import org.megauno.app.application.ClientScreen;
import org.megauno.app.application.MegaUNO;
import org.megauno.app.viewcontroller.controller.GameController;
import org.megauno.app.viewcontroller.players.GameView;

/**
 * This class exists so that something can be drawn before the game starts.
 */
public class Root {
	private MegaUNO megaUNO;
	private Batch batch;
	private IDrawable drawable;

	public Root(MegaUNO megaUNO) {
		this.megaUNO = megaUNO;
		drawable = ((delta, batch) -> {
			ClientScreen.tomte.draw(batch);
		});
		batch = new SpriteBatch();
	}

	/**
	 * Called by client when the initial state of the game exists, that state
	 * is provided as arguments.
	 */
	public void start(int playerID, HashMap<Integer, String> otherPlayersIds, GameController gameController, ViewPublisher viewPublisher) {
		drawable = new GameView(playerID, otherPlayersIds, viewPublisher, gameController);
	}

	/**
	 * Application is expected to call this function about 60 times per second.
	 */
	public void draw() {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		// Draw a game view
		batch.begin();
		drawable.draw(Gdx.graphics.getDeltaTime(), batch);
		batch.end();
	}

	public void resize(int width, int height) {
		batch.getProjectionMatrix().setToOrtho2D(0, 0, width, height);
	}
}
