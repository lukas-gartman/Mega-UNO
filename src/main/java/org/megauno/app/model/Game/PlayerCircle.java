package org.megauno.app.model.Game;

import org.megauno.app.model.Cards.ICard;
import org.megauno.app.model.Player.Player;

import java.util.ArrayList;
import java.util.List;


/**
 * Circular doubly linked list to keep track of players and turns
 * dependency on Node class
 */
public class PlayerCircle {
    private Node head = null;
    private Node tail = null;
    private Node currentPlayer = null;
    Rotation direction;

    public List<Player> winners;

    private int numPlayers;

    public PlayerCircle(){
        // starts with clockwise as default
        direction = Rotation.CLOCKWISE;
        winners = new ArrayList<>();
    }

    public PlayerCircle(List<Player> players){
        for (Player p : players) {
            addNode(p);
        }
        this.direction = Rotation.CLOCKWISE;
        this.winners = new ArrayList<>();
    }

    /**
     * Adding a new node to the circle
     * @param p is the Player object that the new node will hold
     */
    public void addNode(Player p) {
        Node newNode = new Node(p);
        if (head == null) {
            head = newNode;

            // Set first added player as current player:
            currentPlayer = newNode;

        } else {
            tail.nextNode = newNode;
            newNode.previousNode = tail;
        }
        tail = newNode;

        tail.nextNode = head;
        head.previousNode = tail;
        numPlayers++;
    }


    /**
     * Removes a node from the circle, connecting the previous and next node to each other
     * @param toRemove the node that needs to be removed
     */
    private void removeNode(Node toRemove){
        Node next = toRemove.nextNode;
        Node prev = toRemove.previousNode;
        prev.nextNode = next;
        next.previousNode = prev;
        numPlayers--;
    }

    /**
     * Changes currentPlayer to next in line depending on current rotation
     */
    public void moveOnToNextTurn(){
        if (direction == Rotation.CLOCKWISE) currentPlayer = currentPlayer.nextNode;
        else currentPlayer = currentPlayer.previousNode;
    }

    public Player getNextPlayer(){
        if (direction == Rotation.CLOCKWISE) return currentPlayer.nextNode.getPlayer();
        else return currentPlayer.previousNode.getPlayer();
    }

    /**
     * let the current player try to play a set of cards
     * @return the set of cards the player has chosen
     */
    public List<ICard> currentMakeTurn(){
        return currentPlayer.play();
    }

    /**
     * changes the direction of the game
     */
    public void changeRotation(){
        if (direction == Rotation.CLOCKWISE) direction = Rotation.ANTICLOCKWISE;
        else direction = Rotation.CLOCKWISE;
    }

    public int playersLeft(){
        return numPlayers;
    }

    /**
     * give current player a card
     * @param card is the card to give
     */
    public void giveCardToCurrentPlayer(ICard card){
        currentPlayer.giveCardToPlayer(card);
    }

    public Node getCurrent(){
        return currentPlayer;
    }

    public Node getNextPlayerNode(){
        if (direction == Rotation.CLOCKWISE) return currentPlayer.nextNode;
        else return currentPlayer.previousNode;
    }

    public void giveCardToPlayer(ICard c, Node p){
        p.giveCardToPlayer(c);
    }

    /**
     * checking if a player has run out of cards
     * @param n the node that holds the player to be checked
     * @return true if the player have run out of cards
     */
    boolean IsPlayerOutOfCards(Node n){
        return n.getPlayer().numOfCards() == 0;
    }

    /**
     * when a player has finished the game, their node is removed and placed in the list of winners
     * @param n is the node holding the player
     */
    void playerFinished(Node n){
        winners.add(n.getPlayer());
        removeNode(n);
    }

    public Player[] getPlayers(){
        Node next = currentPlayer.nextNode;
        Player[] out = new Player[numPlayers];
        out[0] = currentPlayer.getPlayer();
        for (int i = 1; i < numPlayers; i++){
            out[i] = (next.getPlayer());
            next = next.nextNode;
        }
        return out;
    }

    public int getCurrentId(){
        return currentPlayer.getId();
    }

}