package nl.daedalus;

import nl.daedalus.engine.audio.AudioManager;
import nl.daedalus.engine.audio.Music;
import nl.daedalus.engine.audio.Sound;
import nl.daedalus.engine.core.Constants;
import nl.daedalus.engine.core.DaedalusLogger;
import nl.daedalus.engine.core.DaedalusLoop;
import nl.daedalus.engine.events.Event;
import nl.daedalus.engine.events.WindowResizeEvent;
import nl.daedalus.engine.input.DaedalusInput;
import nl.daedalus.engine.math.Mat4f;
import nl.daedalus.engine.math.Vec2f;
import nl.daedalus.engine.renderer.FrameBuffer;
import nl.daedalus.engine.renderer.texture.SubTexture;
import nl.daedalus.engine.renderer.texture.Texture;
import nl.daedalus.engine.renderer.texture.TextureAtlas;
import nl.daedalus.engine.renderer.texture.TileMap;
import nl.daedalus.engine.scene.Entity;
import nl.daedalus.engine.scene.Scene;
import nl.daedalus.engine.scene.components.*;

import java.util.HashMap;
import java.util.Map;

import static nl.daedalus.engine.input.KeyCodes.*;


public class SandboxLoop implements DaedalusLoop {


    // Scene is part of the ECS. This is an opinionated way of using the engine.
    private Scene ecsScene;

    // CustomScene is more hands on, do whatever you want. It shows the basics of interacting with the engine how you see fit.
    private CustomScene customScene;

    private Texture urbanTileMap;
    private TextureAtlas urbanAtlas;

    private int windowWidth;
    private int windowHeight;

    private FrameBuffer frameBuffer;

    private Sound laserSound; // test

    private Map<Character, SubTexture> tileMapMappings = new HashMap<>(); // TODO implement in TextureAtlas?

    private char[][] tileMapTiles = {
            {'w','w','w','w','w','w','w','w','w','w','w','w','w','w','w','w','w','w','w','w','w' },
            {'w','w','w','w','w','w','w','w','w','w','w','w','w','w','w','w','w','w','w','w','w' },
            {'w','w','w','w','w','w','w','w','w','w','w','w','w','w','w','w','w','w','w','w','w' },
            {'w','w','w','w','w','w','w','w','w','w','w','w','w','w','w','w','w','w','w','w','w' },
            {'w','w','w','w','w','w','w','w','w','w','w','w','w','w','w','w','w','w','w','w','w' },
            {'w','w','w','w','w','w','w','w','w','w','w','w','w','w','w','w','w','w','w','w','w' },
            {'w','w','w','w','w','w','w','w','w','w','w','w','w','w','w','w','w','w','w','w','w' },
            {'w','w','w','w','w','w','w','w','w','w','w','w','w','w','w','w','w','w','w','w','w' },
            {'w','w','w','w','w','w','w','g','g','g','w','w','w','w','w','w','w','w','w','w','w' },
            {'w','w','w','w','w','w','w','g','g','g','w','w','w','w','w','w','w','w','w','w','w' },
            {'w','w','w','w','w','w','w','w','w','w','w','w','w','w','w','w','w','w','w','w','w' },
            {'w','w','w','w','w','w','w','r','t','t','t','t','t','t','y','w','w','w','w','w','w' },
            {'w','w','w','w','w','w','w','v','g','g','g','g','g','g','n','w','w','w','w','w','w' },
            {'w','w','w','w','w','w','w','v','g','g','g','g','g','g','n','w','w','w','w','w','w' },
            {'w','w','w','w','w','w','w','v','g','g','g','g','g','g','n','w','w','w','w','w','w' },
            {'w','w','w','w','w','w','w','v','g','g','g','g','g','g','n','w','w','w','w','w','w' },
            {'w','w','w','w','w','w','w','v','g','g','g','g','g','g','n','w','w','w','w','w','w' },
            {'w','w','w','w','w','w','w','f','b','b','b','b','b','b','h','w','w','w','w','w','w' },
            {'w','w','w','w','w','w','w','w','w','w','w','w','w','w','w','w','w','w','w','w','w' },
            {'w','w','w','w','w','w','w','w','w','w','w','w','w','w','w','w','w','w','w','w','w' },
            {'w','w','w','w','w','w','w','w','w','w','w','w','w','w','w','w','w','w','w','w','w' },
            {'w','w','w','w','w','w','w','w','w','w','w','w','w','w','w','w','w','w','w','w','w' },
            {'w','w','w','w','w','w','w','w','w','w','w','w','w','w','w','w','w','w','w','w','w' },
            {'w','w','w','w','w','w','w','w','w','w','w','w','w','w','w','w','w','w','w','w','w' },
            {'w','w','w','w','w','w','w','w','w','w','w','w','w','w','w','w','w','w','w','w','w' },
            {'w','w','w','w','w','w','w','w','w','w','w','w','w','w','w','w','w','w','w','w','w' },
            {'w','w','w','w','w','w','w','w','w','w','w','w','w','w','w','w','w','w','w','w','w' },
            {'w','w','w','w','w','w','w','w','w','w','w','w','w','w','w','w','w','w','w','w','w' },
            {'w','w','w','w','w','w','w','w','w','w','w','w','w','w','w','w','w','w','w','w','w' }
    };

