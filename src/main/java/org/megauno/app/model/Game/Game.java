package org.megauno.app.model.Game;

import org.megauno.app.model.Cards.Color;
import org.megauno.app.model.Cards.ICard;
import org.megauno.app.model.Deck;
import org.megauno.app.model.Pile;
import org.megauno.app.model.Player.Player;
import org.megauno.app.utility.Publisher;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Game implements IActOnGame {
    private PlayerCircle players;
    private Deck deck;
    private Pile discarded;
    private int drawCount = 0;
    private Publisher<Game> publisher;
    private Color wildCardColor;


    /**
     *
     * @param players is the circle of players
     * @param numCards is the number of cards a hand is initially dealt
     */

    public Game(PlayerCircle players, int numCards){
        this(players,numCards,new Publisher<Game>());
    }

    public Game(PlayerCircle players, int numCards, Publisher<Game> publisher) {
        this.players = players;
        this.discarded = new Pile();
		this.deck = new Deck();
        this.publisher = publisher;

        int p = 0;
        while (p < players.playersLeft() * numCards) {
            players.giveCardToCurrentPlayer(deck.drawCard());
            players.moveOnToNextTurn();
            p++;
        }
    }
    public Game(){
        this.discarded = new Pile();
        this.deck = new Deck();
        players = new PlayerCircle();
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
            commence_forth = false;
		}
	}

	// Basic API for ViewController, potentially tests as well
	
	// Each boolean represents wether or not a card is chosen by the current player
	public boolean[] choices;

	// Inner array is null if the player with the given ID/index is out of the game
	// TODO: add an ID in the Player class to be able to put null here
	public List<List<ICard>> getAllPlayerCards() {
		Player[] players = getPlayers();
		List<List<ICard>> result = new ArrayList<>();
		for (int i = 0; i < players.length; i++) {
			result.add(players[i].getCards());
		}
		return result;
	}

	public int getPlayersLeft() {
		return players.playersLeft();
	}

	// Current player
	public int getCurrentPlayer() {
		return players.getCurrent().getPlayer().getId();
	}

    public void reverse(){
        players.changeRotation();
    }

    /**
     *
     * @param play is the list of cards the current player is trying to play
     * @return whether the list of cards are playable on the current pile
     */
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

    /**
     * The current player tries to draw a card, which is limited to 3 cards
     * @return true if the draw succeeded
     */
    public boolean playerDraws(){
        if (drawCount > 2){
            return false;
        } else {
            Node current = players.getCurrent();
            current.giveCardToPlayer(deck.drawCard());
            drawCount++;
            return true;
        }
    }

    /**
     * If the player has selected which cards to play,
     * attempt to play those cards and discard on pile if successful
     */
    public void try_play() {
        ICard top = discarded.getTop();
        Node current = players.getCurrent();
        List<ICard> choices = players.currentMakeTurn();
        boolean currentHasOnlyOneCard = current.getHandSize() == 1;
        if (!currentHasOnlyOneCard) current.unsayUno();

        if (validPlay(choices, current)) {
            // discard successfully played cards
            for (ICard c: choices) {
                discarded.discard(c);
            }
            for (ICard choice : choices) {
                choice.activate(this);
            }
            // change currentPlayer to next in line:
            nextTurn();



            checkPlayersProgress(current, currentHasOnlyOneCard, choices);
        }
        else {
            for (int i = 0; i < choices.size(); i++) {
                players.giveCardToPlayer(choices.get(i), current);
            }
        }
    }

    /**
     * A check to see if the current player has only one card left and said uno,
     * or is out of cards and qualify to leave the game to
     * @param current is the current player
     * @param currentHasOnlyOneCard is true if current player has only one card
     * @param choices is the set of cards the current player has tried to play
     */
    private void checkPlayersProgress(Node current, boolean currentHasOnlyOneCard, List<ICard> choices){
        if (currentHasOnlyOneCard && !current.uno()) {
            //penalise: draw 3 cards.
            current.giveCardToPlayer(deck.drawCard());
            current.giveCardToPlayer(deck.drawCard());
            current.giveCardToPlayer(deck.drawCard());
            publisher.publish(this);
        }else if (players.IsPlayerOutOfCards(current) ) {
            if (choices.size() > 1 || current.uno()){
                players.playerFinished(current);
                publisher.publish(this);
            }
        }
    }


    /**
     * check that the cards attempted to be played can be played given the top of the discard pile.
     * If a card has been drawn by the current player on this turn, then only this card can be played.
     * @param choices is the set of cards attempted to be played
     * @param current
     * @return true if playing chosen cards is a valid move
     */
    private boolean validPlay(List<ICard> choices, Node current){
        List<ICard> hand = current.getHand();
        int lastCardIndex = hand.size() - 1;
        return validPlayedCards(choices) &&
                (drawCount < 1 ||
                        (choices.size() == 1 && choices.get(0).equals(hand.get(lastCardIndex))));
    }


    /**
     * method to hand over the turn to next player
     * resets drawCount for the next turn.
     */
    public void nextTurn(){
        drawCount = 0;
        players.moveOnToNextTurn();
    }

    public ICard draw(){
        return deck.drawCard();
    }

    public Color getChosenColor(){
        return wildCardColor;
    }

    public Deck getDeck(){
        return deck;
    }

    public PlayerCircle getPlayerCircle(){
        return players;
    }

    public Player[] getPlayers(){
        return players.getPlayers();
    }

    public Player getPlayerWithId(int id){
        for (Player p:getPlayers()) {
            if (p.getId() == id){
                return p;
            }
        }
        return null;
    }

    public ICard getTopCard(){
        return discarded.getTop();
    }

    /**
     * Used for testing purposes, simulates a player choosing a card
      */
    public void simulatePlayerChoosingCard() {
        Random rand = new Random();
        Player currentPlayer = players.getCurrent().getPlayer();
        int randomIndex = rand.nextInt(currentPlayer.numOfCards());
        ICard randomCard = currentPlayer.getCards().get(randomIndex);
        currentPlayer.selectCard(randomCard);
    }

    public boolean tryPlayTest() {
        Node current = players.getCurrent();
        simulatePlayerChoosingCard();
        List<ICard> choices = players.currentMakeTurn();
        boolean currentHasOnlyOneCard = current.getPlayer().numOfCards() == 1;
        System.out.println("Current player: " + current.getPlayer().hashCode());
        System.out.println("Player choices: " + choices);
        System.out.println("Top card: " + discarded.getTop());

        if (validPlayedCards(choices)) {
            // card effects here ....
            for (ICard choice : choices) {
                choice.activate(this);
            }
            System.out.println("-------------<Successfully played>-------------");

            // change currentPlayer to next in line depending on game direction and position in circle:
            players.moveOnToNextTurn();

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
            if (players.IsPlayerOutOfCards(current)) {
                players.playerFinished(current);
                System.out.println("Player " + current.getPlayer().hashCode() + " has finished");
                System.out.println("Players left: " + players.playersLeft());
            }
        }
        else {
            for (ICard choice : choices) {
                players.giveCardToCurrentPlayer(choice);
            }
            players.moveOnToNextTurn();
        }
        System.out.println("\n|||||||||| New round |||||||||| \n");
        return !current.equals(players.getCurrent());
    }
}
