package org.megauno.app.utility.dataFetching;

public interface IDataRetriever<keyType,dataType> {
    dataType retrieveData(keyType key);
}
