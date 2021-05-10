package nl.daedalus.engine.scene.components;

import nl.daedalus.engine.renderer.camera.SceneCamera;

public class CameraComponent extends Component {

    private static final String NAME = "CameraComponent";
    private SceneCamera camera;

    public CameraComponent() {
        this.camera = new SceneCamera();
    }
    public CameraComponent(SceneCamera camera) {
        this.camera = camera;
    }

    public SceneCamera getCamera() {
        return camera;
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public void onUpdate(float dt) {

    }
}
