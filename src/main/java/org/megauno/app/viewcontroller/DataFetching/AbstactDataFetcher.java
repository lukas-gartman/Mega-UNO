package org.megauno.app.viewcontroller.DataFetching;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstactDataFetcher<keyType,dataType> implements IDataFetcher<keyType,dataType> {
    private List<keyType> backUps;

    public AbstactDataFetcher(List<keyType> backUp) {
        this.backUps = backUp;
    }
    public AbstactDataFetcher() {
        this.backUps = new ArrayList<>();
    }

    @Override
    public abstract dataType getData(keyType key);

    private dataType tryGetDataUnSafe(keyType key){

        try {
            return getData(key);
        }catch (Exception e){
            for (keyType backUp: backUps) {
                try {
                    return getData(backUp);
                }catch (Exception e2){}
            }

            throw new RuntimeException(e);

        }
    }
    public dataType tryGetDataSafe(keyType key){
        try {
            tryGetDataUnSafe(key);
        }catch (Exception e){
            return null;
        }
        return null;
    }


}
