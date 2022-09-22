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
    }

    public void reverse(){
        players.changeRotation();
    }

    public void play() {
        while (players.playersLeft() > 1) {

            ICard top = discarded.getTop();
            Node current = players.getCurrent();
            //boolean currentHasOnlyOneCard;

            ICard choice = players.currentMakeTurn(top);
            while(!choice.canBePlayed(top)){
                players.returnCard(choice);
                choice = players.currentMakeTurn(top);
            }

            // change to next player:
            players.nextTurn();

            // discard played card
            discarded.discard(choice);

            // check if the player is still in the game
            if (players.playerOutOfCards(current)){
                // Check if said uno...
                //if (currentHasOnlyOneCard && current.getPlayer().uno() || !currentHasOnlyOneCard)
                if (current.getPlayer().uno()){
                    players.playerFinished(current);
                } else {
                    //penalise: draw 3 cards.
                    current.returnCard(deck.drawCard());
                    current.returnCard(deck.drawCard());
                    current.returnCard(deck.drawCard());
                }
            }
        }
    }
}
