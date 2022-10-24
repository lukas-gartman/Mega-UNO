package org.megauno.app.viewcontroller;

import com.badlogic.gdx.graphics.g2d.Batch;

/**
 * Interface for objects that can be drawn on the screen.
 */
public interface IDrawable {
    void draw(float delta, Batch batch);
}

