package org.megauno.app.viewcontroller.distributers.handChanges;

import org.megauno.app.utility.ObserverPattern.Subscriber;

public interface HandChangesSubscriber extends Subscriber<HandChanges> {
    void delivery(HandChanges hc);
}
