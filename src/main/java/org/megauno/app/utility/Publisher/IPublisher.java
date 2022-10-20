package org.megauno.app.utility.Publisher;

import org.megauno.app.utility.Publisher.normal.Subscriber;

public interface IPublisher<newsPaper> {
    void addSubscriber(Subscriber<newsPaper> sub);

    boolean removeSubscriber(Subscriber<newsPaper> sub);

}
