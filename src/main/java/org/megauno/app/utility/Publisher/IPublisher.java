package org.megauno.app.utility.Publisher;

import org.megauno.app.utility.Publisher.ISubscribable;

public interface IPublisher<newsPaper> extends ISubscribable<newsPaper> {
    /**
     * Publishes the newspaper to all the subscribers of the publisher
     * @param np the newspaper to be sent
     */
    void publish(newsPaper np);
}
