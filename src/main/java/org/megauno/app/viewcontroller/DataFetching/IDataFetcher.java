package org.megauno.app.viewcontroller.DataFetching;

public interface IDataFetcher<keyType,dataType> {
    dataType getData(keyType key);
}
