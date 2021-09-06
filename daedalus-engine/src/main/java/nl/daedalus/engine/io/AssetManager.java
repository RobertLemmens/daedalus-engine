package nl.daedalus.engine.io;

import nl.daedalus.engine.renderer.texture.Texture;

public class AssetManager {

    public Asset loadSound() {
       return null;
    }

    public Asset loadTilemap() {
        return null;
    }

    public Texture loadTexture(String path) {
        return Texture.create(path);
    }

    public Asset loadTMX() {
        return null;
    }

    public Asset loadTextureAtlas() {
        return null;
    }

}
