package org.megauno.app.model.Game;

import org.megauno.app.model.Cards.ICard;
import org.megauno.app.model.Deck;
import org.megauno.app.model.Player.Player;

import java.util.List;

/**
 * The API for the game. What other components outside the model can access.
 */
public interface IGame {

    Player getCurrentPlayer();
    List<ICard> getCurrentPlayerHand();
    PlayerCircle getPlayers();
    Deck getDeck();

    /**
     * To play a round
     * @return weather a round was played successfully or not.
     */
    boolean play();

    /**
     * To communicate what cards a player have chosen to play.
     * @param choices
     */
    void chooseCurrentPlayerCards(List<Boolean> choices);

    /**
     *
      * @param player the player that says UNO.
     */
    void sayUno(Player player);


}
