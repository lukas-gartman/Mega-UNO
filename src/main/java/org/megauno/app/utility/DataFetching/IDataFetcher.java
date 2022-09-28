package org.megauno.app.utility.DataFetching;

public interface IDataFetcher<keyType,dataType> {
    dataType getData(keyType key);
}
