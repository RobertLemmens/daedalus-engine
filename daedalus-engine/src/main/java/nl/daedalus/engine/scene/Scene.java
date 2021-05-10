package nl.daedalus.engine.scene;

import nl.daedalus.engine.events.Event;
import nl.daedalus.engine.math.Mat4f;
import nl.daedalus.engine.math.Vec4f;
import nl.daedalus.engine.renderer.Renderer;
import nl.daedalus.engine.renderer.camera.Camera;
import nl.daedalus.engine.renderer.camera.SceneCamera;
import nl.daedalus.engine.scene.components.CameraComponent;
import nl.daedalus.engine.scene.components.SpriteComponent;
import nl.daedalus.engine.scene.components.TransformComponent;

public class Scene implements Updatable, EventProcessor {

    private int viewportWidth = 0;
    private int viewportHeight = 0;

    public Scene() {
    }

    public void onUpdate(float dt) {

        Camera mainCamera = null;
        Mat4f cameraTransform = null;
        for (Entity e : EntityRegistry.getGroup(TransformComponent.class, CameraComponent.class)) {
            mainCamera = e.getComponent(CameraComponent.class).getCamera();
            cameraTransform = e.getComponent(TransformComponent.class).getTransform();
        }

        // Only update or render if we are actually looking at stuff
        if (mainCamera != null) {
            Renderer.begin(mainCamera, cameraTransform);
            for(Entity e : EntityRegistry.getGroup(TransformComponent.class, SpriteComponent.class)) {
                e.getComponents().forEach(c -> c.onUpdate(dt));
                Renderer.drawQuad(e.getComponent(TransformComponent.class).getTransform(),
                        e.getComponent(SpriteComponent.class).getTexture(), 1, new Vec4f(1.0f));
            }
            Renderer.end();
        }

    }

    public Entity createEntity(String name) {
        return EntityRegistry.createEntity(name);
    }


    @Override
    public void onEvent(Event e) {

    }

    public void onViewportResize(int width, int height) {
        viewportWidth = width;
        viewportHeight = height;
        for (Entity e : EntityRegistry.getGroup(TransformComponent.class, CameraComponent.class)) {
            SceneCamera mainCamera = e.getComponent(CameraComponent.class).getCamera();
            mainCamera.setViewportSize(width, height);
        }

    }
}
