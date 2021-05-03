package nl.codebulb.engine.math;

public record Vec3f(float r, float g, float b) {
    public Vec3f(float ident) {
        this(ident, ident, ident);
    }
}
