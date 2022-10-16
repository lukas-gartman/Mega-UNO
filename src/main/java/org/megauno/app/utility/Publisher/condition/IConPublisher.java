package org.megauno.app.utility.Publisher.condition;

import org.megauno.app.utility.Publisher.IPublisher;
import org.megauno.app.utility.Publisher.normal.Subscriber;

public interface IConPublisher<newsPaper> extends IPublisher<newsPaper> {
    void addSubscriberWithCondition(Subscriber<newsPaper> sub, DataCon<newsPaper> con);
}
