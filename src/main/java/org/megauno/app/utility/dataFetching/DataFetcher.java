package org.megauno.app.utility.dataFetching;

import java.util.ArrayList;
import java.util.List;

//This class has some usefull methods for the use of IDataRetrivers
//Thus it is meant to be used for the same purpose
public class DataFetcher<keyType, dataType> {
    //Backup keys for when data is failed to be retrived from a specific key
    private List<keyType> backUps;
    private IDataRetriever<keyType, dataType> dataRetriever;

    public DataFetcher(IDataRetriever<keyType, dataType> dataRetriever, List<keyType> backUp) {
        this.backUps = backUp;
        this.dataRetriever = dataRetriever;
    }

    public DataFetcher(IDataRetriever<keyType, dataType> dataRetriever) {
        this.dataRetriever = dataRetriever;
        this.backUps = new ArrayList<>();
    }

    /**
     * Tries to retrieve data from a key, or backup, if not possible it trows an error
     * @param key the key to the data which is to be returned
     * @return the data from the key if possible, else data from backup keys
     */
    public dataType tryGetDataUnSafe(keyType key) {

        try {
            return dataRetriever.retrieveData(key);
        } catch (Exception e) {
            for (keyType backUp : backUps) {
                try {
                    return dataRetriever.retrieveData(key);
                } catch (Exception e2) {
                }
            }

            throw new RuntimeException(e);

        }
    }

    /**
     * Tries to retrieve data from a key
     * @param key the key to the data which is to be returned
     * @return the data from the key if possible, else data from backup keys, els null
     */
    public dataType tryGetDataSafe(keyType key) {
        try {
            return tryGetDataUnSafe(key);
        } catch (Exception e) {
            return null;
        }
    }


}
