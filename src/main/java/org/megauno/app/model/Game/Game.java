package org.megauno.app.model.Game;

import org.megauno.app.model.Cards.ICard;
import org.megauno.app.model.Deck;
import org.megauno.app.model.Pile;

import java.util.List;

public class Game {
    PlayerCircle players;
    //ICard top;
    Deck deck;
    Pile discarded;

    public Game(){
        discarded = new Pile();
    }

    public void reverse(){
        players.changeRotation();
    }

     private boolean validPlayedCards(List<ICard> play){
        ICard top = discarded.getTop();
        if(play.size() == 0){
            return false;
        }else if (!play.get(0).canBePlayed(top)){
            return false;
        }
        for(int i = 1; i < play.size(); i++){
            if(!play.get(i).canBeStacked(play.get(i-1))){
                return false;
            }
        }
        return true;
    }

    public void play() {
        ICard top = discarded.getTop();
        Node current = players.getCurrent();
        List<ICard> choices = players.currentMakeTurn(top);
        boolean currentHasOnlyOneCard = current.getPlayer().numOfCards() == 1;

        if(validPlayedCards(choices)) {
            // card effects here ....

            // change currentPlayer to next in line depending on game direction and position in circle:
            players.nextTurn();

            // discard played card
            for(int i = 0; i < choices.size(); i++){
                discarded.discard(choices.get(i));
            }


            // check if the player has run out of cards
            if (players.playerOutOfCards(current)) {
                // if the player only had one card, and never said uno,
                if (currentHasOnlyOneCard && !current.getPlayer().uno()) {
                    //penalise: draw 3 cards.
                    current.giveCardToPlayer(deck.drawCard());
                    current.giveCardToPlayer(deck.drawCard());
                    current.giveCardToPlayer(deck.drawCard());
                } else {
                    // removes player
                    players.playerFinished(current);
                }
            }
        }else{
            for(int i = 0; i < choices.size(); i++) {
                players.giveCardToPlayer(choices.get(i));
            }
        }

    }
}
