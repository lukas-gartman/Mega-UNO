package org.megauno.app.viewcontroller.datafetching;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import org.megauno.app.utility.dataFetching.IDataRetriever;

public class SpriteLoader implements IDataRetriever<String, Sprite> {
    @Override
    public Sprite retrieveData(String key) {
        return new Sprite(new Texture(key));
    }
}
