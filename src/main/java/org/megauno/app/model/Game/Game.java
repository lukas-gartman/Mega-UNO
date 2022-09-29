package org.megauno.app.model.Game;

import org.megauno.app.model.Cards.ICard;
import org.megauno.app.model.Deck;
import org.megauno.app.model.Pile;

import java.util.ArrayList;
import java.util.List;

public class Game {
    PlayerCircle players;
    Deck deck;
    Pile discarded;

    public Game(PlayerCircle players, int numCards){
        discarded = new Pile();
        this.players = players;

        int p = 0;
        while(p < players.playersLeft()*numCards) {
            players.getCurrent().giveCardToPlayer(deck.drawCard());
            players.nextPlayer();
            p++;
        }
    }

    public PlayerCircle getPlayers(){
        return players;
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
                    current.giveCardToPlayer(draw());
                    current.giveCardToPlayer(draw());
                    current.giveCardToPlayer(draw());
                } else {
                    // removes player
                    players.playerFinished(current);
                }
            }
        }else{
            players.giveCardToPlayer(choice);
        }
    }

    public Deck getDeck(){
        return deck;
    }

    public ICard draw(){
        return deck.drawCard();
    }
}
