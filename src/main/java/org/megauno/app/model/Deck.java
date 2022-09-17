package org.megauno.app.model;

import org.megauno.app.model.Cards.ICard;
import org.megauno.app.model.Cards.ActionCard;
import org.megauno.app.model.Cards.NumberCard;
import org.megauno.app.model.Cards.Color;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class Deck implements IDeck {
    private HashMap<ICard, Integer> probabilities;
    public Deck() {
        // default distribution
        this.probabilities = new HashMap();

        List<ICard> numberCards = generateNumberCards();
        List<ICard> actionCards = generateActionCards();

        for (ICard card : numberCards)
            probabilities.put(card, 6);
        for (ICard card : actionCards)
            probabilities.put(card, 3);
    }

    private List<ICard> generateNumberCards() {
        List<ICard> numberCards = new ArrayList<>();
        for (Color colour : Color.values())
            for (int num = 0; num <= 9; num++)
                numberCards.add(new NumberCard(colour, num));
        return numberCards;
    }

    private List<ICard> generateActionCards() {
        List<ICard> actionCards = new ArrayList<>();
        // todo: create action cards (need to create actions first)
        return actionCards;
    }

    public Deck(HashMap dist) {
        this.probabilities = dist;
    }

    public ICard drawCard() {
        ArrayList<ICard> cardProbs = new ArrayList<>();
        for (ICard c : probabilities.keySet())
            for (int i = 0; i < probabilities.get(c); i++)
                cardProbs.add(c);

        Random rand = new Random();
        int index = rand.nextInt(cardProbs.size());
        ICard randomCard = cardProbs.get(index);

        return randomCard;
    }
}
