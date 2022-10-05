package org.megauno.app.viewcontroller;

import org.megauno.app.model.Cards.ICard;
import org.megauno.app.utility.dataFetching.DataFetcher;
import org.megauno.app.utility.dataFetching.PathDataFetcher;
import org.megauno.app.viewcontroller.datafetching.FontLoader;
import org.megauno.app.viewcontroller.datafetching.SpriteLoader;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Card extends Actor {
	static DataFetcher<String,Sprite> spriteFetcher = new PathDataFetcher(new SpriteLoader(),"assets/");
	static Sprite red = spriteFetcher.tryGetDataUnSafe("RedCard.png");
	static Sprite blue = spriteFetcher.tryGetDataUnSafe("BlueCard.png");
	static Sprite yellow = spriteFetcher.tryGetDataUnSafe("YellowCard.png");
	static Sprite green = spriteFetcher.tryGetDataUnSafe("GreenCard.png");
	static Sprite nonColored = spriteFetcher.tryGetDataUnSafe("WhiteCard.png");
	static DataFetcher<String,BitmapFont> fontFetcher = new PathDataFetcher<>(new FontLoader(),"assets/");
	static BitmapFont fnt = fontFetcher.tryGetDataUnSafe("minecraft.fnt");

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

	public ICard getCard(){
		return card.copyCard();
	}

	@Override
	public void draw(Batch batch, float alpha) {
		// Draw card with it's color
		batch.draw(sprite, getX(), getY());
		// Draw number if number card
		if (card.getNumber() != null) {
			fnt.draw(batch, card.getNumber().toString(), getX(), getY()+sprite.getHeight());
		}

		// TODO: type image
	}
}

