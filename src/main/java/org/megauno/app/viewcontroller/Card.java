package org.megauno.app.viewcontroller;

import org.megauno.app.model.Cards.ICard;
import org.megauno.app.viewcontroller.DataFetching.FontLoader;
import org.megauno.app.viewcontroller.DataFetching.SpriteLoader;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;

class Card extends Actor {
	static SpriteLoader spAssets = new SpriteLoader("assets/");
	static Sprite red = spAssets.getData("RedCard.png");
	static Sprite blue = spAssets.getData("BlueCard.png");
	static Sprite yellow = spAssets.getData("YellowCard.png");
	static Sprite green = spAssets.getData("GreenCard.png");
	static Sprite nonColored = spAssets.getData("WhiteCard.png");
	static BitmapFont fnt = new FontLoader("assets/").getDataFromPath("minecraft.fnt");

	Sprite sprite;
	ICard card;

	public Card(ICard card) {
		switch(card.getColor()) {
			case RED:
				sprite = red;
				break;
			case BLUE:
				sprite = blue;
				break;
			case YELLOW:
				sprite = yellow;
				break;
			case GREEN:
				sprite = green;
				break;
			case NONE:
				sprite = nonColored;
		}
		this.card = card;
	}

	@Override
	public void draw(Batch batch, float alpha) {
		batch.begin();
		// Draw card with it's color
		batch.draw(sprite, getX(), getY());
		// Draw number if number card
		if (card.getNumber() != null) {
			fnt.draw(batch, card.getNumber().toString(), getX(), getY());
		}
		// TODO: type image
		batch.end();
	}
}

