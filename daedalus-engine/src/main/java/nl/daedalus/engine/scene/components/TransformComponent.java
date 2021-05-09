package nl.daedalus.engine.scene.components;

import nl.daedalus.engine.math.Mat4f;
import nl.daedalus.engine.math.Vec3f;

public class TransformComponent extends Component{

    private static final String NAME = "TransformComponent";

    private Mat4f transform;
    private Vec3f position = new Vec3f(1.0f);
    private Mat4f scale = new Mat4f();

    public TransformComponent(Mat4f transform) {
        this.transform = transform;
    }

    public Mat4f getTransform() {
        return transform;
    }

    @Override
    public void onUpdate(float dt) {
        transform = Mat4f.translate(position).multiply(scale);
    }

    public void setTransform(Mat4f transform) {
        this.transform = transform;
    }

    public void setPosition(int x, int y, int z) {
        position = new Vec3f(x, y, 1.0f);
        onUpdate(0);
    }

    public void setScale(int xScale, int yScale) {
        scale = Mat4f.scale(new Vec3f(xScale, yScale, 1.0f));
    }

    @Override
    public String getName() {
        return NAME;
    }
}
