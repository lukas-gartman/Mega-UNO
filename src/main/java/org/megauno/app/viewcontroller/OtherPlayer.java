package org.megauno.app.viewcontroller;

import com.badlogic.gdx.graphics.g2d.Sprite;
import org.megauno.app.utility.dataFetching.DataFetcher;
import org.megauno.app.utility.dataFetching.PathDataFetcher;
import org.megauno.app.viewcontroller.datafetching.FontLoader;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import org.megauno.app.viewcontroller.datafetching.SpriteLoader;

import java.util.ArrayList;
import java.util.List;

// Graphical element to represent another player
public class OtherPlayer implements IDrawable {
	static DataFetcher<String, BitmapFont> fontFetcher = new PathDataFetcher<BitmapFont>(new FontLoader(), "assets/");
	static BitmapFont font = fontFetcher.tryGetDataUnSafe("minecraft.fnt");
	static DataFetcher<String, Sprite> spriteLoader = new PathDataFetcher<Sprite>(new SpriteLoader(), "assets/");
	static Sprite cardBack = spriteLoader.tryGetDataSafe("Card.png");
	private int playerID;
	private List<Sprite> cards = new ArrayList<>();

	public float x;
	public float y;

	public OtherPlayer(int playerID, int nCards) {
		this.playerID = playerID;
		addCards(nCards);
	}

	// Adds cards to the player
	public void addCards(int newCards) {
		for (int i = 0; i < newCards; i++) {
			Sprite card = cardBack;
			cards.add(card);
		}

	}

	// Removes cards from the player
	public void removeCards(int removedCards) {
		for (int i = 0; i < removedCards; i++) {
			cards.remove(cards.size() - 1);
		}
	}

	public int getNrOfCard() {
		return cards.size();
	}

	public int getPlayerID() {
		return playerID;
	}

	@Override
	public void draw(float delta, Batch batch) {
		for (int i = 0; i < cards.size(); i++) {
			Sprite card = cards.get(i);
			card.setX(x + i * 20);
			card.setY(y);
			card.draw(batch);
		}
		// Draw text as well
		font.draw(batch, "P" + playerID + ": " + Integer.toString(cards.size()), x, y);
	}
}