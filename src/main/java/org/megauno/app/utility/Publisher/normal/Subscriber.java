package org.megauno.app.utility.Publisher.normal;

/**
 * An interface for a subscriber to a publisher in teh observer pattern
 */
public interface Subscriber<newsPaper> {
    /**
     * The method through with the newspaper will be delivered tp
     * @param np the newspaper to be delivered
     */
    void delivery(newsPaper np);
}
