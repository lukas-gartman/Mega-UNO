package org.megauno.app.utility;

import java.util.Set;

public interface BiDicrectionalHashMap<l,r> {
    void put(l left, r right);

    boolean removeLeft(l left) ;

    boolean removeRight(r right);

    l getLeft(r right);

    r getRight(l left);

    Set<l> getLeftKeys();

    Set<r> getRightKeys();

    int size();
}
