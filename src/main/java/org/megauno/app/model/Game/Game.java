package org.megauno.app.model.Game;

import org.megauno.app.model.Cards.ICard;
import org.megauno.app.model.Deck;
import org.megauno.app.model.Pile;

public class Game {
    PlayerCircle players;
    //ICard top;
    Deck deck;
    Pile discarded;

    public Game(){
        discarded = new Pile();
    }



    public void play() {
        ICard top = discarded.getTop();
        Node current = players.getCurrent();
        ICard choice = players.currentMakeTurn(top);
        boolean currentHasOnlyOneCard = current.getPlayer().numOfCards() == 1;

        if(choice.canBePlayed(top)) {
            // card effects here ....
            choice.activate();

            // change currentPlayer to next in line depending on game direction and position in circle:
            players.nextTurn();

            // discard played card
            discarded.discard(choice);

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
            players.giveCardToPlayer(choice);
        }
    }
}
