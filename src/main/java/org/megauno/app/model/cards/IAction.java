package org.megauno.app.model.cards;

import org.megauno.app.model.game.IActOnGame;

/**
 * Defines an action which an action card has. This is an implementation of Strategy Pattern,
 * which in this instance enables the cards to be abstracted under a common interface,
 * instead of having to create specific instances of action cards.
 */
public interface IAction {

    /**
     * Executes the specific action.
     *
     * @param g An abstraction of the game which the action is executed on.
     * @return Weather the execution of the action is successfull or not.
     */
    boolean execute(IActOnGame g);
}
