package nl.codebulb.engine.renderer;

import nl.codebulb.engine.math.Mat4f;
import nl.codebulb.engine.math.Vec3f;

public class OrthographicCamera extends Camera {

    private Mat4f projectionMatrix;
    private Mat4f viewMatrix;
    private Mat4f viewProjectionMatrix;
    private Vec3f position = new Vec3f(0,0,0);
    private float rotation = 0.0f;

    public OrthographicCamera(float left, float right, float bottom, float top) {
        projectionMatrix = Mat4f.ortho(left, right, bottom, top, -1.0f, 1.0f);
        viewMatrix = new Mat4f();
        viewProjectionMatrix = projectionMatrix.multiply(viewMatrix);
    }

    // nodig voor repaints pas (movement)
    public void calculateViewMatrix() {
        Mat4f transform = Mat4f.translate(position).multiply(Mat4f.rotate(rotation, new Vec3f(0,0, 1)));

        viewMatrix = transform.inverse();
        viewProjectionMatrix = projectionMatrix.multiply(viewMatrix);
    }

    public Vec3f getPosition() {
        return position;
    }

    public void setPosition(Vec3f position) {
        this.position = position;
    }

    public float getRotation() {
        return rotation;
    }

    public void setRotation(float rotation) {
        this.rotation = rotation;
    }

    public Mat4f getProjectionMatrix() {
        return projectionMatrix;
    }

    public Mat4f getViewMatrix() {
        return viewMatrix;
    }

    public Mat4f getViewProjectionMatrix() {
        return viewProjectionMatrix;
    }
}
