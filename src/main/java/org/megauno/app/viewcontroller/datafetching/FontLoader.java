package org.megauno.app.viewcontroller.datafetching;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import org.megauno.app.utility.dataFetching.IDataRetriever;

public class FontLoader implements IDataRetriever<String, BitmapFont> {
    @Override
    public BitmapFont retrieveData(String key) {
        return new BitmapFont(Gdx.files.internal(key));
    }
}
