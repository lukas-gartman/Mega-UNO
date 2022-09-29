package org.megauno.app.viewcontroller.distributers;

import org.megauno.app.model.Player.Player;
import org.megauno.app.utility.ObserverPattern.Distributor;
import org.megauno.app.utility.ObserverPattern.Publisher;
import org.megauno.app.viewcontroller.IGame;
import org.megauno.app.viewcontroller.distributers.dataClasses.OtherPlayersNrOfCards;

public class DistributorOtherPlayerCards extends Distributor<IGame, OtherPlayersNrOfCards> {
    private int playerId;

    public DistributorOtherPlayerCards(Publisher<OtherPlayersNrOfCards> publisher, int playerId) {
        super(publisher);
        this.playerId = playerId;
    }

    @Override
    public OtherPlayersNrOfCards extractStory(IGame game) {

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
        return new OtherPlayersNrOfCards(playerNrOfCards);

    }
}
