package org.megauno.app.model;

import org.megauno.app.model.Cards.Color;
import org.megauno.app.model.Cards.ICard;
import org.megauno.app.model.Cards.Impl.NumberCard;

import java.util.*;

public class Pile implements IPile {
    private final Stack<ICard> cards = new Stack<>();
    public Pile() {
        ICard firstCard = generateFirstCard();
        cards.push(firstCard);
    }

    private ICard generateFirstCard() {
        Random rand = new Random();
        int number = rand.nextInt(1, 9);
        List<Color> colors = new ArrayList<>();
        // remove none from colors, this should later on be a separate method/getter
        Arrays.stream(Color.values()).filter(c -> c != Color.NONE).forEach(colors::add);
        int colorIndex = rand.nextInt(colors.size());
        Color color = colors.get(colorIndex);
        
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
