package org.megauno.app.model.Game;

import org.megauno.app.model.Cards.ICard;
import org.megauno.app.model.Deck;
import org.megauno.app.model.Pile;
import org.megauno.app.model.Player.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Game implements IActOnGame {
    PlayerCircle players;
    //ICard top;
    Deck deck;
    Pile discarded;
    private int drawCount = 0;


    /**
     *
     * @param players is the circle of players
     * @param numCards is the number of cards a hand is initially dealt
     */
    public Game(PlayerCircle players, int numCards) {
        discarded = new Pile();
        this.players = players;

        int p = 0;
        while (p < players.playersLeft() * numCards) {
            players.getCurrent().giveCardToPlayer(deck.drawCard());
            players.getNextPlayer();
            p++;
        }
        discarded = new Pile();
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
		}
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
        boolean currentHasOnlyOneCard = current.getPlayer().numOfCards() == 1;

        if (validPlay(choices, current)) {
            for (ICard choice : choices) {
                choice.activate(this);
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

    /**
     * A check to see if the current player has only one card left and said uno,
     * or is out of cards and qualify to leave the game to
     * @param current is the current player
     * @param currentHasOnlyOneCard is true if current player has only one card
     * @param choices is the set of cards the current player has tried to play
     */
    private void checkPlayersProgress(Node current, boolean currentHasOnlyOneCard, List<ICard> choices){
        if (currentHasOnlyOneCard && !current.getPlayer().uno()) {
            //penalise: draw 3 cards.
            current.giveCardToPlayer(deck.drawCard());
            current.giveCardToPlayer(deck.drawCard());
            current.giveCardToPlayer(deck.drawCard());
        }else if (players.IsPlayerOutOfCards(current) ) {
            if (choices.size() > 1 || current.getPlayer().uno()) players.playerFinished(current);
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
        List<ICard> hand = current.getPlayer().getCards();
        int lastCardIndex = current.getPlayer().getCards().size() - 1;
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


    public Deck getDeck(){
        return deck;
    }

    public PlayerCircle getPlayers(){
        return players;
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

    /**
     *
     * @return
     */
    private <A> void prettyPrintList(List<A> list) throws InterruptedException {
        for (int i = 0; i < list.size(); i++) {
            Thread.sleep(500);
            System.out.println((i+1) + ": " + list.get(i).toString());
        }
    }

    public boolean tryPlayTest() throws InterruptedException {
        Scanner input = new Scanner(System.in);
        Node current = players.getCurrent();
        List<ICard> availableCards = current.getPlayer().getCards();

        System.out.println("Current player: " + current.getPlayer().hashCode());
        Thread.sleep(500);
        System.out.println("Top card: " + discarded.getTop());
        Thread.sleep(500);

        // Display cards
        System.out.println("Available cards to choose from: ");
        Thread.sleep(500);
        prettyPrintList(availableCards);

        // Check that the player can actually play, otherwise make them draw cards
        while (!canPlayerPlay(availableCards)) {
            if (!playerDraws()) {
                Thread.sleep(500);
                System.out.println(">>>You have no playable cards on hand, a card is drawn<<<");
                Thread.sleep(2000);
                // Update the available cards such that the draw card is included
                availableCards = current.getPlayer().getCards();
                nextTurn();
                System.out.println("You didn't draw any playable cards, though luck.");
                Thread.sleep(1000);
                return !current.equals(players.getCurrent());
            }
            Thread.sleep(500);
            System.out.println(">>>You have no playable cards on hand, a card is drawn<<<");
            Thread.sleep(2000);
            availableCards = current.getPlayer().getCards();
            System.out.println("Available cards to choose from: ");
            Thread.sleep(500);
            prettyPrintList(availableCards);
        }

        // Suggest saying UNO
        System.out.println("Say UNO? -> ");
        String maybeUno = input.nextLine();
        // Check that uno was said
        if (maybeUno.equals("UNO")) current.getPlayer().sayUno();

        // For player to choose cards
        int chosenIndex = -1;
        // Loop until valid index is chosen.
        while (chosenIndex < 0 || chosenIndex > availableCards.size()) {
            System.out.println("Enter the number of the card you want to choose: ");
            chosenIndex = input.nextInt();
        }
        // Choose the card
        current.getPlayer().selectCard(availableCards.get(chosenIndex - 1));

        //simulatePlayerChoosingCard();
        // Play the chosen card(s)
        boolean currentHasOnlyOneCard = current.getPlayer().numOfCards() == 1;
        // Important that this comes after the previous line, since
        // make turn removes the cards from the hand
        List<ICard> choices = players.currentMakeTurn();
        System.out.println("Player choice(s): " + choices);

        if (validPlayedCards(choices)) {
            // card effects here ....
            for (ICard choice : choices) {
                choice.activate(this);
            }
            System.out.println("-------------<Successfully played>-------------");
            Thread.sleep(700);
            System.out.println(choices + " was played on " + discarded.getTop());

            // discard played card
            for (ICard choice : choices) {
                discarded.discard(choice);
            }

            // check if the player has run out of cards
            if (players.IsPlayerOutOfCards(current)) {
                // if the player only had one card, and never said uno,
                if (currentHasOnlyOneCard && !current.getPlayer().uno()) {
                    //penalise: draw 3 cards.
                    System.out.println("-------------<Didn't say UNO>-------------");
                    Thread.sleep(500);
                    System.out.println("Penalty applied, three cards added to your hand");
                    current.giveCardToPlayer(deck.drawCard());
                    current.giveCardToPlayer(deck.drawCard());
                    current.giveCardToPlayer(deck.drawCard());
                } else {
                    // removes player
                    players.playerFinished(current);
                    System.out.println("Player " + current.getPlayer().hashCode() + " has finished");
                    System.out.println(players.playersLeft());
                }
            }
            // change currentPlayer to next in line depending on game direction and position in circle:
            players.moveOnToNextTurn();
        }
        else {
            for (ICard choice : choices) {
                players.giveCardToCurrentPlayer(choice);
            }
            players.moveOnToNextTurn();
        }
        return !current.equals(players.getCurrent());
    }

    private boolean canPlayerPlay(List<ICard> hand) {
        for (ICard card : hand) {
            // Create a new list for each card, since that is the required input
            // but we want to check each card on its own
            List<ICard> choice = new ArrayList<>();
            choice.add(card);
            if (validPlayedCards(choice))
                return true;
        }
        return false;
    }
}
