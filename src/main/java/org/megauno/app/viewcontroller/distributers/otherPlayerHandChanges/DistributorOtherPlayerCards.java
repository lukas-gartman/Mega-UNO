package org.megauno.app.viewcontroller.distributers.otherPlayerHandChanges;

import org.megauno.app.model.Player.Player;
import org.megauno.app.utility.ObserverPattern.Distributor;
import org.megauno.app.utility.ObserverPattern.Publisher;
import org.megauno.app.utility.ObserverPattern.Subscriber;
import org.megauno.app.viewcontroller.IGame;

public class DistributorOtherPlayerCards implements Subscriber<IGame> {
    private int playerId;
    private Publisher<OtherPlayersNrOfCards> publisher;

    public DistributorOtherPlayerCards(Publisher<OtherPlayersNrOfCards> publisher, int playerId) {
        this.playerId = playerId;
        this.publisher = publisher;
    }


    @Override
    public void delivery(IGame game) {

        Player[] players = game.getPlayers();
        int[] playerNrOfCards = new int[players.length-1];
        int j = 0;
        for(int i = 0; i < players.length-1; i++){
            Player player = players[i];
            if(player.getId() != playerId){
                playerNrOfCards[j] = player.numOfCards();
                j++;
            }
        }
        publisher.publish(new OtherPlayersNrOfCards(playerNrOfCards));

    }
}
