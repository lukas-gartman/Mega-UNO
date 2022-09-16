package org.megauno.app.model.Game;

import org.megauno.app.model.Cards.ICard;

public class Game {
    PlayerCircle players;
    ICard top;
    // deck
    // discardPile

    public Game(PlayerCircle enemies){
        this.players = enemies;
    }

    public void reverse(){
        players.changeRotation();
    }

    public void play() {
        while (players.playersLeft() > 1) {

            ICard choice = players.currentMakeTurn(top);
            while(!choice.canBePlayed(top)){
                players.returnCard(choice);
                choice = players.currentMakeTurn(top);
            }
            top = choice;
            players.nextTurn();
        }
    }
}
