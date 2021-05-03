package nl.codebulb.engine.math;

public record Vec3f(float x, float y, float z) {
    public Vec3f(float ident) {
        this(ident, ident, ident);
    }

    public float length() {
        return (float) Math.sqrt(x*x + y*y + z*z);
    }

    public Vec3f normalize() {
        return divide(length());
    }

    public Vec3f divide(float scalar) {
        return scale(1.0f / scalar);
    }

    public Vec3f scale(float scalar) {
        return new Vec3f(x*scalar, y*scalar, z*scalar);
    }
}
