package org.megauno.app.utility;

import org.megauno.app.model.Cards.ICard;
import org.megauno.app.viewcontroller.Card;

import java.util.ArrayList;
import java.util.List;

public class CardMethodes {
    public static ArrayList<ICard> copyCards (List<ICard> cards){
        ArrayList<ICard> copy = new ArrayList<>();
        for (ICard card: cards) {
            copy.add(card.copyCard());
        }
        return copy;
    }
    public static List<ICard> cardsDifference(List<ICard> oldCards, List<ICard> newCards){
        for (ICard oldCard: oldCards) {
            for (int i = 0; i < newCards.size(); i++) {
                ICard card = newCards.get(i);
                if (oldCard.equals(card)){
                    newCards.remove(card);
                }
            }
        }
        return newCards;
    }
    public static List<ICard> getRealCards(List<Card> cards){
        List<ICard> out = new ArrayList<>();
        for(Card card: cards){
            out.add(card.getCard());
        }
        return out;
    }
}
