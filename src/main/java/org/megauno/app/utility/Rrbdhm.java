package org.megauno.app.utility;

import java.util.HashSet;
import java.util.Set;

public class Rrbdhm<l,r> implements BiDicrectionalHashMap<l,r>{
    BiHashMap<l,EqualsReferenceWrapper<r>> biHashMap;

    public Rrbdhm(BiDicrectionalHashMap<l, EqualsReferenceWrapper<r>> biHashMap) {
        this.biHashMap = new BiHashMap<>(biHashMap);
    }
    public Rrbdhm(){
        biHashMap = new BiHashMap<>();
    }

    @Override
    public void put(l left, r right) {
        biHashMap.put(left,new EqualsReferenceWrapper<>(right));
    }

    @Override
    public boolean removeLeft(l left) {
        return biHashMap.removeLeft(left);
    }

    @Override
    public boolean removeRight(r right) {
        return biHashMap.removeRight(new EqualsReferenceWrapper<>(right));
    }

    @Override
    public l getLeft(r right) {
        return biHashMap.getLeft(new EqualsReferenceWrapper<>(right));
    }

    @Override
    public r getRight(l left) {
        return biHashMap.getRight(left).getItem();
    }

    @Override
    public Set<l> getLeftKeys() {
        return biHashMap.getLeftKeys();
    }

    @Override
    public Set<r> getRightKeys() {
        Set<r> set = new HashSet<>();
        for (EqualsReferenceWrapper<r> right:biHashMap.getRightKeys()) {
            set.add(right.getItem());
        }
        return set;
    }

    @Override
    public int size() {
        return biHashMap.size();
    }
}
