package org.megauno.app.viewcontroller;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import org.megauno.app.ClientApplication;
import org.megauno.app.viewcontroller.datafetching.IDrawable;

class EndTurnButton implements IDrawable {
    public float x;
    public float y;
    private GameController gameController;

    private Clickable clickable;

    private Sprite sprite;

    private boolean clicked;

    private Preposition prep;

    public EndTurnButton(float x, float y, GameController gameController, Preposition prep) {
        sprite = ClientApplication.CommenceForth;
        this.x = x;
        this.y = y;
        this.gameController = gameController;
        this.prep = prep;
        clickable = new Clickable(sprite.getWidth(), sprite.getHeight());

        clicked = false;

    }


    @Override
    public void draw(float delta, Batch batch) {
        if (clickable.wasClicked(x, y)) {
            gameController.commenceForth();
            clicked = !clicked;

        }
        if (prep.value()) {
            sprite = ClientApplication.CommenceForth;
        } else {
            sprite = ClientApplication.Tomte;
        }

        batch.draw(sprite, x, y);
    }
}
