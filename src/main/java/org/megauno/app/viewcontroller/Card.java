package org.megauno.app.viewcontroller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.system.CallbackI;
import org.megauno.app.model.Cards.CardType;
import org.megauno.app.model.Cards.Color;
import org.megauno.app.model.Cards.ICard;
import org.megauno.app.model.Game.Game;
import org.megauno.app.utility.dataFetching.DataFetcher;
import org.megauno.app.utility.dataFetching.PathDataFetcher;
import org.megauno.app.viewcontroller.datafetching.FontLoader;
import org.megauno.app.viewcontroller.datafetching.SpriteLoader;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Touchable;

public class Card implements IDrawable {
	static DataFetcher<String,Sprite> spriteFetcher = new PathDataFetcher<Sprite>(
			(key) ->  {
		return new Sprite(new Texture(key));
	},"assets/");

	Sprite red = spriteFetcher.tryGetDataUnSafe("RedCard.png");
	static Sprite blue = spriteFetcher.tryGetDataUnSafe("BlueCard.png");
	static Sprite yellow = spriteFetcher.tryGetDataUnSafe("YellowCard.png");
	static Sprite green = spriteFetcher.tryGetDataUnSafe("GreenCard.png");
	static Sprite nonColored = spriteFetcher.tryGetDataUnSafe("WhiteCard.png");
	static DataFetcher<String,BitmapFont> fontFetcher = new PathDataFetcher<BitmapFont>(
			(key) ->  {
				return new BitmapFont(Gdx.files.internal(key));
			},"assets/");
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
	// Null means there are no color options, otherwise it is a filled array
	private List<ColorOption> colorOptions = null;

	public Card(ICard card, Game game, int playerID, int cardID) {
		this.playerID = playerID;
		this.cardID = cardID;
		this.game = game;
		this.card = card;
		this.sprite = chooseSprite(card.getColor());
		this.clickable = new Clickable(sprite.getWidth(), sprite.getHeight());
		// setBounds(sprite.getX(), sprite.getY(), sprite.getWidth(),
		// sprite.getHeight());
		// setTouchable(Touchable.enabled);
	}

	private Sprite chooseSprite(Color color) {
		switch (color) {
			case RED:
				return red;
			case BLUE:
				return blue;
			case YELLOW:
				return yellow;
			case GREEN:
				return green;
			case NONE:
				return nonColored;
			default:
				return nonColored;
		}
	}

	public ICard getCard() {
		return card.copyCard();
	}

	@Override
	public void draw(float delta, Batch batch) {
		// Check if clicked
		if (clickable.wasClicked(x, y)) {
			System.out.println("Clicked card with ID: " + Integer.toString(cardID));
			// If wildcard: show color-selector
			if (card.getType() == CardType.WILDCARD) {
				if (colorOptions == null)
					showColorOptions();
				else
					hideColorOptions();
			}
			// Flip card selection in model and visually
			// game.choices[cardID] = !game.choices[cardID];
			else {
				selected = !selected;
				if (selected) {
					game.getPlayerWithId(playerID).selectCard(card);
				} else {
					game.getPlayerWithId(playerID).unSelectCard(card);
				}
			}
		}
		// Check if any cardOption was selected
		if (colorOptions != null) {
			for (ColorOption colorOption : colorOptions) {
				if (colorOption.wasSelected) {
					onColorSelected(colorOption.color);
				}
			}
		}


		// Draw card, with a tint if unselected
		if (!selected) {
			batch.setColor(new com.badlogic.gdx.graphics.Color(0.7f, 0.7f, 0.7f, 0.7f));   // A bit greyed out
			drawAll(batch);
			batch.setColor(com.badlogic.gdx.graphics.Color.WHITE);
		} else {
			drawAll(batch);
		}

		// Draw color options if a clicked wildcard
		if (colorOptions != null) {
			for (ColorOption colorOption : colorOptions) {
				colorOption.draw(delta, batch);
			}
		}
	}

	// Card and every sub-element, not color options
	private void drawAll(Batch batch) {
		batch.draw(sprite, x, y);
		// Draw number if number card
		if (card.getNumber() != null) {
			fnt.draw(batch, card.getNumber().toString(), x + 5, y + sprite.getHeight() - 5);
		}

		// Draw type of card, reverse, take 2 etc.
		String type = getTypeInString();

		if (type != null){
			batch.draw(spriteFetcher.tryGetDataUnSafe(type), x, y);
		}
	}

	// What happens when a color for a wildcard is slected
	private void onColorSelected(Color color) {
		//TODO: select color in Game
		game.setColor(color);
		game.getPlayerWithId(playerID).selectCard(card);
		hideColorOptions();
	}

	private void showColorOptions() {
		Color[] allColors = { Color.RED, Color.BLUE, Color.GREEN, Color.YELLOW };
		colorOptions = new ArrayList<>();
		for (int i = 0; i < allColors.length; i++) {
			Color c = allColors[i];
			colorOptions.add(new ColorOption(c, x + (40*i), y + 50));
		}
	}

	private void hideColorOptions() {
		// Garbage collector fixes the rest
		colorOptions = null;
	}

	// Gets corresponding filename of card
	private String getTypeInString(){
		String special = null;
		switch (card.getType()){
			case WILDCARD -> {
				special = "WildCard.png";
			}
			case REVERSECARD -> {
				special = "Reverse.png";
			}
			case TAKETWO-> {
				special = "Take2.png";
			}
			case TAKEFOUR -> {
				special = "Take4.png";
			}
		}
		return special;
	}

	// Visual class for representing a color picked when playing a wildcard
	class ColorOption implements IDrawable {
		// Used by parent card, could use an event here
		public boolean wasSelected = false;
		public Color color;

		private float x;
		private float y;
		private Clickable clickable;
		private Sprite sprite;
		public ColorOption(Color color, float x, float y) {
			this.color = color;
			this.sprite = chooseSprite(color);
			this.x = x;
			this.y = y;
			this.clickable = new Clickable(sprite.getWidth(), sprite.getHeight());
		}

		public void draw(float delta, Batch batch) {
			// Check if clicked
			if (clickable.wasClicked(x, y)) {
				wasSelected = true;
			}

			// Draw
			batch.draw(sprite, x, y);
		}
	}
}
