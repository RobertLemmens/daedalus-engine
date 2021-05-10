package nl.daedalus;

import nl.daedalus.engine.core.Constants;
import nl.daedalus.engine.events.Event;
import nl.daedalus.engine.math.Mat4f;
import nl.daedalus.engine.math.Vec2f;
import nl.daedalus.engine.math.Vec3f;
import nl.daedalus.engine.math.Vec4f;
import nl.daedalus.engine.renderer.Renderer;
import nl.daedalus.engine.renderer.camera.OrthographicCameraController;
import nl.daedalus.engine.renderer.texture.SubTexture;
import nl.daedalus.engine.renderer.texture.Texture;
import nl.daedalus.engine.renderer.texture.TextureAtlas;
import nl.daedalus.engine.scene.EventProcessor;
import nl.daedalus.engine.scene.Updatable;

/**
 * This is a custom scene, that shows how to interact with the engine without the ECS
 *
 */
public class CustomScene implements Updatable, EventProcessor {


    private OrthographicCameraController cameraController;
    private Texture checkerboard;
    private Texture urbanTileMap;

    private SubTexture grassTileBottomLeft;
    private SubTexture grassTileBottomMid;
    private SubTexture grassTileBottomRight;

    private TextureAtlas urbanAtlas;

    private Vec4f noTint = new Vec4f(1.0f, 1.0f, 1.0f, 1.0f);
    private Vec4f blueTint = new Vec4f(0.0f, 0.0f, 0.9f, 1.0f);
    float rotation = 0.0f;

    public CustomScene(TextureAtlas urbanAtlas, Texture urbanTileMap) {
        this.urbanAtlas = urbanAtlas;
        cameraController = new OrthographicCameraController((float) Constants.WINDOW_WIDTH / (float)Constants.WINDOW_HEIGHT, true);
        cameraController.setZoomLevel(5);

        checkerboard = Texture.create("textures/Checkerboard.png"); //TODO asset manager

        grassTileBottomMid = SubTexture.fromCoords(urbanTileMap, new Vec2f(1,15) , new Vec2f(16.0f, 16.0f), new Vec2f(1.0f, 1.0f));
        grassTileBottomRight = SubTexture.fromCoords(urbanTileMap, new Vec2f(2,15) , new Vec2f(16.0f, 16.0f), new Vec2f(1.0f, 1.0f));
        grassTileBottomLeft = SubTexture.fromCoords(urbanTileMap, new Vec2f(0,15) , new Vec2f(16.0f, 16.0f), new Vec2f(1.0f, 1.0f));
    }

    @Override
    public void onUpdate(float dt) {

        cameraController.onUpdate(dt);

        Renderer.begin(cameraController.getCamera());

        // Draw some quads with color, some rotating.
        rotation += dt * 50;
        for(float y = -5.0f; y < 5.0f; y+= 0.5f) {
            for (float x = -5.0f; x < 5.0f; x+= 0.5f) {
                Vec4f color = new Vec4f((x + 5.0f) / 10.0f, 0.4f, (y + 5.0f) / 10.0f, 1.0f);
                if ((int)x % 2 == 0) {
                    // draw rotated quad
                    Renderer.drawRotatedQuad(x, y, rotation, 0.45f, 0.45f, color);
                } else {
                    Renderer.drawQuad(x, y, 0.45f, 0.45f, color);
                }
            }
        }

        // Drawing transparent textures is still somewhat tricky (render ordering and Z matters)
        Renderer.drawQuad(-0.3f, 0.0f, 20.0f, 20.0f, checkerboard, 10, noTint);
        Renderer.drawQuad(6.0f, 0.0f, 1.0f, 1.0f, grassTileBottomLeft, 1, noTint);
        Renderer.drawQuad(7.0f, 0.0f, 1.0f, 1.0f, grassTileBottomMid, 1, noTint);
        Renderer.drawQuad(8.0f, 0.0f, 1.0f, 1.0f, grassTileBottomRight, 1, noTint);
        Renderer.drawQuad(6.0f, 1.0f, 1.0f, 1.0f, urbanAtlas.subTextures[16][0], 1, noTint);
        Renderer.drawQuad(7.0f, 1.0f, 1.0f, 1.0f, urbanAtlas.subTextures[16][1], 1, noTint);
        Renderer.drawQuad(8.0f, 1.0f, 1.0f, 1.0f, urbanAtlas.subTextures[16][2], 1, noTint);
        Renderer.drawQuad(6.0f, 2.0f, 1.0f, 1.0f, urbanAtlas.subTextures[17][0], 1, noTint);
        Renderer.drawQuad(7.0f, 2.0f, 1.0f, 1.0f, urbanAtlas.subTextures[17][1], 1, noTint);
        Renderer.drawQuad(8.0f, 2.0f, 1.0f, 1.0f, urbanAtlas.subTextures[17][2], 1, noTint);

        Renderer.drawQuad(new Vec3f(7.0f, 1.5f, 0.9f), Mat4f.scale(new Vec3f(1.0f, 2.0f, 1.0f)), urbanAtlas.getCombined("tree"), 1, noTint);

        Renderer.end();
    }

    @Override
    public void onEvent(Event e) {
        cameraController.onEvent(e);
    }
}
