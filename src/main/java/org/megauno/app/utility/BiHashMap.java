package org.megauno.app.utility;

import java.util.HashMap;
import java.util.Set;

public class BiHashMap<l,r>{
    HashMap<l,r> leftRight = new HashMap<>();
    HashMap<r,l> rightLeft = new HashMap<>();

    public BiHashMap() {}

    public BiHashMap(BiHashMap<l,r> bhm){
        for (l left:bhm.getLeftKeys()) {
            put(left,bhm.getRight(left));
        }
    }

    public void put(l left,r right){
        leftRight.put(left,right);
        rightLeft.put(right,left);
    }
    public boolean removeLeft(l left){
        r right = leftRight.remove(left);
        if(right != null){
            if(rightLeft.remove(right) != null){
                return true;
            }
        }
        return  false;
    }
    public boolean removeRight(r right){
        l left = rightLeft.remove(right);
        if(right != null){
            if(leftRight.remove(left) != null){
                return true;
            }
        }
        return  false;
    }

    public l getLeft(r right){
        return rightLeft.get(right);
    }
    public r getRight(l left){
        return leftRight.get(left);
    }
    public Set<l> getLeftKeys(){
        return leftRight.keySet();
    }
    public Set<r> getRightKeys(){
        return rightLeft.keySet();
    }
    public int size(){
        return leftRight.size();
    }
}