package nl.daedalus.engine.renderer.camera;

import nl.daedalus.engine.events.Event;
import nl.daedalus.engine.events.MouseScrolledEvent;
import nl.daedalus.engine.events.WindowResizeEvent;
import nl.daedalus.engine.input.DaedalusInput;
import nl.daedalus.engine.math.Vec3f;
import nl.daedalus.engine.math.Vec4f;

import static nl.daedalus.engine.input.KeyCodes.*;
import static nl.daedalus.engine.input.KeyCodes.DAE_KEY_D;

public class OrthographicCameraController extends CameraController{

    private OrthographicCamera camera;

    // Control parameters
    float zoomLevel = 1.0f;
    float translationSpeed = 2.0f;
    float rotationSpeed = 100.0f;

    // State
    boolean rotatable;
    float aspectRatio;
    float rotation = 0.0f;
    Vec3f position = new Vec3f(0.0f, 0.0f, 0.0f);
    Vec4f bounds;

    public OrthographicCameraController(float aspectRatio, boolean rotatable) {
        this.aspectRatio = aspectRatio;
        this.rotatable = rotatable;
        bounds = new Vec4f(-aspectRatio * zoomLevel, aspectRatio * zoomLevel, -zoomLevel, zoomLevel);
        camera = new OrthographicCamera(bounds.r(), bounds.g(), bounds.b(), bounds.a());
    }

    @Override
    public void onUpdate(float dt) {
        if (DaedalusInput.isKeyPressed(DAE_KEY_UP) || DaedalusInput.isKeyPressed(DAE_KEY_W)) {
            position = position.addY(translationSpeed * dt);
        } else if (DaedalusInput.isKeyPressed(DAE_KEY_DOWN) || DaedalusInput.isKeyPressed(DAE_KEY_S)) {
            position = position.subtractY(translationSpeed * dt);
        }
        if (DaedalusInput.isKeyPressed(DAE_KEY_LEFT) || DaedalusInput.isKeyPressed(DAE_KEY_A)) {
            position = position.subtractX(translationSpeed * dt);
        } else if (DaedalusInput.isKeyPressed(DAE_KEY_RIGHT) || DaedalusInput.isKeyPressed(DAE_KEY_D)) {
            position = position.addX(translationSpeed * dt);
        }

        if (rotatable) {
            if (DaedalusInput.isKeyPressed(DAE_KEY_Q)) {
                rotation -= rotationSpeed * dt;
            } else if (DaedalusInput.isKeyPressed(DAE_KEY_E)) {
                rotation += rotationSpeed * dt;
            }
            camera.setRotation(rotation);
        }

        camera.setPosition(position);
    }

    @Override
    public void onEvent(Event e) {
        if (e.getType() == Event.EventType.MouseScrolled) {
           onMouseScrolled((MouseScrolledEvent) e);
        } else if (e.getType() == Event.EventType.WindowResized) {
            onWindowResized((WindowResizeEvent) e);
        }
    }

    public void onMouseScrolled(MouseScrolledEvent e) {
        zoomLevel -= e.getyOffset();
        zoomLevel = Math.max(zoomLevel, 0.25f); // clamp max zoom
        recalculateView();
    }

    public void onWindowResized(WindowResizeEvent e) {
        aspectRatio = ((float) e.getWidth()) / ((float) e.getHeight());
        recalculateView();
    }

    public OrthographicCamera getCamera() {
        return this.camera;
    }

    private void recalculateView() {
        bounds = new Vec4f(-aspectRatio * zoomLevel, aspectRatio * zoomLevel, -zoomLevel, zoomLevel);
        camera.setProjection(bounds.r(), bounds.g(), bounds.b(), bounds.a());
    }
}
