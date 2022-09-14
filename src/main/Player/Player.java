package Player;
//
public abstract class Player {

    //Bool for if the player has said uno this round
    private boolean saidUno = false;
    //The entity who makes the decisions
    DecisionMaker decisionMaker;
    //The hand of the player, has the cards
    Ihand hand;

     Player(Ihand hand, DecisionMaker decisionMaker) {
        this.hand = hand;
        this.decisionMaker = decisionMaker;
    }

    boolean isSaidUno() {
        return saidUno;
    }


    Icard[] getCards(){
        return hand.copyCards(hand.getCards());
    }
    void addCards(Icard[] cards){
        hand.addCards(hand.copyCards(cards));
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
        }
        return selectedCard;
    }



}
