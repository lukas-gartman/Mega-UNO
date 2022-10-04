package org.megauno.app.utility;

public interface IDataFetcher<keyType,dataType> {
    dataType getData(keyType key);
}
