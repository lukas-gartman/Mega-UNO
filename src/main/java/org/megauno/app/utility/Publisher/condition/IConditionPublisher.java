package org.megauno.app.utility.Publisher.condition;

import org.megauno.app.utility.Publisher.IPublisher;
import org.megauno.app.utility.Publisher.normal.Subscriber;

public interface IConditionPublisher<newsPaper> extends IPublisher<newsPaper> {
    void addSubscriberWithCondition(Subscriber<newsPaper> sub, DataCondition<newsPaper> con);
}
