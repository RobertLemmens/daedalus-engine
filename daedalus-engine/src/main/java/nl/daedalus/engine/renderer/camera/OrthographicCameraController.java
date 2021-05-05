package nl.daedalus.engine.renderer.camera;

import nl.daedalus.engine.input.DaedalusInput;
import nl.daedalus.engine.math.Vec3f;

import static nl.daedalus.engine.input.KeyCodes.*;
import static nl.daedalus.engine.input.KeyCodes.DAE_KEY_D;

public class OrthographicCameraController extends CameraController{

    private OrthographicCamera camera;

    // Control parameters
    float zoomLevel = 1.0f;
    float translationSpeed = 5.0f;
    float rotationSpeed = 100.0f;

    // State
    boolean rotatable;
    float aspectRatio;
    float rotation = 0.0f;
    Vec3f position = new Vec3f(0.0f, 0.0f, 0.0f);

    public OrthographicCameraController(float aspectRatio, boolean rotatable) {
        this.aspectRatio = aspectRatio;
        this.rotatable = rotatable;
        camera = new OrthographicCamera(-aspectRatio * zoomLevel, aspectRatio * zoomLevel, -zoomLevel, zoomLevel);
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

    public OrthographicCamera getCamera() {
        return this.camera;
    }
}
