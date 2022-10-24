package org.megauno.app.utility.dataFetching;

import java.util.List;

//DataFetcher for string paths
public class PathDataFetcher<dataType> extends DataFetcher<String, dataType> {
    //The default path where each key should start
    private String defaultPath;

    public PathDataFetcher(IDataRetriever<String, dataType> dataRetriever, String defaultPath, List<String> backUp) {
        super(dataRetriever, backUp);
        this.defaultPath = defaultPath;
    }

    public PathDataFetcher(IDataRetriever<String, dataType> dataRetriever, String defaultPath) {
        super(dataRetriever);
        this.defaultPath = defaultPath;
    }

    @Override
    public dataType tryGetDataUnSafe(String key) {
        return super.tryGetDataUnSafe(defaultPath + key);
    }

}
