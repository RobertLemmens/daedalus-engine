package nl.daedalus.engine.math;

public record Vec2f(float x, float y) {

    public Vec2f(float[] floats) {
        this(floats[0], floats[1]);
    }

    public float[] asFloats() {
        return new float[] {x, y};
    }

}
