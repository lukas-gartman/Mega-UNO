package org.megauno.app.model.Game;

import org.megauno.app.model.Cards.Color;
import org.megauno.app.model.Cards.ICard;
import org.megauno.app.model.Deck;
import org.megauno.app.model.IDeck;
import org.megauno.app.model.IPile;
import org.megauno.app.model.Pile;
import org.megauno.app.model.Player.Player;
import org.megauno.app.utility.Publisher;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Game implements IActOnGame {
    private PlayerCircle players;
    private IDeck deck;
    private IPile discarded;
    private int drawCount = 0;
    private Publisher<Game> publisher;
    private Color wildCardColor;

    /**
     * @param players  is the circle of players
     * @param numCards is the number of cards a hand is initially dealt
     */
    public Game(PlayerCircle players, int numCards) {
        this(players, numCards, new Publisher<Game>());
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

    public Game() {
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
    public int getCurrentPlayerId() {
        return players.getCurrentId();
    }

    @Override
    public void reverse() {
        players.changeRotation();
    }

    /**
     * @param play is the list of cards the current player is trying to play
     * @return whether the list of cards are playable on the current pile
     */
    private boolean validPlayedCards(List<ICard> play) {
        ICard top = discarded.getTop();
        if (play.size() == 0) {
            return false;
        } else if (!play.get(0).canBePlayed(top)) {
            return false;
        }

        for (int i = 1; i < play.size(); i++) {
            if (!play.get(i).canBeStacked(play.get(i - 1))) {
                return false;
            }
        }
        return true;
    }

    /**
     * The current player tries to draw a card, which is limited to 3 cards
     *
     * @return true if the draw succeeded
     */
    public boolean playerDraws() {
        if (drawCount > 2) {
            nextTurn();
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
        boolean currentHasOnlyOneCard = (current.getHandSize() - choices.size()) == 1;
        if (!currentHasOnlyOneCard) current.unsayUno();

        if (validPlay(choices, current)) {
            // discard successfully played cards
            for (ICard c : choices) {
                discarded.discard(c);
            }
            for (ICard choice : choices) {
                choice.activate(this);
            }
            // change currentPlayer to next in line:
            nextTurn();

            current.removeSelected();
            checkPlayersProgress(current, currentHasOnlyOneCard, choices);

        }
        /*else {
            for (int i = 0; i < choices.size(); i++) {
                players.giveCardToPlayer(choices.get(i), current);
            }
        }*/
    }

    /**
     * A check to see if the current player has only one card left and said uno,
     * or is out of cards and qualify to leave the game to
     *
     * @param current               is the current player
     * @param currentHasOnlyOneCard is true if current player has only one card
     * @param choices               is the set of cards the current player has tried to play
     */
    private void checkPlayersProgress(Node current, boolean currentHasOnlyOneCard, List<ICard> choices) {
        if (currentHasOnlyOneCard && !current.uno()) {
            //penalise: draw 3 cards.
            current.giveCardToPlayer(deck.drawCard());
            current.giveCardToPlayer(deck.drawCard());
            current.giveCardToPlayer(deck.drawCard());
            publisher.publish(this);
        } else if (players.IsPlayerOutOfCards(current)) {
            if (choices.size() > 1 || current.uno()) {
                players.playerFinished(current);
                publisher.publish(this);
            }
        }
    }

    /**
     * check that the cards attempted to be played can be played given the top of the discard pile.
     * If a card has been drawn by the current player on this turn, then only this card can be played.
     *
     * @param choices the set of cards attempted to be played
     * @param current the current player
     * @return true if playing chosen cards is a valid move
     */
    private boolean validPlay(List<ICard> choices, Node current) {
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
    @Override
    public void nextTurn() {
        drawCount = 0;
        players.moveOnToNextTurn();
    }

    @Override
    public ICard draw() {
        return deck.drawCard();
    }

    // Setter and getter for setting  the color of chosen wildcards
    // during the current turn, this means there is no way of choosing
    // different colors for different wildcards if multiple is played
    @Override
    public Color getChosenColor() {
        return wildCardColor;
    }

    @Override
    public void setColor(Color color) {
        wildCardColor = color;
    }

    @Override
    public IDeck getDeck() {
        return deck;
    }

    @Override
    public PlayerCircle getPlayerCircle() {
        return players;
    }

    @Override
    public Player[] getPlayers() {
        return players.getPlayers();
    }

    @Override
    public Player getPlayerWithId(int id) {
        for (Player p : getPlayers()) {
            if (p.getId() == id) {
                return p;
            }
        }
        return null;
    }

    @Override
    public ICard getTopCard() {
        return discarded.getTop();
    }

    /**
     * Used for testing purposes, simulates a player choosing a card
     */
    public void simulatePlayerChoosingCard() {
        Random rand = new Random();
        Node current = players.getCurrent();
        int randomIndex = rand.nextInt(current.getHandSize());
        ICard randomCard = current.getHand().get(randomIndex);
        current.selectCard(randomCard);
    }

    @Override
    public void sayUno(Player player) {
        player.sayUno();
    }

    @Override
    public void unsayUno(Player player) {
        player.unsayUno();
    }

    private boolean hasSaidUno(Player player) {
        return player.uno();
    }

    private <A> void prettyPrintList(List<A> list) throws InterruptedException {
        for (int i = 0; i < list.size(); i++) {
            Thread.sleep(300);
            System.out.println((i + 1) + ": " + list.get(i).toString());
        }
    }

    // Check that they input indices are valid
    private boolean validIndices(List<ICard> hand, List<Integer> indices) {
        indices = indices.stream().distinct().toList();
        for (Integer index : indices) {
            if (index < 0 || index > hand.size()) {
                return false;
            }
        }
        return true;
    }

    public boolean tryPlayTest() throws InterruptedException {
        Scanner input = new Scanner(System.in);
        Node current = players.getCurrent();
        Player currentPlayer = current.getPlayer();
        List<ICard> availableCards = currentPlayer.getCards();

        System.out.println("Current player: " + currentPlayer.hashCode());
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
                availableCards = currentPlayer.getCards();
                nextTurn();
                System.out.println("You didn't draw any playable cards, though luck.");
                Thread.sleep(1000);
                return !current.equals(players.getCurrent());
            }
            Thread.sleep(500);
            System.out.println(">>>You have no playable cards on hand, a card is drawn<<<");
            Thread.sleep(2000);
            availableCards = currentPlayer.getCards();
            System.out.println("Available cards to choose from: ");
            Thread.sleep(500);
            prettyPrintList(availableCards);
        }

        // Suggest saying UNO
        System.out.println("Say UNO? -> ");
        String maybeUno = input.nextLine();
        // Check that uno was said
        if (maybeUno.equals("UNO")) {
            sayUno(currentPlayer);
            System.out.println("Player has said UNO: " + hasSaidUno(currentPlayer));
        }

        // For player to choose cards
        int chosenIndex = -1;
        // Loop until valid index is chosen.
        while (chosenIndex < 0 || chosenIndex > availableCards.size()) {
            System.out.println("Enter the number of the card you want to choose: ");
            chosenIndex = input.nextInt();
        }
        // Choose the card
        currentPlayer.selectCard(availableCards.get(chosenIndex - 1));

        //simulatePlayerChoosingCard();
        // Play the chosen card(s)
        boolean currentHasOnlyOneCard = currentPlayer.numOfCards() == 1;
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
                if (currentHasOnlyOneCard && !hasSaidUno(currentPlayer)) {
                    //penalise: draw 3 cards.
                    System.out.println("-------------<Didn't say UNO>-------------");
                    Thread.sleep(500);
                    System.out.println("Penalty applied, three cards added to your hand");
                    for (int i = 0; i < 3; i++) {
                        current.giveCardToPlayer(deck.drawCard());
                    }
                } else {
                    // removes player
                    players.playerFinished(current);
                    System.out.println("Player " + current.getPlayer().hashCode() + " has finished");
                    System.out.println(players.playersLeft());
                }
            }
            // change currentPlayer to next in line depending on game direction and position in circle:
            nextTurn();
        }
        // Player has valid cards to play, but chose not to play them
        else {
            System.out.println("You chose to play a card that cannot be played, too bad");
            for (ICard choice : choices) {
                players.giveCardToCurrentPlayer(choice);
            }
            nextTurn();
        }
        System.out.println("\n|||||||||| New round |||||||||| \n");
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
