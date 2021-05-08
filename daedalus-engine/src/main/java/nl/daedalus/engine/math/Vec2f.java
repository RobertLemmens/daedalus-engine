package nl.daedalus.engine.math;

public record Vec2f(float x, float y) {

    public float[] asFloat() {
        return new float[] {x, y};
    }
}
