package org.megauno.app.viewcontroller.lobby;

import com.badlogic.gdx.graphics.g2d.Batch;
import org.megauno.app.viewcontroller.IDrawable;

// For now, GameView parses deltas from Game and calls the appropriate
public class LobbyView implements IDrawable {
    @Override
    public void draw(float delta, Batch batch) {
        // Draw background
        batch.draw(LobbyApplication.background, 0, 0, 1600, 900);
    }
}
