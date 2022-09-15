package Player;

import java.util.ArrayList;

//
public abstract class Player {

    //Bool for if the player has said uno this round
    private boolean saidUno = false;
    //The entity who makes the decisions
    private DecisionMaker decisionMaker;
    //The hand of the player, has the cards
    private ArrayList<Icard> hand;

     Player(ArrayList<Icard> hand, DecisionMaker decisionMaker) {
        this.hand = hand;
        this.decisionMaker = decisionMaker;
    }

    boolean isSaidUno() {
        return saidUno;
    }


    ArrayList<Icard> getCards(){
        return copyCards(hand);
    }
    void addCard(Icard card){
         hand.add(card.copyCard());
    }


    //To get the card a player wants to play
    Icard play(Icard topCard){
        saidUno = decisionMaker.saidUno();
        boolean playable = false;
        Icard selectedCard;
        selectedCard = decisionMaker.ChooseCard();
        playable = selectedCard.canBePlayedOn(topCard);
        while(!playable){
            selectedCard = decisionMaker.ChooseCard();
            playable = selectedCard.canBePlayedOn(topCard);
            if(playable){
                playable = hand.remove(selectedCard);
            }
        }
        hand.remove(selectedCard);
        return selectedCard;
    }
    private ArrayList<Icard> copyCards(ArrayList<Icard> cards){
        ArrayList<Icard> copy = new ArrayList<>();
        for (Icard card: cards) {
            copy.add(card.copyCard());
        }
        return copy;
    }


}
