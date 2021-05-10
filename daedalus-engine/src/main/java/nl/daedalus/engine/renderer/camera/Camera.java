package nl.daedalus.engine.renderer.camera;

import nl.daedalus.engine.math.Mat4f;

public class Camera {

    private boolean main;
    private Mat4f projection = new Mat4f();

    public boolean isMain() {
        return main;
    }

    public void setMain(boolean main) {
        this.main = main;
    }

    public Mat4f getProjection() {
        return projection;
    }

    public void setProjection(Mat4f projection) {
        this.projection = projection;
    }
}
