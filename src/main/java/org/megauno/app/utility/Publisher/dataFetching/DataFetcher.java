package org.megauno.app.utility.Publisher.dataFetching;

import java.util.ArrayList;
import java.util.List;

public class DataFetcher<keyType,dataType>{
    private List<keyType> backUps;
    private IDataRetriever<keyType,dataType> dataRetriever;

    public DataFetcher(IDataRetriever<keyType,dataType> dataRetriever, List<keyType> backUp) {
        this.backUps = backUp;
        this.dataRetriever = dataRetriever;
    }
    public DataFetcher(IDataRetriever<keyType,dataType> dataRetriever) {
        this.dataRetriever = dataRetriever;
        this.backUps = new ArrayList<>();
    }




    public dataType tryGetDataUnSafe(keyType key){

        try {
            return dataRetriever.retrieveData(key);
        }catch (Exception e){
            for (keyType backUp: backUps) {
                try {
                    return dataRetriever.retrieveData(key);
                }catch (Exception e2){}
            }

            throw new RuntimeException(e);

        }
    }
    public dataType tryGetDataSafe(keyType key){
        try {
            return tryGetDataUnSafe(key);
        }catch (Exception e){
            return null;
        }
    }


}
