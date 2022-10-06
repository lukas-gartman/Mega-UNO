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
    private int drawCount = 0;

    public Game(PlayerCircle players, int numCards) {
        discarded = new Pile();
        this.players = players;

        int p = 0;
        while (p < players.playersLeft() * numCards) {
            players.getCurrent().giveCardToPlayer(deck.drawCard());
            players.nextPlayer();
            p++;
        }
        discarded = new Pile();
    }
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

    public void playerDraws(){
        Node current = players.getCurrent();
        current.giveCardToPlayer(deck.drawCard());
        drawCount++;
    }

    public void try_play() {
        ICard top = discarded.getTop();
        Node current = players.getCurrent();
        List<ICard> choices = players.currentMakeTurn();
        boolean currentHasOnlyOneCard = current.getPlayer().numOfCards() == 1;

        if (validPlay(choices, current)) {
            for (ICard choice : choices) {
                choice.activate();
            }
            // change currentPlayer to next in line:
            nextTurn();

            // discard successfully played cards
            for (ICard c: choices) {
                discarded.discard(c);
            }

            checkPlayersProgress(current, currentHasOnlyOneCard, choices);
        }
        /*else {
            for (int i = 0; i < choices.size(); i++) {
                players.giveCardToPlayer(choices.get(i));
            }
        }*/
    }

    private void checkPlayersProgress(Node current, boolean currentHasOnlyOneCard, List<ICard> choices){
        if (currentHasOnlyOneCard && !current.getPlayer().uno()) {
            //penalise: draw 3 cards.
            current.giveCardToPlayer(deck.drawCard());
            current.giveCardToPlayer(deck.drawCard());
            current.giveCardToPlayer(deck.drawCard());
        }else if (players.playerOutOfCards(current) && choices.size() > 1) {
            players.playerFinished(current);
        }
    }


    private boolean validPlay(List<ICard> choices, Node current){
        List<ICard> hand = current.getPlayer().getCards();
        int lastCardIndex = current.getPlayer().getCards().size() - 1;
        return validPlayedCards(choices) &&
                (drawCount < 1 ||
                        (choices.size() == 1 && choices.get(0).equals(hand.get(lastCardIndex))));
    }


    private void nextTurn(){
        drawCount = 0;
        players.nextTurn();
    }

    public Deck getDeck(){
        return deck;
    }

    public PlayerCircle getPlayers(){
        return players;
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
