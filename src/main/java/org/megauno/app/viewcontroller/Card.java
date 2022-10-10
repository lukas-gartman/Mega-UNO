package org.megauno.app.viewcontroller;

import org.megauno.app.model.Cards.ICard;
import org.megauno.app.model.Game.Game;
import org.megauno.app.utility.dataFetching.DataFetcher;
import org.megauno.app.utility.dataFetching.PathDataFetcher;
import org.megauno.app.viewcontroller.datafetching.FontLoader;
import org.megauno.app.viewcontroller.datafetching.SpriteLoader;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Touchable;

public class Card implements IDrawable {
	static DataFetcher<String, Sprite> spriteFetcher = new PathDataFetcher(new SpriteLoader(), "assets/");
	static Sprite red = spriteFetcher.tryGetDataUnSafe("RedCard.png");
	static Sprite blue = spriteFetcher.tryGetDataUnSafe("BlueCard.png");
	static Sprite yellow = spriteFetcher.tryGetDataUnSafe("YellowCard.png");
	static Sprite green = spriteFetcher.tryGetDataUnSafe("GreenCard.png");
	static Sprite nonColored = spriteFetcher.tryGetDataUnSafe("WhiteCard.png");
	static DataFetcher<String, BitmapFont> fontFetcher = new PathDataFetcher<>(new FontLoader(), "assets/");
	static BitmapFont fnt = fontFetcher.tryGetDataUnSafe("minecraft.fnt");

	public int cardID;
	public float x;
	public float y;
	public boolean selected = false;

	private Sprite sprite;
	private ICard card;
	private int playerID;
	private Game game;
	private Clickable clickable;

	public Card(ICard card, Game game, int playerID, int cardID) {
		this.playerID = playerID;
		this.cardID = cardID;
		this.game = game;
		switch (card.getColor()) {
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
		this.clickable = new Clickable(sprite.getWidth(), sprite.getHeight());
		// setBounds(sprite.getX(), sprite.getY(), sprite.getWidth(),
		// sprite.getHeight());
		// setTouchable(Touchable.enabled);
	}

	public ICard getCard() {
		return card.copyCard();
	}

	@Override
	public void draw(float delta, Batch batch) {
		// Check if clicked
		if (clickable.wasClicked(x, y)) {
			System.out.println("Clicked card with ID: " + Integer.toString(cardID));
			// Flip card selection in model and visually
			// game.choices[cardID] = !game.choices[cardID];
			selected = !selected;
			if (selected) {
				game.getPlayerWithId(playerID).selectCard(card);
			} else {
				game.getPlayerWithId(playerID).unSelectCard(card);
			}
		}

		// Draw card, with a tint if unselected
		if (!selected) {
			batch.setColor(new Color(0.7f, 0.7f, 0.7f, 0.7f));   // A bit greyed out
			batch.draw(sprite, x, y);
			batch.setColor(Color.WHITE);
		} else {
			batch.draw(sprite, x, y);
		}
		// Draw number if number card
		if (card.getNumber() != null) {
			fnt.draw(batch, card.getNumber().toString(), x, y + sprite.getHeight());
		}

		// TODO: type image
	}

	// Bounding box, used to know if it was clicked or not
	public class Clickable {
		public float width;
		public float height;

		public Clickable(float width, float height) {
			this.width = width;
			this.height = height;
		}

		public boolean wasClicked(float x, float y) {
			if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
				// Note that the input coordinate system is flipped in the y axis
				float input_x = Gdx.input.getX();
				float input_y = Gdx.graphics.getHeight() - Gdx.input.getY();

				float xLeft = x;
				float xRight = x + width;
				float yBottom = y;
				float yTop = y + height;

				return (input_x > xLeft && input_x < xRight) && (input_y > yBottom && input_y < yTop);
			}
			return false;
		}
	}
}
