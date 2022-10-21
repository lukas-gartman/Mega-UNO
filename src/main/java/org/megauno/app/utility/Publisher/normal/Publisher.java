package org.megauno.app.utility.Publisher.normal;

import org.megauno.app.utility.Publisher.IPublisher;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Publisher<newsPaper> implements IPublisher<newsPaper> {
    private List<Subscriber<newsPaper>> subscribers = new ArrayList<>();

    public Publisher() {
    }

    public void addSubscriber(Subscriber<newsPaper> sub) {
        subscribers.add(sub);
    }

    public boolean removeSubscriber(Subscriber<newsPaper> sub) {
        return subscribers.remove(sub);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Publisher<?> publisher)) return false;
        return Objects.equals(subscribers, publisher.subscribers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(subscribers);
    }

    public void publish(newsPaper np) {
        for (Subscriber<newsPaper> sub : subscribers) {
            sub.delivery(np);
        }
    }
}
