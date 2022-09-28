package org.megauno.app.viewcontroller.DataFetching;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import org.megauno.app.utility.DataFetching.PathLoader;

import java.util.List;

public class FontLoader extends PathLoader<BitmapFont> {

    public FontLoader(List<String> backUp, String defaultPath) {
        super(backUp, defaultPath);
    }

    public FontLoader(String defaultPath) {
        super(defaultPath);
    }

    public FontLoader(List<String> backUp) {
        super(backUp);
    }

    public FontLoader() {
    }

    @Override
    public BitmapFont getDataFromPath(String key) {
        return new BitmapFont(Gdx.files.internal("assets/minecraft.fnt"));
    }
}
