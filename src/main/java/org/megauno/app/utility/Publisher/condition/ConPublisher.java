package org.megauno.app.utility.Publisher.condition;

import org.megauno.app.utility.Publisher.IPublisher;
import org.megauno.app.utility.Publisher.normal.Subscriber;

import java.util.HashMap;

public class ConPublisher<newsPaper> implements IPublisher<newsPaper> {
    private HashMap<Subscriber<newsPaper>, DataCon<newsPaper>> conDict = new HashMap<Subscriber<newsPaper>,DataCon<newsPaper>>();


    public void addSubscriberWithCondition(Subscriber<newsPaper> sub, DataCon<newsPaper> con) {
        conDict.put(sub,con);
    }


    @Override
    public void addSubscriber(Subscriber<newsPaper> sub) {
        addSubscriberWithCondition(sub,(np) -> {return true;});
    }

    @Override
    public boolean removeSubscriber(Subscriber<newsPaper> sub) {
        if(conDict.remove(sub) == null){
            return false;
        }else{
            return true;
        }
    }

    public void publish(newsPaper np){
        for (Subscriber<newsPaper> sub : conDict.keySet()){
            sub.delivery(np);
        }
    }
}
