package org.megauno.app.model.Game;

import org.megauno.app.model.Cards.ICard;
import org.megauno.app.model.Deck;
import org.megauno.app.model.Pile;
import org.megauno.app.model.Player.Player;

import java.util.List;
import java.util.Random;

public class Game {
    PlayerCircle players;
    //ICard top;
    Deck deck;
    Pile discarded;

    public Game(){
        this.discarded = new Pile();
        this.deck = new Deck();
    }

    // For testing purposes
    public Game(PlayerCircle players) {
        this.discarded = new Pile();
        this.deck = new Deck();
        this.players = players;
    }

	// TODO: move method to a more general class (general class representing the model)
	// commence_forth: set by a controller (or test) to signal that the player has chosen.
	// tests should remember to call update
	public boolean commence_forth = false;
	public void update() {
		if (commence_forth) {
			try_play();
		}
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

    public void try_play() {
        ICard top = discarded.getTop();
        Node current = players.getCurrent();
        List<ICard> choices = players.currentMakeTurn();
        boolean currentHasOnlyOneCard = current.getPlayer().numOfCards() == 1;

        if(validPlayedCards(choices)) {
            // card effects here ....
            for (ICard choice : choices) {
                choice.activate();
            }

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

    // To simulate a player choosing a card
    public void currentPlayerChooseCard() {
        Random rand = new Random();
        Player currentPlayer = players.getCurrent().getPlayer();
        int randomIndex = rand.nextInt(currentPlayer.numOfCards());
        ICard randomCard = currentPlayer.getCards().get(randomIndex);
        currentPlayer.selectCard(randomCard);
    }

    public boolean tryPlayTest() {
        Node current = players.getCurrent();
        currentPlayerChooseCard();
        List<ICard> choices = players.currentMakeTurn();
        boolean currentHasOnlyOneCard = current.getPlayer().numOfCards() == 1;
        System.out.println("Current player: " + current.getPlayer().hashCode());
        System.out.println("Player choices: " + choices);
        System.out.println("Top card: " + discarded.getTop());

        if (validPlayedCards(choices)) {
            // card effects here ....
            for (ICard choice : choices) {
                choice.activate();
            }
            System.out.println("-------------<Successfully played>-------------");

            // change currentPlayer to next in line depending on game direction and position in circle:
            players.nextTurn();

            // discard played card
            for (ICard choice : choices) {
                discarded.discard(choice);
            }


            // check if the player has run out of cards
/*
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
                    System.out.println(players.playersLeft());
                }
            }
*/
            if (players.playerOutOfCards(current)) {
                players.playerFinished(current);
                System.out.println("Player " + current.getPlayer().hashCode() + " has finished");
                System.out.println("Players left: " + players.playersLeft());
            }
        }
        else {
            for (ICard choice : choices) {
                players.giveCardToPlayer(choice);
            }
            players.nextTurn();
        }
        System.out.println("\n|||||||||| New round |||||||||| \n");
        return !current.equals(players.getCurrent());
    }
}