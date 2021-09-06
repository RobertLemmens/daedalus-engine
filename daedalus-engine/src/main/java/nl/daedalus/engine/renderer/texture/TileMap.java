package nl.daedalus.engine.renderer.texture;

import nl.daedalus.engine.debug.DebugContext;
import nl.daedalus.engine.renderer.Renderer;
import nl.daedalus.engine.util.Renderable;

public class TileMap implements Renderable {

    public SubTexture[][] tileMap; //TODO layers

    public TileMap(SubTexture[][] tileMap) {
        this.tileMap = tileMap;
    }

    public float getMapHeight() {
        return tileMap.length;
    }

    public float getMapWidth() {
        return tileMap[0].length;
    }

    @Override
    public void onRender() {
        long startTime = System.nanoTime();

        for (float y = 0 ; y < tileMap.length; y++) { //todo we might want a single texture draw call as a tilemap
            for (float x = 0; x < tileMap[0].length; x++) {
                Renderer.drawQuad(x - getMapWidth()/2, getMapHeight() - y - getMapHeight()/2, 1, 1, tileMap[(int)y][(int)x]);
            }
        }

        long stopTime = System.nanoTime();
        DebugContext.add("tilemap#onRender()", startTime, stopTime);
    }
}
