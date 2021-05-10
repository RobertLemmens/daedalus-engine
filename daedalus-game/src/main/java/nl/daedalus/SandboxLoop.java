package nl.daedalus;

import nl.daedalus.engine.core.Constants;
import nl.daedalus.engine.core.DaedalusLoop;
import nl.daedalus.engine.events.Event;
import nl.daedalus.engine.events.WindowResizeEvent;
import nl.daedalus.engine.math.Mat4f;
import nl.daedalus.engine.math.Vec2f;
import nl.daedalus.engine.renderer.FrameBuffer;
import nl.daedalus.engine.renderer.texture.Texture;
import nl.daedalus.engine.renderer.texture.TextureAtlas;
import nl.daedalus.engine.scene.Entity;
import nl.daedalus.engine.scene.Scene;
import nl.daedalus.engine.scene.components.CameraComponent;
import nl.daedalus.engine.scene.components.Component;
import nl.daedalus.engine.scene.components.SpriteComponent;
import nl.daedalus.engine.scene.components.TransformComponent;


public class SandboxLoop implements DaedalusLoop {


    // Scene is part of the ECS. This is an opinionated way of using the engine.
    private Scene testScene;

    // CustomScene is more hands on, do whatever you want. It shows the basics of interacting with the engine how you see fit.
    private CustomScene customScene;

    private Texture urbanTileMap;
    private TextureAtlas urbanAtlas;

    private int windowWidth;
    private int windowHeight;

    private FrameBuffer frameBuffer;

    @Override
    public void onInit() {
        frameBuffer = FrameBuffer.create(new Vec2f(Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT));
        windowWidth = Constants.WINDOW_WIDTH;
        windowHeight = Constants.WINDOW_HEIGHT;

        urbanTileMap = Texture.create("textures/tilemaps/urban/tilemap_packed.png");
        urbanAtlas = new TextureAtlas(urbanTileMap, new Vec2f(16.0f, 16.0f));
        urbanAtlas.addCombined("tree",new Vec2f(1.0f, 2.0f), new Vec2f(16,5));

        customScene = new CustomScene(urbanAtlas, urbanTileMap);

        testScene = new Scene();
        Entity player = testScene.createEntity("player");
        TransformComponent transformComponent = new TransformComponent(new Mat4f());
        transformComponent.setPosition(0.0f, 0.0f, 1);
        player.add(transformComponent);
        player.add(new SpriteComponent(urbanAtlas.subTextures[5][25]));

        Entity camera = testScene.createEntity("camera");
        camera.add(new CameraComponent());
        camera.add(new TransformComponent(new Mat4f()));
        testScene.onViewportResize(windowWidth, windowHeight);
    }

    @Override
    public void onUpdate(float dt) {
        customScene.onUpdate(dt);
        if (windowWidth > 0.0f && windowHeight > 0.0f &&
                (frameBuffer.getSize().x() != windowWidth || frameBuffer.getSize().y() != windowHeight)) {

            frameBuffer.resize(new Vec2f(windowWidth, windowHeight));

            testScene.onViewportResize(windowWidth, windowHeight);
        }


        testScene.onUpdate(dt);
    }

    @Override
    public void onEvent(Event e) {
        if (e.getType() == Event.EventType.WindowResized) {
            onWindowResized((WindowResizeEvent) e);
        }
        customScene.onEvent(e);
        testScene.onEvent(e);
    }

    public void onWindowResized(WindowResizeEvent e) {
        windowWidth = e.getWidth();
        windowHeight = e.getHeight();
    }
}
