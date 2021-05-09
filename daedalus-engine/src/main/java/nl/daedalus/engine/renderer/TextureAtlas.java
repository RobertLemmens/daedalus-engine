package nl.daedalus.engine.renderer;

import nl.daedalus.engine.math.Vec2f;
import nl.daedalus.engine.renderer.Texture;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TextureAtlas {

    private Texture texture;
    private Vec2f cellSize;

    public SubTexture[][] subTextures;

    public Map<String, SubTexture> combinedTextures;


    public TextureAtlas(Texture texture, Vec2f cellSize) {
        combinedTextures = new HashMap<>();
        this.texture = texture;
        this.cellSize = cellSize;
        int width = texture.getWidth();
        int height = texture.getHeight();

        subTextures = new SubTexture[height / (int)cellSize.y()][width / (int)cellSize.x()];

        for (int y = 0; y < height / cellSize.y(); y++) {
            for (int x = 0; x < width / cellSize.x(); x++) {
                subTextures[y][x] = SubTexture.fromCoords(texture, new Vec2f(x,y), cellSize, new Vec2f(1.0f, 1.0f));
            }
        }
    }

    public void addCombined(String name, Vec2f spriteSize, Vec2f textureStart) {
       combinedTextures.put(name, SubTexture.fromCoords(texture, textureStart, cellSize, spriteSize));
    }

    public SubTexture getCombined(String name) {
        return combinedTextures.get(name);
    }


}
