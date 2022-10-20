package org.megauno.app.viewcontroller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.MoveByAction;
import org.lwjgl.opengl.GL20;
import org.megauno.app.viewcontroller.datafetching.FontLoader;
import org.megauno.app.viewcontroller.datafetching.SpriteLoader;

import java.util.ArrayList;
import java.util.List;

// The outer class managing views and controllers
public class ViewController {
    private ViewPublisher publishers;
    private Batch batch;

    private GameView currentGameView;
    private List<GameView> gameViews = new ArrayList<GameView>();

    // TODO: have empty constructor, get IGame from either controller (client)
    // or "Lobby" object of model (server) (subscribe to an event of "GameStarting")
    public ViewController(int playerId, int[] ids, ViewPublisher publishers, GameController gameController) {
        this.publishers = publishers;
        // Create all game views, stored in gameViews

        batch = new SpriteBatch();
        currentGameView = new GameView(playerId, ids, publishers, gameController);

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

        // Remove game view if player is gone
        //Player[] allPlayers = game.getPlayers();
        List<GameView> gameViewToRemove = new ArrayList<>();
        for (GameView gameView : gameViews) {
            boolean playersGameViewExists = false;
            //for (Player player : allPlayers) {
            //if (player.getId() == gameView.getPlayerID()) {
            //	playersGameViewExists = true;
            //}
            //}
            if (!playersGameViewExists) {
                gameViewToRemove.add(gameView);
            }
        }
        for (GameView gameView : gameViewToRemove) {
            gameViews.remove(gameView);
        }

        // Draw a game view
        batch.begin();
        currentGameView.draw(Gdx.graphics.getDeltaTime(), batch);
        batch.end();
    }

    // NOTE: called by Application
    // Application is supposed to call this when the game is about to quit
    public void teardown() {
    }


    public void newPlayerId(int id) {
        for (GameView gameView : gameViews) {
            if (gameView.getPlayerID() == id) {
                currentGameView = gameView;
            }
        }
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
