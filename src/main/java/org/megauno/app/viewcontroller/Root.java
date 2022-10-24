package org.megauno.app.viewcontroller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import org.lwjgl.opengl.GL20;
import org.megauno.app.application.ClientScreen;
import org.megauno.app.application.MegaUNO;
import org.megauno.app.viewcontroller.controller.GameController;
import org.megauno.app.viewcontroller.players.GameView;

public class Root {
	MegaUNO megaUNO;
	private Batch batch;
	private IDrawable drawable;

	public Root(MegaUNO megaUNO) {
		this.megaUNO = megaUNO;
		drawable = ((delta, batch) -> {
			ClientScreen.tomte.draw(batch);
		});
		batch = new SpriteBatch();
	}

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

	public void resize(int width, int height) {
		batch.getProjectionMatrix().setToOrtho2D(0, 0, width, height);
	}
}
