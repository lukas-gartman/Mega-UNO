package org.megauno.app.model.Game.Utilities;

import org.megauno.app.model.Cards.Color;
import org.megauno.app.model.Cards.ICard;
import org.megauno.app.model.Cards.Impl.NumberCard;
import org.megauno.app.model.Game.IPile;

import java.util.*;

/**
 * The concrete implementation of a pile.
 * Responsible for storing the top card and generating the first top card.
 */
public class Pile implements IPile {
    private final Stack<ICard> cards = new Stack<>();

    public Pile() {
        ICard firstCard = generateFirstCard();
        cards.push(firstCard);
    }

    // Generates the first card in the pile, only used when the game is initialized.
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

    @Override
    public void discard(ICard card) {
        cards.push(card);
    }

    @Override
    public ICard getTop() {
        if (!cards.isEmpty())
            return cards.peek();
        return null;
    }
}
