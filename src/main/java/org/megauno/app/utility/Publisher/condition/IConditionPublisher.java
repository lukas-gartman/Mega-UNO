package org.megauno.app.utility.Publisher.condition;

import org.megauno.app.utility.Publisher.IPublisher;
import org.megauno.app.utility.Publisher.normal.Subscriber;
/**
 * An interface for a publisher which only delivers a newspaper ro a subscriber if
 * it fits a condition detrmind by the subscriber on subscription
 */
public interface IConditionPublisher<newsPaper> extends IPublisher<newsPaper> {
    /**
     * The method through witch the newspaper will be delivered to
     * @param sub is the subscriber to be subscribed
     * @param con the condition mentioned before
     */
    void addSubscriberWithCondition(Subscriber<newsPaper> sub, DataCondition<newsPaper> con);

}
