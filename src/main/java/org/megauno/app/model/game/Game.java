package org.megauno.app.model.game;

import org.megauno.app.model.cards.Color;
import org.megauno.app.model.cards.ICard;
import org.megauno.app.model.game.utilities.Deck;
import org.megauno.app.model.game.utilities.Pile;
import org.megauno.app.model.game.utilities.PlayerCircle;
import org.megauno.app.model.player.Player;
import org.megauno.app.utility.Publisher.IPublisher;
import org.megauno.app.utility.Publisher.normal.Publisher;
import org.megauno.app.utility.Tuple;
import org.megauno.app.viewcontroller.GamePublishers;

import java.util.ArrayList;
import java.util.List;

/**
 * The endpoint of the model. Here is where all the smaller components are combined into
 * a functional logical representation of a UNO game.
 * The changes made in Game are published to anyone who is subscribed to Game.
 */
public class Game implements IActOnGame, GamePublishers, IGameImputs {
    private PlayerCircle players;
    private IDeck deck;
    private IPile discarded;
    private int drawCount = 0;
    // Publisher for the new top card in the discard pile
    private Publisher<ICard> onNewTopCard = new Publisher<>();
    // Publisher for when cards are added to a player
    private Publisher<Tuple<Player, List<ICard>>> onCardsAddedByPlayer = new Publisher<>();
    // Publisher for when cards are added to a player
    private Publisher<Tuple<Player, List<ICard>>> onCardsRemovedByPlayer = new Publisher<>();

    private Color wildCardColor;

    public Game() {
        this.discarded = new Pile();
        this.deck = new Deck();
        this.players = new PlayerCircle();
    }

    public Game(PlayerCircle players) {
        this.players = players;
        addSubscriptionToPlayers(players.getPlayers());
    }

    public void start(int numCards) {
        this.discarded = new Pile();
        onNewTopCard.publish(getTopCard());
        this.deck = new Deck();
        addCardsToAllPlayers(numCards);
        //GameStatePrint.print(this);
        players.onNewPlayer().publish(getCurrentPlayer());
    }

    public void addCardsToAllPlayers(int numCards) {
        for (Player player : getPlayers()) {
            player.addCards(deck.dealHand(numCards));
        }
    }

    private void addSubscriptionToPlayers(Player[] players) {
        for (Player player : players) {
            player.getOnCardsAddedByPlayer().addSubscriber(onCardsAddedByPlayer::publish);
            player.getOnCardRemovedByPlayer().addSubscriber(onCardsRemovedByPlayer::publish);
        }
    }

    // TODO: move method to a more general class (general class representing the model)
    // commence_forth: set by a controller (or test) to signal that the player has chosen.
    // tests should remember to call update
    public boolean commenceForth = false;

    public void update() {
        if (commenceForth) {
            try_play();
            commenceForth = false;
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
        for (Player player : players)
            result.add(player.getCards());

        return result;
    }

    // Current player


    public Player getCurrentPlayer() {
        return players.getCurrent();
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
     * The given player tries to draw a card, which is limited to 3 cards
     */
    @Override
    public void playerDraws(Player player) {
        if (drawCount <= 2) {
            if (player == getCurrentPlayer()) {
                drawCount++;
                player.addCard(deck.drawCard());
            }
        } else {
            nextTurn();
        }
    }

    /**
     * If the player has selected which cards to play,
     * attempt to play those cards and discard on pile if successful
     */
    public void try_play() {
        Player current = players.getCurrent();
        List<ICard> choices = current.play();
        boolean currentHasOnlyOneCard = (current.getCards().size() - choices.size()) == 1;
        if (!currentHasOnlyOneCard) current.unsayUno();

        if (validPlay(choices, current)) {
            // discard successfully played cards
            for (ICard c : choices) {
                discarded.discard(c);
            }
            onNewTopCard.publish(getTopCard());
            for (ICard choice : choices) {
                choice.activate(this);
            }
            // change currentPlayer to next in line:
            nextTurn();

            current.removeSelectedCardsFromHand();
            checkPlayersProgress(current, currentHasOnlyOneCard, choices);
            //GameStatePrint.print(this);
        }

    }

    /**
     * A check to see if the current player has only one card left and said uno,
     * or is out of cards and qualify to leave the game to
     *
     * @param current               is the current player
     * @param currentHasOnlyOneCard is true if current player has only one card
     * @param choices               is the set of cards the current player has tried to play
     */
    private void checkPlayersProgress(Player current, boolean currentHasOnlyOneCard, List<ICard> choices) {
        if (currentHasOnlyOneCard && !current.uno()) {
            //penalise: draw 3 cards.
            current.addCard(deck.drawCard());
            current.addCard(deck.drawCard());
            current.addCard(deck.drawCard());
        } else if (players.isPlayerOutOfCards(current)) {
            if (choices.size() > 1 || current.uno()) {
                players.playerFinished(current);
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
    private boolean validPlay(List<ICard> choices, Player current) {
        List<ICard> hand = current.getCards();
        int lastCardIndex = hand.size() - 1;
        return validPlayedCards(choices) &&
                (drawCount < 1 ||
                        (choices.size() == 1 && choices.get(0) == hand.get(lastCardIndex)));
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
    public void setColor(Player player, Color color) {
        if (player == getCurrentPlayer()) {
            wildCardColor = color;
        }
    }

    @Override
    public PlayerCircle getPlayerCircle() {
        return players;
    }

    @Override
    public Player[] getPlayers() {
        return players.getPlayers();
    }

    public ICard getTopCard() {
        return discarded.getTop();
    }

    @Override
    public Publisher<Player> onNewPlayer() {
        return getPlayerCircle().onNewPlayer();
    }

    @Override
    public Publisher<ICard> onNewTopCard() {
        return onNewTopCard;
    }

    @Override
    public IPublisher<Tuple<Player, List<ICard>>> onCardsAddedToPlayer() {
        return onCardsAddedByPlayer;
    }

    @Override
    public IPublisher<Tuple<Player, List<ICard>>> onCardsRemovedByPlayer() {
        return onCardsRemovedByPlayer;
    }

    @Override
    public void selectCard(Player player, ICard card) {
        if (player == getCurrentPlayer()) {
            System.out.println(player.getSelectedCards());
            player.selectCard(card);
        }
    }

    @Override
    public void unSelectCard(Player player, ICard card) {
        if (player == getCurrentPlayer()) {
            player.unSelectCard(card);
        }
    }

    @Override
    public void commenceForth(Player player) {
        if (player == getCurrentPlayer()) {
            commenceForth = true;
        }
    }

    @Override
    public void sayUno(Player player) {
        if (player == getCurrentPlayer()) {
            player.sayUno();
        }
    }


}
