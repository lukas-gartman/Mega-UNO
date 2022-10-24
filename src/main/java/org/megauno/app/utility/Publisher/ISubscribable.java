package org.megauno.app.utility.Publisher;

import org.megauno.app.utility.Publisher.normal.Subscriber;

/**
 * An interface for classes that wants to publish some information.
 * @param <newsPaper> The specific information a specific instance of a publisher wants to publish.
 */
public interface ISubscribable<newsPaper> {
    /**
     * Adding a subscriber to the publisher, i.e. someone who's interested
     * of the updates from the publisher.
     * @param sub The subscriber that wants to know something about the publisher.
     */
    void addSubscriber(Subscriber<newsPaper> sub);

    /**
     * When a subscriber no longer is interested in the information from a publisher
     * it is subscribed to, it can remove itself from the publisher.
     * @param sub The subscriber that wants to unsubscribe.
     * @return if the subscriber successfully unsubscribed.
     */
    boolean removeSubscriber(Subscriber<newsPaper> sub);

}
