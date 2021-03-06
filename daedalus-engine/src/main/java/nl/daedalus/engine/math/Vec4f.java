package nl.daedalus.engine.math;

public record Vec4f(float r, float g, float b, float a) {
    public Vec4f(float ident) {
        this(ident, ident, ident, ident);
    }

    public float[] asFloats() {
        return new float[]{r,g,b,a};
    }
}
