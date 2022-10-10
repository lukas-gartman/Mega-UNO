package org.megauno.app.viewcontroller;

import org.megauno.app.model.Game.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.MoveByAction;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL20;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import org.megauno.app.utility.Subscriber;
import org.megauno.app.viewcontroller.datafetching.FontLoader;
import org.megauno.app.viewcontroller.datafetching.SpriteLoader;

// The outer class managing views and controllers
public class ViewController implements Subscriber<Game>{
	private Game game;
	private Batch batch;

	private GameView currentGameView;
	private List<GameView> gameViews = new ArrayList<GameView>();

	// TODO: have empty constructor, get IGame from either controller (client)
	// or "Lobby" object of model (server) (subscribe to an event of "GameStarting")
	public ViewController(Game game) {
		// Create all game views, stored in gameViews
		for (int i = 0; i < game.getPlayersLeft(); i++) {
			gameViews.add(new GameView(game, i));
		}
		currentGameView = gameViews.get(game.getCurrentPlayer());
		// Gdx.input.setInputProcessor(currentGameView);

		batch = new SpriteBatch();

		// DummyActor dummyActor = new DummyActor();
		// stage.addActor(dummyActor);
		// stage.setKeyboardFocus(dummyActor);

	}

	// Necessary call from top level window handler (Application),
	// viewport of stage cannot handle this itself.
	public void resize(int width, int height) {
		// stage.getViewport().update(width, height, true);
	}

	// NOTE: the Application is supposed to call this every frame
	// where delta is the time it took between the last frame and the current

	public void draw() {
		// Clear screen
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		// Draw a game view
		batch.begin();
		gameViews.get(game.getCurrentPlayer()).draw(Gdx.graphics.getDeltaTime(), batch);
		batch.end();
		currentGameView.update();
	}

	// NOTE: called by Application
	// Application is supposed to call this when the game is about to quit
	public void teardown() {
	}

	@Override
	public void delivery(Game game) {
		for(GameView gameView: gameViews){
			if(gameView.getPlayerID() == game.getCurrentPlayer()){
				currentGameView = gameView;
			}
		}
		Gdx.input.setInputProcessor(currentGameView);
		currentGameView.update();
	}


	public class DummyActor extends Actor {
		static Sprite sprite = new SpriteLoader().retrieveData("yay.jpg");
		static BitmapFont fnt = new FontLoader().retrieveData("assets/minecraft.fnt");

		public DummyActor() {
			setBounds(sprite.getX(), sprite.getY(), sprite.getWidth(), sprite.getHeight());
			setTouchable(Touchable.enabled);
			addListener(
					new InputListener() {
						@Override
						public boolean keyDown(InputEvent event, int keycode) {
							if (keycode == Input.Keys.RIGHT) {
								MoveByAction mba = new MoveByAction();
								mba.setAmount(100f, 0f);
								mba.setDuration(5f);
								DummyActor.this.addAction(mba);
							}
							return true;
						}
					});
		}

		@Override
		protected void positionChanged() {
			sprite.setPosition(getX(), getY());
			super.positionChanged();
		}
	}
}
