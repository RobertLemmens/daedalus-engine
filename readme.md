# Daedalus engine
### 2D Java game engine

The engine is still in its early stages, but capable of making basic gameloops already.

1. Create a gameloop by implementing DaedalusLoop:

```java
public class SandboxLoop implements DaedalusLoop {

    private OrthographicCameraController cameraController;
    private Texture checkerboard;

    @Override
    public void onInit() {
        cameraController = new OrthographicCameraController((float)Constants.WINDOW_WIDTH / (float)Constants.WINDOW_HEIGHT, true);
        checkerboard = Texture.create("textures/Checkerboard.png");
    }

    @Override
    public void onUpdate(float dt) {

        cameraController.onUpdate(dt);

        Renderer.begin(cameraController.getCamera());
        Renderer.drawQuad(0.0f, 0.0f, Mat4f.scale(new Vec3f(0.5f)), 45, checkerboard);
        Renderer.end();
    }

    @Override
    public void onEvent() {

    }
}

```

2. Initialize the engine in main by extending DaedalusApplication and calling run, passing it your loop class: 

```java
public class SandboxApplication extends DaedalusApplication {

    public static void main(String[] args) {
        SandboxApplication sandboxApplication = new SandboxApplication();
        sandboxApplication.run(new SandboxLoop());
    }

}

```