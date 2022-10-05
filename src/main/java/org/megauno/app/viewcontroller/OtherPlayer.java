package org.megauno.app.viewcontroller;

import com.badlogic.gdx.graphics.g2d.Sprite;
import org.megauno.app.viewcontroller.datafetching.FontLoader;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import org.megauno.app.viewcontroller.datafetching.SpriteLoader;

import java.util.ArrayList;
import java.util.List;

// Graphical element to represent another player
public class OtherPlayer extends Image {
	static BitmapFont font = new FontLoader("assets/").getDataFromPath("assets/minecraft.fnt");
	static Sprite cardBack = new SpriteLoader().getData("assets/Card.png");
	private int playerID;
	private List<Sprite> cards = new ArrayList<>();

	public OtherPlayer(int playerID, int nCards) {
		this.playerID = playerID;
		addCards(nCards);
	}

	//Adds cards to the player
	public void addCards(int newCards){
		for(int i = 0; i < newCards; i++){
			Sprite card = cardBack;
			cards.add(card);
		}

	}

	//Removes cards from the player
	public void removeCards(int removedCards){
		for(int i = 0; i < removedCards; i++){
			cards.remove(cards.size()-1);
		}
	}

	public int getNrOfCard(){
		return cards.size();
	}

	@Override
 	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
		for (int i = 0; i < cards.size(); i++) {
			Sprite card = cards.get(i);
			card.setX(getX() + i * 20);
			card.setY(getY());
			card.draw(batch, parentAlpha);
		}
		// Draw text as well
		font.draw(batch, "P" + playerID + ": " + Integer.toString(cards.size()), getX(), getY());
	}

}
