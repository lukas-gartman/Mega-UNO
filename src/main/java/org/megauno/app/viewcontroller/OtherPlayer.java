package org.megauno.app.viewcontroller;

import org.megauno.app.viewcontroller.DataFetching.FontLoader;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

// Graphical element to represent another player
public class OtherPlayer extends Image {
	private int playerID;
	private int nCards;
	private IGame game;

	static BitmapFont font = new FontLoader("assets/").getDataFromPath("assets/minecraft.fnt");

	public OtherPlayer(int playerID, IGame game) {
		this.playerID = playerID;
		this.game = game;
		updateCardsShown();
	}

	// At the moment, have a text indicator
	private void updateCardsShown() {
		
	}

	@Override
 	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
		// Draw text as well
		batch.begin();
		font.draw(batch, Integer.toString(nCards), getX(), getY());
		batch.end();
	}
}
