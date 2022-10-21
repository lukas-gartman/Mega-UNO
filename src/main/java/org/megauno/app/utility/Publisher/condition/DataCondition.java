package org.megauno.app.utility.Publisher.condition;

public interface DataCondition<data> {
    boolean isOk(data d);
}
