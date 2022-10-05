package org.megauno.app.viewcontroller;

import org.megauno.app.model.Cards.ICard;
import org.megauno.app.viewcontroller.datafetching.FontLoader;
import org.megauno.app.viewcontroller.datafetching.SpriteLoader;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Touchable;

class Card extends Actor {
	static Sprite red = new SpriteLoader().getData("assets/RedCard.png");
	static Sprite blue = new SpriteLoader().getData("assets/BlueCard.png");
	static Sprite yellow = new SpriteLoader().getData("assets/YellowCard.png");
	static Sprite green = new SpriteLoader().getData("assets/GreenCard.png");
	static Sprite nonColored = new SpriteLoader().getData("assets/WhiteCard.png");
	static BitmapFont fnt = new FontLoader("assets/").getDataFromPath("minecraft.fnt");

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
			fnt.draw(batch, card.getNumber().toString(), getX() + 20, getY());
		}
		// TODO: type image
	}
}

