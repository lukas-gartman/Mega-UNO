package org.megauno.app.utility.Publisher.dataFetching;

public interface IDataRetriever<keyType,dataType> {
    dataType retrieveData(keyType key);
}
