package org.megauno.app;

import org.megauno.app.model.Game.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.Gdx;

// The outer class managing views and controllers
public class ViewController {
    Game game;
    SpriteBatch batch;
    Texture img;
    // NOTE: the view-controller should take a Model as an argument
    public ViewController(Game game) {
        this.game = game;
        batch = new SpriteBatch();
        img = new Texture("yay.jpg");
    }

    // NOTE: the Application is supposed to call this every frame
    // where delta is the time it took between the last frame and the current
    public void draw() {
        float delta = Gdx.graphics.getDeltaTime();
        ScreenUtils.clear(1, 0, 0, 1);
        batch.begin();
        batch.draw(img, 0, 0);
        batch.end();
    }

    // Application is supposed to call this when the game is about to quit
    public void teardown() {
        batch.dispose();
        img.dispose();
    }
}