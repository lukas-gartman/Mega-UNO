package org.megauno.app.model.Game;

import org.megauno.app.model.Cards.ICard;
import org.megauno.app.model.Player.Player;

import java.util.ArrayList;
import java.util.List;

// Circular doubly linked list to keep track of players and turns
// dependency on Node class
public class PlayerCircle {
    private Node head = null;
    private Node tail = null;
    private Node currentPlayer = null;
    Rotation direction;

    public List<Player> winners;

    private int numPlayers;

    public PlayerCircle(List<Player> players){
        // starts with clockwise as default
        direction = Rotation.CLOCKWISE;
        winners = new ArrayList<>();

        for (Player p : players) {
            addNode(p);
        }
    }

    private void addNode(Player p) {
        Node newNode = new Node(p);
        if (head == null) {
            head = newNode;

            // Sets first added player as current player:
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

    private void removeNode(Node toRemove){
        Node next = toRemove.nextNode;
        Node prev = toRemove.previousNode;
        prev.nextNode = next;
        next.previousNode = prev;
        numPlayers--;
    }

    // Changes currentPlayer to next in line depending on current rotation
    public void nextTurn(){
        if (direction == Rotation.CLOCKWISE) currentPlayer = currentPlayer.nextNode;
        else currentPlayer = currentPlayer.previousNode;
    }

    public ICard currentMakeTurn(ICard top){
       return currentPlayer.play(top);
    }

    public void changeRotation(){
        if (direction == Rotation.CLOCKWISE) direction = Rotation.ANTICLOCKWISE;
        else direction = Rotation.CLOCKWISE;
    }

    public int playersLeft(){
        return numPlayers;
    }

    void giveCardToPlayer(ICard choice){
        currentPlayer.giveCardToPlayer(choice);
    }

    Node getCurrent(){
        return currentPlayer;
    }

    boolean playerOutOfCards(Node n){
        return n.getPlayer().numOfCards() == 0;
    }

    void playerFinished(Node n){
        winners.add(n.getPlayer());
        removeNode(n);
    }

}
