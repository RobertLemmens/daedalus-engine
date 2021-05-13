package nl.daedalus.engine.renderer.texture;

import nl.daedalus.engine.math.Vec2f;

import java.util.HashMap;
import java.util.Map;

public class TextureAtlas {

    private Texture texture;
    private Vec2f cellSize;

    public SubTexture[][] subTextures;

    public Map<String, SubTexture> combinedTextures;


    public TextureAtlas(Texture texture, Vec2f cellSize, int pixelMargin) {
        combinedTextures = new HashMap<>();
        this.texture = texture;
        this.cellSize = cellSize;
        int width = texture.getWidth();
        int height = texture.getHeight();

        int tilesY = ((height - pixelMargin*2) - (int)cellSize.y()) / (int)cellSize.y() + 1;
        int tilesX = ((width - pixelMargin*2) - (int)cellSize.x()) / (int) cellSize.x() + 1;
        subTextures = new SubTexture[tilesY][tilesX];

        for (int y = 0; y < tilesY; y++) {
            for (int x = 0; x < tilesX; x++) {
                subTextures[y][x] = SubTexture.fromCoords(texture, new Vec2f(x,y), new Vec2f(cellSize.x() + pixelMargin, cellSize.y() + pixelMargin), new Vec2f(1.0f, 1.0f));
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
