package org.megauno.app.viewcontroller;

import org.megauno.app.viewcontroller.datafetching.FontLoader;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

// Graphical element to represent another player
public class OtherPlayer extends Image {
	static BitmapFont font = new FontLoader("assets/").getDataFromPath("assets/minecraft.fnt");
	private int playerID;
	private int nCards = 0;

	public OtherPlayer(int playerID, int nCards) {
		this.playerID = playerID;
		this.nCards = nCards;
	}

	// Called by parent
	public void updateCardsShown(int nCards) {
		this.nCards = nCards;
	}

	@Override
 	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
		// Draw text as well
		font.draw(batch, "P" + playerID + ": " + Integer.toString(nCards), getX(), getY());
	}
}
