package org.megauno.app.viewcontroller;

import org.megauno.app.model.Cards.ICard;
import org.megauno.app.utility.dataFetching.DataFetcher;
import org.megauno.app.utility.dataFetching.PathDataFetcher;
import org.megauno.app.viewcontroller.datafetching.FontLoader;
import org.megauno.app.viewcontroller.datafetching.SpriteLoader;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Touchable;

public class Card extends Actor {
	static DataFetcher<String,Sprite> spriteFetcher = new PathDataFetcher(new SpriteLoader(),"assets/");
	static Sprite red = spriteFetcher.tryGetDataUnSafe("RedCard.png");
	static Sprite blue = spriteFetcher.tryGetDataUnSafe("BlueCard.png");
	static Sprite yellow = spriteFetcher.tryGetDataUnSafe("YellowCard.png");
	static Sprite green = spriteFetcher.tryGetDataUnSafe("GreenCard.png");
	static Sprite nonColored = spriteFetcher.tryGetDataUnSafe("WhiteCard.png");
	static DataFetcher<String,BitmapFont> fontFetcher = new PathDataFetcher<>(new FontLoader(),"assets/");
	static BitmapFont fnt = fontFetcher.tryGetDataUnSafe("minecraft.fnt");

	public boolean selected = false;

	private Sprite sprite;
	private ICard card;

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
		setBounds(sprite.getX(), sprite.getY(), sprite.getWidth(), sprite.getHeight());
		setTouchable(Touchable.enabled);
	}

	public ICard getCard(){
		return card.copyCard();
	}

	@Override
	public void draw(Batch batch, float alpha) {
		// Draw card, with a tint if unselected
		if (!selected) {
			System.out.println("Not Selected draw");
			batch.setColor(new Color(0.7f, 0.7f, 0.7f, 0.7f));   // A bit greyed out
			batch.draw(sprite, getX(), getY());
			batch.setColor(Color.WHITE);
		} else {
			System.out.println("Selected draw");
			batch.draw(sprite, getX(), getY());
		}
		// Draw number if number card
		if (card.getNumber() != null) {
			fnt.draw(batch, card.getNumber().toString(), getX(), getY()+sprite.getHeight());
		}

		// TODO: type image
	}
}

