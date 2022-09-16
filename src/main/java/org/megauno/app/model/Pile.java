package org.megauno.app.model;

import org.megauno.app.model.Cards.Color;
import org.megauno.app.model.Cards.ICard;
import org.megauno.app.model.Cards.NumberCard;

import java.util.Random;
import java.util.Stack;

public class Pile implements IPile {
    private final Stack<ICard> cards = new Stack<>();
    public Pile() {
        ICard firstCard = generateFirstCard();
        cards.push(firstCard);
    }

    private ICard generateFirstCard() {
        Random rand = new Random();
        int number = rand.nextInt(9);
        Color colors[] = Color.values();
        int colorIndex = rand.nextInt(colors.length);
        Color color = colors[colorIndex];
        
        return new NumberCard(color, number);
    }

    public void discard(ICard card) {
        cards.push(card);
    }

    public ICard getTop() {
        if (!cards.isEmpty())
            return cards.peek();
        return null;
    }
}
