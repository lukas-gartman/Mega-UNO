package org.megauno.app.viewcontroller.distributers.otherPlayerHandChanges;

import org.megauno.app.utility.ObserverPattern.Subscriber;

public interface OtherPlayerHandChangesSubscriber extends Subscriber<OtherPlayersNrOfCards> {
    void delivery(OtherPlayersNrOfCards np);
}
