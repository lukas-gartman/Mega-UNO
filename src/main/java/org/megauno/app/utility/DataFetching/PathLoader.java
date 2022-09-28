package org.megauno.app.utility.DataFetching;

import java.util.List;

public abstract class PathLoader<dataType> extends AbstactDataFetcher<String,dataType> {
    private String defaultPath;
    public PathLoader(List<String> backUp,String defaultPath) {
        super(backUp);
        this.defaultPath = defaultPath;
    }
    public PathLoader(String defaultPath) {
        super();
        this.defaultPath = defaultPath;
    }
    public PathLoader(List<String> backUp) {
        super(backUp);
        this.defaultPath = "";
    }
    public PathLoader() {
        super();
        this.defaultPath = "";
    }



    @Override
    public dataType getData(String key) {
        return getDataFromPath(defaultPath + key);
    }
    public abstract dataType getDataFromPath(String key);

}
