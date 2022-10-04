package org.megauno.app.viewcontroller.datafetching;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import org.megauno.app.utility.PathLoader;

import java.util.List;

public class SpriteLoader extends PathLoader<Sprite> {


    public SpriteLoader(List<String> backUp, String defaultPath) {
        super(backUp, defaultPath);
    }
    public SpriteLoader(String defaultPath) {
        super(defaultPath);
    }
    public SpriteLoader(List<String> backUp) {
        super(backUp);
    }
    public SpriteLoader() {
        super();
    }

    @Override
    public Sprite getDataFromPath(String key) {
        return new Sprite(new Texture(key));
    }
}
