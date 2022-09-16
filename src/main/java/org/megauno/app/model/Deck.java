package org.megauno.app.model;

import org.megauno.app.model.Cards.ICard;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Stack;

public class Deck implements IDeck {
    private final Stack<ICard> cards = new Stack<>();
    private HashMap<ICard, Integer> probabilities;
    public Deck() {
        // default distribution
        this.probabilities = new HashMap();
//        distribution.put(NumberCard, 66); // 66% chance for a NumberCard
//        distribution.put(ActionCard, 33); // 33% chance for an ActionCard
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
