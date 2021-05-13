package nl.daedalus.engine.scene.components;

import nl.daedalus.engine.math.Mat4f;
import nl.daedalus.engine.math.Vec3f;

public class TransformComponent extends Component{

    private static final String NAME = "TransformComponent";

    private Mat4f transform;
    private Vec3f position = new Vec3f(1.0f);
    private Mat4f scale = new Mat4f();
    private float rotation = 0;

    public TransformComponent(Mat4f transform) {
        this.transform = transform;
    }

    public Mat4f getTransform() {
        return transform;
    }

    @Override
    public void onUpdate(float dt) {
        if (rotation == 0) {
            transform = Mat4f.translate(position).multiply(scale);
        } else {
            transform = Mat4f.translate(position).multiply(Mat4f.rotate(rotation, new Vec3f(0.0f, 0.0f, 1.0f))).multiply(scale);
        }
    }

    public void setRotation(float rotation) {
        this.rotation = rotation;
    }

    public void setTransform(Mat4f transform) {
        this.transform = transform;
    }

    public void setPosition(float x, float y, float z) {
        position = new Vec3f(x, y, z);
    }

    public void setPosition(Vec3f vector) {
        this.position = vector;
    }

    public Vec3f getPosition() {
        return position;
    }

    public void setScale(int xScale, int yScale) {
        scale = Mat4f.scale(new Vec3f(xScale, yScale, 1.0f));
    }

    @Override
    public String getName() {
        return NAME;
    }
}
