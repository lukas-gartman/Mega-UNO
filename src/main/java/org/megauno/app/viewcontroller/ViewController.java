package org.megauno.app.viewcontroller;

import org.megauno.app.model.Game.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.MoveByAction;

import org.lwjgl.opengl.GL20;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import org.megauno.app.utility.ObserverPattern.Subscriber;
import org.megauno.app.viewcontroller.DataFetching.FontLoader;
import org.megauno.app.viewcontroller.DataFetching.SpriteLoader;

// The outer class managing views and controllers
public class ViewController implements Subscriber<Game> {
	private WilliamIGame game;

	private Stage gameStage;
	private Stage lobbyStage;

	// TODO: have empty constructor, get IGame from either controller (client)
	// or "Lobby" object of model (server) (subscribe to an event of "GameStarting")
	public ViewController(WilliamIGame game) {
		this.game = game;
		//gameStage = new GameView(game);
		//Gdx.input.setInputProcessor(stage); // The app's input processor is our stage
		//DummyActor dummyActor = new DummyActor();
		//stage.addActor(dummyActor);
		//stage.setKeyboardFocus(dummyActor);
	}

	// Necessary call from top level window handler (Application),
	// viewport of stage cannot handle this itself.
	public void resize(int width, int height) {
		//stage.getViewport().update(width, height, true);
	}

	// NOTE: the Application is supposed to call this every frame
	// where delta is the time it took between the last frame and the current
	public void draw() {
		// Clear screen
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		//stage.act(Gdx.graphics.getDeltaTime());
		//stage.draw();
	}

	// NOTE: called by Application
	// Application is supposed to call this when the game is about to quit
	public void teardown() {
	}

	@Override
	public void delivery(Game game) {
		//this.game = game;
		//TODO update view state
	}

	public class DummyActor extends Actor {
		static Sprite sprite = new SpriteLoader().getData("yay.jpg");
		static BitmapFont fnt = new FontLoader("assets/").getDataFromPath("assets/minecraft.fnt");


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
		public void draw(Batch batch, float parentAlpha) {
			super.draw(batch, parentAlpha);
			sprite.draw(batch);
			fnt.draw(batch, "Absolutely Amazing", Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2);
		}

		@Override
		protected void positionChanged() {
			sprite.setPosition(getX(), getY());
			super.positionChanged();
		}
	}
}
