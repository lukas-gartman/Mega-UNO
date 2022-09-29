package org.megauno.app.utility.ObserverPattern;

import org.lwjgl.system.CallbackI;

public class DeltaDistributor<newsPaper,story> extends Distributor<newsPaper,story> {
    private newsPaper oldNewspaper;

    public DeltaDistributor(Publisher<story> publisher, newsPaper oldNewspaper) {
        super(publisher);
        this.oldNewspaper = oldNewspaper;
    }

    @Override
    public story extractStory(newsPaper np) {
        return null;
    }
}
