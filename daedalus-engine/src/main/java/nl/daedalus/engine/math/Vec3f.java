package nl.daedalus.engine.math;

import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;

public record Vec3f(float x, float y, float z) {
    public Vec3f(float ident) {
        this(ident, ident, ident);
    }

    public FloatBuffer toBuffer() {
        FloatBuffer buffer = BufferUtils.createFloatBuffer(3);
        buffer.put(x);
        buffer.put(y);
        buffer.put(z);
        return buffer;
    }

    public float[] asFloats() {
        return new float[]{x, y, z};
    }

    public Vec3f add(Vec3f other) {
        return new Vec3f(x + other.x, y + other.y, z + other.z);
    }

    public Vec3f addX(float newX) {
        return new Vec3f(x + newX, y, z);
    }

    public Vec3f addY(float newY) {
        return new Vec3f(x, y + newY, z);
    }

    public Vec3f addZ(float newZ) {
        return new Vec3f(x, y, z + newZ);
    }

    public Vec3f subtractX(float newX) {
        return new Vec3f(x - newX, y, z);
    }

    public Vec3f subtractY(float newY) {
        return new Vec3f(x, y - newY, z);
    }

    public Vec3f subtractZ(float newZ) {
        return new Vec3f(x, y, z - newZ);
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
