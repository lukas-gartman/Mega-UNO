package org.megauno.app.viewcontroller.players.thisPlayer;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import org.megauno.app.application.ClientApplication;
import org.megauno.app.model.cards.CardType;
import org.megauno.app.model.cards.Color;
import org.megauno.app.model.cards.ICard;
import org.megauno.app.viewcontroller.controller.Clickable;
import org.megauno.app.viewcontroller.controller.GameController;
import org.megauno.app.viewcontroller.IDrawable;

import java.util.ArrayList;
import java.util.List;

/**
 * Displays a card and handles what happens when you click it.
 * Also contains the ClorOption, which shows color options for wildcards.
 */
public class Card implements IDrawable {


    static Sprite red = ClientApplication.redCard;
    static Sprite blue = ClientApplication.blueCard;
    static Sprite yellow = ClientApplication.yellowCard;
    static Sprite green = ClientApplication.greenCard;
    static Sprite nonColored = ClientApplication.whiteCard;
    static BitmapFont fnt = ClientApplication.minecraftFont;
    static Sprite wildCard = ClientApplication.wildCard;
    static Sprite reverse = ClientApplication.reverse;
    static Sprite takeTwo = ClientApplication.take2;
    static Sprite takeFour = ClientApplication.take4;


    public float x;
    public float y;
    public boolean selected = false;

    private Sprite sprite;
    private ICard card;
    private Clickable clickable;
    private GameController gameController;
    private int cardId;
    // Null means there are no color options, otherwise it is a filled array
    private List<ColorOption> colorOptions = null;

    public Card(ICard card, int cardId, GameController gameController) {
        this.gameController = gameController;
        this.cardId = cardId;
        this.card = card;
        this.sprite = chooseSprite(card.getColor());
        this.clickable = new Clickable(sprite.getWidth(), sprite.getHeight());
        // setBounds(sprite.getX(), sprite.getY(), sprite.getWidth(),
        // sprite.getHeight());
        // setTouchable(Touchable.enabled);
    }

    public int getCardId() {
        return cardId;
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

            System.out.println("Clicked card with ID: " + Integer.toString(cardId));
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
                    gameController.selectCard(cardId);
                } else {
                    gameController.unSelectCard(cardId);
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
        Sprite type = getTypeInString();

        if (type != null) {
            batch.draw(type, x, y);
        }
    }

    // What happens when a color for a wildcard is slected
    private void onColorSelected(Color color) {
        //TODO: select color in Game
        gameController.setColor(color);
        gameController.selectCard(cardId);
        hideColorOptions();
    }

    private void showColorOptions() {
        Color[] allColors = {Color.RED, Color.BLUE, Color.GREEN, Color.YELLOW};
        colorOptions = new ArrayList<>();
        for (int i = 0; i < allColors.length; i++) {
            Color c = allColors[i];
            colorOptions.add(new ColorOption(c, x + (40 * i), y + 50));
        }
    }

    private void hideColorOptions() {
        // Garbage collector fixes the rest
        colorOptions = null;
    }

    // Gets corresponding filename of card
    private Sprite getTypeInString() {
        Sprite special = null;
        switch (card.getType()) {
            case WILDCARD ->
                special = wildCard;
            case REVERSECARD ->
                special = reverse;
            case TAKETWO ->
                special = takeTwo;
            case TAKEFOUR ->
                special = takeFour;
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
