package org.megauno.app.utility.ObserverPattern;

public abstract class Distributor<newsPaper,story> implements Subscriber<newsPaper>{
    private Publisher<story> publisher;

    public Distributor(Publisher<story> publisher) {
        this.publisher = publisher;
    }

    @Override
    public void delivery(newsPaper np) {
        publisher.publish(extractStory(np));
    }


    public abstract story extractStory(newsPaper np);
}
