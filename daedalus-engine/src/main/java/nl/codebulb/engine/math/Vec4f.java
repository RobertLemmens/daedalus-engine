package nl.codebulb.engine.math;

public record Vec4f(float r, float g, float b, float a) {
    public Vec4f(float ident) {
        this(ident, ident, ident, ident);
    }
}
