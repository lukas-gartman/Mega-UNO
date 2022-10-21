package org.megauno.app.utility.Publisher.distribution;

import org.megauno.app.utility.Publisher.normal.Publisher;
import org.megauno.app.utility.Publisher.normal.Subscriber;

public class Distributor<newsPaper, story> {
    private Publisher<newsPaper> publisher = new Publisher<>();
    private Editor<newsPaper, story> editor;

    public Distributor(Editor<newsPaper, story> editor) {
        this.editor = editor;
    }


    public void addSubscriber(Subscriber<newsPaper> sub) {
        publisher.addSubscriber(sub);
    }


    public boolean removeSubscriber(Subscriber<newsPaper> sub) {
        return publisher.removeSubscriber(sub);
    }


    public void publish(story np) {
        publisher.publish(editor.extractStory(np));
    }


}
