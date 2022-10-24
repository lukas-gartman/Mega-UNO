package org.megauno.app.utility.Publisher.condition;
/**
 * An interface for conditions on data
 */
public interface DataCondition<data> {
    /**
     * The condition
     * @param d the data from which the data can be detremind to be true or false
     * @return the evaluation of the condition
     */
    boolean isOk(data d);
}
