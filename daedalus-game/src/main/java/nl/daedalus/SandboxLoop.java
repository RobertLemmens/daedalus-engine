package nl.daedalus;

import nl.daedalus.engine.core.Constants;
import nl.daedalus.engine.core.DaedalusLogger;
import nl.daedalus.engine.core.DaedalusLoop;
import nl.daedalus.engine.events.Event;
import nl.daedalus.engine.math.Vec4f;
import nl.daedalus.engine.renderer.Shader;
import nl.daedalus.engine.math.Mat4f;
import nl.daedalus.engine.math.Vec3f;
import nl.daedalus.engine.renderer.*;
import nl.daedalus.engine.renderer.camera.OrthographicCameraController;

public class SandboxLoop implements DaedalusLoop {

    private OrthographicCameraController cameraController;
    private Texture checkerboard;
    private Texture transparent;

    private Vec4f noTint = new Vec4f(1.0f, 1.0f, 1.0f, 1.0f);
    private Vec4f blueTint = new Vec4f(0.0f, 0.0f, 0.9f, 1.0f);

    @Override
    public void onInit() {
        cameraController = new OrthographicCameraController((float)Constants.WINDOW_WIDTH / (float)Constants.WINDOW_HEIGHT, true);
        cameraController.setZoomLevel(1);

        checkerboard = Texture.create("textures/Checkerboard.png");
        transparent = Texture.create("textures/transparent.png");
    }

    @Override
    public void onUpdate(float dt) {

        cameraController.onUpdate(dt);

        Renderer.begin(cameraController.getCamera());

        for(int y = -5; y < 5; y+=1) {
            for (int x = -5; x < 5; x+=1) {
                Vec3f position = new Vec3f(x * 0.11f, y * 0.11f, 1f);
                Renderer.drawQuad(position, Mat4f.scale(new Vec3f(0.1f)), new Vec4f((x + 5.0f) / 10.0f, 0.4f, (y + 5.0f) / 10.0f, 1.0f));
            }
        }

        Renderer.drawQuad(-0.3f, 0.0f, Mat4f.scale(new Vec3f(0.5f)), 45, checkerboard, 2, noTint);
        Renderer.drawQuad(0.4f, 0.0f, Mat4f.scale(new Vec3f(0.5f)), 45, checkerboard, 1, noTint);
        Renderer.drawQuad(0.8f, 0.5f, Mat4f.scale(new Vec3f(0.5f)), 45, checkerboard, 1, blueTint);
        Renderer.drawQuad(new Vec3f(0.2f, 0.7f, 1.0f), Mat4f.scale(new Vec3f(0.5f)), 45, transparent, 1, noTint);

        Renderer.end();
    }

    @Override
    public void onEvent(Event e) {
        cameraController.onEvent(e);
    }
}
