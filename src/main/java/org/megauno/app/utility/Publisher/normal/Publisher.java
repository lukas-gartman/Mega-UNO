package org.megauno.app.utility.Publisher.normal;

import org.megauno.app.utility.Publisher.IPublisher;

import java.util.ArrayList;
import java.util.List;

public class Publisher<newsPaper> implements IPublisher<newsPaper> {
    private List<Subscriber<newsPaper>> subscribers = new ArrayList<>();

    public Publisher() {}
    public void addSubscriber(Subscriber<newsPaper> sub){
        subscribers.add(sub);
    }
    public boolean removeSubscriber(Subscriber<newsPaper> sub){
        return subscribers.remove(sub);
    }


    public void publish(newsPaper np){
        for (Subscriber<newsPaper> sub: subscribers) {
            sub.delivery(np);
        }
    }
}