    @Override
    public void onInit() {
        frameBuffer = FrameBuffer.create(new Vec2f(Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT));
        windowWidth = Constants.WINDOW_WIDTH;
        windowHeight = Constants.WINDOW_HEIGHT;

        urbanTileMap = Texture.create("textures/tilemaps/urban/tilemap_packed.png");
        urbanAtlas = new TextureAtlas(urbanTileMap, new Vec2f(16.0f, 16.0f), 0);
        urbanAtlas.addCombined("tree",new Vec2f(1.0f, 2.0f), new Vec2f(16,5));
        tileMapMappings.put('w', urbanAtlas.subTextures[10][9]);
        tileMapMappings.put('g', urbanAtlas.subTextures[16][1]);
        tileMapMappings.put('h', urbanAtlas.subTextures[15][2]);
        tileMapMappings.put('y', urbanAtlas.subTextures[17][2]);
        tileMapMappings.put('r', urbanAtlas.subTextures[17][0]);
        tileMapMappings.put('f', urbanAtlas.subTextures[15][0]);
        tileMapMappings.put('v', urbanAtlas.subTextures[16][0]);
        tileMapMappings.put('b', urbanAtlas.subTextures[15][1]);
        tileMapMappings.put('n', urbanAtlas.subTextures[16][2]);
        tileMapMappings.put('t', urbanAtlas.subTextures[17][1]);

        SubTexture[][] tiles = new SubTexture[tileMapTiles.length][tileMapTiles[0].length];
        for (int y = 0; y < tileMapTiles.length; y++) {
            for (int x = 0; x < tileMapTiles[0].length; x++) {
                tiles[y][x] = tileMapMappings.get(tileMapTiles[y][x]);
            }
        }
        TileMap tileMap = new TileMap(tiles);

        // Freeform scene example
        customScene = new CustomScene(urbanAtlas, urbanTileMap);

        // ECS example code
        ecsScene = new Scene();
        ecsScene.setTileMap(tileMap);


        Entity player = ecsScene.createEntity("player");
        TransformComponent transformComponent = new TransformComponent(new Mat4f());
        transformComponent.setPosition(0.0f, 1.5f, 0.1f);
        player.add(transformComponent);
        player.add(new SpriteComponent(urbanAtlas.subTextures[5][24]));
        SubTexture[][] playerSprites = { // todo make api nicer, dont want to keep working with arrays like this
                {urbanAtlas.subTextures[0][23], urbanAtlas.subTextures[0][24], urbanAtlas.subTextures[0][25], urbanAtlas.subTextures[0][26]},
                {urbanAtlas.subTextures[1][23], urbanAtlas.subTextures[1][24], urbanAtlas.subTextures[1][25], urbanAtlas.subTextures[1][26]},
                {urbanAtlas.subTextures[2][23], urbanAtlas.subTextures[2][24], urbanAtlas.subTextures[2][25], urbanAtlas.subTextures[2][26]}
        };

        Entity player2 = ecsScene.createEntity("player2");
        TransformComponent transformComponent2 = new TransformComponent(new Mat4f());
        SpriteComponent spriteComponent = new SpriteComponent(playerSprites[0][2]);
        InputComponent inputComponent = new InputComponent(() -> {
            if (DaedalusInput.isKeyPressed(DAE_KEY_UP) || DaedalusInput.isKeyPressed(DAE_KEY_W)) {
                transformComponent2.setPosition(transformComponent2.getPosition().addY(0.1f));
            } else if (DaedalusInput.isKeyPressed(DAE_KEY_DOWN) || DaedalusInput.isKeyPressed(DAE_KEY_S)) {
                transformComponent2.setPosition(transformComponent2.getPosition().subtractY(0.1f));
            }

            if (DaedalusInput.isKeyPressed(DAE_KEY_LEFT) || DaedalusInput.isKeyPressed(DAE_KEY_A)) {
                transformComponent2.setPosition(transformComponent2.getPosition().subtractX(0.1f));
            } else if (DaedalusInput.isKeyPressed(DAE_KEY_RIGHT) || DaedalusInput.isKeyPressed(DAE_KEY_D)) {
                transformComponent2.setPosition(transformComponent2.getPosition().addX(0.1f));
            }
        });
        transformComponent2.setPosition(1.0f, 0.0f, 0.5f);
        player2.add(inputComponent);
        player2.add(transformComponent2);
        player2.add(new ScriptComponent(PlayerController.class));

        player2.add(spriteComponent);

        SpriteAnimatorComponent spriteAnimatorComponent = new SpriteAnimatorComponent(playerSprites, spriteComponent);
        spriteAnimatorComponent.setAnimationsPerSecond(8);
        spriteAnimatorComponent.setupAnimation(SpriteAnimatorComponent.AnimationDirection.TOP_TO_BOTTOM);
        player2.add(spriteAnimatorComponent);

        Entity camera = ecsScene.createEntity("camera");
        CameraComponent cameraComponent = new CameraComponent();
        cameraComponent.getCamera().setOrthographicSize(14);
        camera.add(cameraComponent);
        camera.add(new TransformComponent(new Mat4f()));
        ecsScene.onViewportResize(windowWidth, windowHeight);

        //// initialize audio
        laserSound = AudioManager.loadSound("pew", "audio/laser1.ogg");
//        Music music = AudioManager.loadMusic("bg", "audio/Friends.ogg");
//        music.play();
    }


    @Override
    public void onUpdate(float dt) {
        if (windowWidth > 0.0f && windowHeight > 0.0f && // todo move to engine?
                (frameBuffer.getSize().x() != windowWidth || frameBuffer.getSize().y() != windowHeight)) {

            frameBuffer.resize(new Vec2f(windowWidth, windowHeight));

            ecsScene.onViewportResize(windowWidth, windowHeight);
        }

        if (!ecsScene.isPlaying()) {
           ecsScene.beginPlay();
        }

//        customScene.onUpdate(dt);
        ecsScene.onUpdate(dt);
    }

    @Override
    public void onEvent(Event e) {
        if (e.getType() == Event.EventType.WindowResized) {
            onWindowResized((WindowResizeEvent) e);
        }
//        customScene.onEvent(e);
        ecsScene.onEvent(e);

        if (e.getType() == Event.EventType.MouseButtonPressed) {
            laserSound.play();
        }
    }

    @Override
    public void onExit() {
        DaedalusLogger.warn("Closing application");
    }

    public void onWindowResized(WindowResizeEvent e) {
        windowWidth = e.getWidth();
        windowHeight = e.getHeight();
    }
}
