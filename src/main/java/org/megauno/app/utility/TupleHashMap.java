package org.megauno.app.utility;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TupleHashMap<l,r> implements BiDicrectionalHashMap<l,r> {
    List<Tuple<l,r>> list = new ArrayList<>();
    @Override
    public boolean put(l left, r right) {
        for (Tuple<l,r> t:list) {
            if(t.l == left || t.r == right){
                return false;
            }
        }
        list.add(new Tuple<>(left,right));
        return true;
    }

    @Override
    public boolean removeLeft(l left) {
        for (Tuple<l,r> t:list) {
            if(t.l == left){
                list.remove(t);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean removeRight(r right) {
        for (Tuple<l,r> t:list) {
            if(t.r == right){
                list.remove(t);
                return true;
            }
        }
        return false;
    }

    @Override
    public l getLeft(r right) {
        for (Tuple<l,r> t:list) {
            if(t.r == right){
                return t.l;
            }
        }
       return null;
    }

    @Override
    public r getRight(l left) {
        for (Tuple<l,r> t:list) {
            if(t.l == left){
                return t.r;
            }
        }
        return null;
    }

    @Override
    public Set<l> getLeftKeys() {
        Set<l> set = new HashSet<>();
        for (Tuple<l,r> t:list) {
            set.add(t.l);
        }
        return set;
    }

    @Override
    public Set<r> getRightKeys() {
        Set<r> set = new HashSet<>();
        for (Tuple<l,r> t:list) {
            set.add(t.r);
        }
        return set;
    }

    @Override
    public int size() {
        return list.size();
    }
}
