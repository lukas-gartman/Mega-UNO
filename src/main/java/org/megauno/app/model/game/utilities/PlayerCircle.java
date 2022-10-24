package org.megauno.app.model.game.utilities;

import org.megauno.app.model.cards.ICard;
import org.megauno.app.model.player.Player;
import org.megauno.app.utility.Publisher.normal.Publisher;

import java.util.ArrayList;
import java.util.List;


/**
 * PlayerCircle handles all the active players in the game.
 * An instance of Facade Pattern for Game -> PlayerCircle -> Player
 */
public class PlayerCircle {
    public int currentIndex = 0;
    Rotation direction;
    List<Player> players;
    public List<Player> winners;
    private Publisher<Player> onNewPlayer = new Publisher<>();

    public PlayerCircle() {
        // starts with clockwise as default
        direction = Rotation.CLOCKWISE;
        winners = new ArrayList<>();
    }

    /**
     * Creates a player circle, specifies the direction of play and
     * fills the player circle with the given players.
     * @param players the players for which the player circle will contain.
     */
    public PlayerCircle(List<Player> players) {
        this.players = players;
        this.direction = Rotation.CLOCKWISE;
        this.winners = new ArrayList<>();
    }

    /**
     * Changes currentPlayer to next in line depending on current rotation
     */
    public void moveOnToNextTurn() {
        if (direction == Rotation.CLOCKWISE) {
            if (currentIndex < (players.size()-1)){
                currentIndex++;
            }else currentIndex = 0;
        }
        else {
            if (currentIndex == 0){
                currentIndex = players.size()-1;
            } else currentIndex--;
        }
        onNewPlayer.publish(players.get(currentIndex));
    }

    /**
     * get the player who is next up, depending on the current direction of play
     * @return the next player
     */
    public Player getNextPlayer() {
        if (direction == Rotation.CLOCKWISE){
            if (currentIndex < (players.size()-1)){
                return players.get(currentIndex+1);
            }else return players.get(0);
        }
        else {
            if (currentIndex == 0){
                return players.get(players.size()-1);
            }else return players.get(currentIndex-1);
        }
    }

    /**
     * changes the direction of the game
     */
    public void changeRotation() {
        if (direction == Rotation.CLOCKWISE) direction = Rotation.ANTICLOCKWISE;
        else direction = Rotation.CLOCKWISE;
        onNewPlayer.publish(players.get(currentIndex));
    }

    /**
     * Getter for the amount of players still in the game
     * @return
     */
    public int playersLeft() {
        return players.size();
    }

    /**
     * get the player whose turn it currently is
     * @return the current player
     */
    public Player getCurrent(){
        return players.get(currentIndex);
    }

    /**
     * checking if a player has run out of cards
     *
     * @param p is the player to be checked
     * @return true if the player have run out of cards
     */
    public boolean isPlayerOutOfCards(Player p) {
         return p.getCards().size() == 0;
    }

    /**
     * when a player has finished the game, they are removed from the list of players
     * and placed in the list of winners
     * @param p is the player that has finished the game
     */
    public void playerFinished(Player p) {
        winners.add(p);
        players.remove(p);
    }

    public Player[] getPlayers() {
        Player[] out = new Player[players.size()];
        for (int i = 0; i < players.size(); i++) {
            out[i] = players.get(i);
        }
        return out;
    }

    /**
     *
     * @return
     */
    public Publisher<Player> onNewPlayer() {
        return onNewPlayer;
    }
}
