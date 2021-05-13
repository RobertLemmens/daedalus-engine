package nl.daedalus.engine.renderer.camera;

import nl.daedalus.engine.math.Mat4f;
import nl.daedalus.engine.math.Vec4f;

public class SceneCamera extends Camera{

    private float orthographicSize = 10;
    private float orthographicNear = -1.0f;
    private float orthographicFar = 1.0f;

    private float aspectRatio = 0.0f;

    private Vec4f bounds = new Vec4f(1.0f);

    public void setOrhograpic(float size, float near, float far) {
        orthographicSize = size;
        orthographicNear = near;
        orthographicFar = far;
        calculateProjection();
    }

    public void setOrthographicSize(float size) {
        this.orthographicSize = size;
        calculateProjection();
    }

    public void setViewportSize(int width, int height) {
        aspectRatio = (float) width / (float) height;
        calculateProjection();
    }

    public void calculateProjection() {
       float orthoLeft = -orthographicSize * aspectRatio * 0.5f;
       float orthoRight = orthographicSize * aspectRatio * 0.5f;
       float orthoBottom = -orthographicSize * 0.5f;
       float orthoTop = orthographicSize * 0.5f;
       bounds = new Vec4f(orthoLeft, orthoRight, orthoBottom, orthoTop);

       setProjection(Mat4f.ortho(orthoLeft, orthoRight, orthoBottom, orthoTop, orthographicNear, orthographicFar));
    }

    public Vec4f getBounds() {
        return bounds;
    }


}
