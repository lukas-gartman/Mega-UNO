package org.megauno.app.utility.dataFetching;

//This interface is a generalization for retriving data
// from wherever with any data typ and any key typ for that data
public interface IDataRetriever<keyType, dataType> {
    dataType retrieveData(keyType key);
}
