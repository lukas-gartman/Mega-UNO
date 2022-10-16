package org.megauno.app.viewcontroller.players.thisPlayer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import org.megauno.app.model.Cards.ICard;
import org.megauno.app.utility.dataFetching.DataFetcher;
import org.megauno.app.utility.dataFetching.PathDataFetcher;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import org.megauno.app.viewcontroller.Clickable;
import org.megauno.app.viewcontroller.GameController;
import org.megauno.app.viewcontroller.GamePublishers;
import org.megauno.app.viewcontroller.datafetching.IDrawable;

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

	public float x;
	public float y;
	public boolean selected = false;

	private Sprite sprite;
	private ICard card;
	private Clickable clickable;
	private GameController gameController;
	private int cardId;

	public Card(ICard card, int cardId, GameController gameController) {
		this.gameController = gameController;
		this.cardId = cardId;
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

	public int getCardId(){
		return cardId;
	}

	public ICard getCard() {
		return card.copyCard();
	}

	@Override
	public void draw(float delta, Batch batch) {
		// Check if clicked
		if (clickable.wasClicked(x, y)) {

			// Flip card selection in model and visually
			selected = !selected;
			if (selected) {
				gameController.selectCard(cardId);
			} else {
				gameController.unSelectCard(cardId);
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

		String type = getTypeInString();

		if (type != null){
			batch.draw(spriteFetcher.tryGetDataUnSafe(type), x, y);
		}

		// TODO: type image
	}

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

}
