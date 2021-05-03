package nl.codebulb.engine.math;

import java.nio.FloatBuffer;

public record Mat4f(Vec4f col1, Vec4f col2, Vec4f col3, Vec4f col4) {

    /*
        [ 1, 0, 0, 0
          0, 1, 0, 0
          0, 0, 1, 0
          0, 0, 0, 1 ]
     */
    public Mat4f() {
        this(
                new Vec4f(1, 0, 0 ,0),
                new Vec4f(0, 1, 0, 0),
                new Vec4f(0, 0 ,1, 0),
                new Vec4f(0, 0 ,0, 1)
        );
    }

    public Mat4f add(Mat4f other) {
        Vec4f res1 = new Vec4f(
                col1.r() + other.col1.r(),
                col1.g() + other.col1.g(),
                col1.b() + other.col1.g(),
                col1.a() + other.col1.a());
        Vec4f res2 = new Vec4f(
                col2.r() + other.col2.r(),
                col2.g() + other.col2.g(),
                col2.b() + other.col2.b(),
                col2.a() + other.col2.a());
        Vec4f res3 = new Vec4f(
                col3.r() + other.col3.r(),
                col3.g() + other.col3.g(),
                col3.b() + other.col3.b(),
                col3.a() + other.col3.a());
        Vec4f res4 = new Vec4f(
                col4.r() + other.col4.r(),
                col4.g() + other.col4.g(),
                col4.b() + other.col4.b(),
                col4.a() + other.col4.a());

        return new Mat4f(res1, res2, res3, res4);
    }

    public Mat4f inverse() {
        return multiply(-1);
    }

    public Mat4f multiply(float scalar) {
        Vec4f res1 = new Vec4f(this.col1.r() * scalar, this.col1.g() * scalar, this.col1.b() * scalar, this.col1.a() * scalar);
        Vec4f res2 = new Vec4f(this.col2.r() * scalar, this.col2.g() * scalar, this.col2.b() * scalar, this.col2.a() * scalar);
        Vec4f res3 = new Vec4f(this.col3.r() * scalar, this.col3.g() * scalar, this.col3.b() * scalar, this.col3.a() * scalar);
        Vec4f res4 = new Vec4f(this.col4.r() * scalar, this.col4.g() * scalar, this.col4.b() * scalar, this.col4.a() * scalar);

        return new Mat4f(res1, res2, res3, res4);
    }

    public Mat4f multiply(Mat4f other) {
        Vec4f res1 = new Vec4f(
                this.col1.r() * other.col1.r() + this.col1.g() * other.col2.r() + this.col1.b() * other.col3.r() + this.col1.a() * other.col4.r(),
                this.col1.r() * other.col1.g() + this.col1.g() * other.col2.g() + this.col1.b() * other.col3.g() + this.col1.a() * other.col4.g(),
                this.col1.r() * other.col1.b() + this.col1.g() * other.col2.b() + this.col1.b() * other.col3.b() + this.col1.a() * other.col4.b(),
                this.col1.r() * other.col1.a() + this.col1.g() * other.col2.a() + this.col1.b() * other.col3.a() + this.col1.a() * other.col4.a());

        Vec4f res2 = new Vec4f(
                this.col2.r() * other.col1.r() + this.col2.g() * other.col2.r() + this.col2.b() * other.col3.r() + this.col2.a() * other.col4.r(),
                this.col2.r() * other.col1.g() + this.col2.g() * other.col2.g() + this.col2.b() * other.col3.g() + this.col2.a() * other.col4.g(),
                this.col2.r() * other.col1.b() + this.col2.g() * other.col2.b() + this.col2.b() * other.col3.b() + this.col2.a() * other.col4.b(),
                this.col2.r() * other.col1.a() + this.col2.g() * other.col2.a() + this.col2.b() * other.col3.a() + this.col2.a() * other.col4.a());

        Vec4f res3 = new Vec4f(
                this.col3.r() * other.col1.r() + this.col3.g() * other.col2.r() + this.col3.b() * other.col3.r() + this.col3.a() * other.col4.r(),
                this.col3.r() * other.col1.g() + this.col3.g() * other.col2.g() + this.col3.b() * other.col3.g() + this.col3.a() * other.col4.g(),
                this.col3.r() * other.col1.b() + this.col3.g() * other.col2.b() + this.col3.b() * other.col3.b() + this.col3.a() * other.col4.b(),
                this.col3.r() * other.col1.a() + this.col3.g() * other.col2.a() + this.col3.b() * other.col3.a() + this.col3.a() * other.col4.a());

        Vec4f res4 = new Vec4f(
                this.col4.r() * other.col1.r() + this.col4.g() * other.col2.r() + this.col4.b() * other.col3.r() + this.col4.a() * other.col4.r(),
                this.col4.r() * other.col1.g() + this.col4.g() * other.col2.g() + this.col4.b() * other.col3.g() + this.col4.a() * other.col4.g(),
                this.col4.r() * other.col1.b() + this.col4.g() * other.col2.b() + this.col4.b() * other.col3.b() + this.col4.a() * other.col4.b(),
                this.col4.r() * other.col1.a() + this.col4.g() * other.col2.a() + this.col4.b() * other.col3.a() + this.col4.a() * other.col4.a());

        return new Mat4f(res1, res2, res3, res4);
    }

    public static Mat4f ortho(float left, float right, float bottom, float top, float near, float far) {
        return new Mat4f(
                new Vec4f(2.0f / (right - left), 0, 0,-(right + left) / (right - left)),
                new Vec4f(0, 2.0f / (top - bottom), 0, -(top + bottom) / (top - bottom)),
                new Vec4f(0, 0 ,-2.0f / (far - near), -(far + near) / (far - near)),
                new Vec4f(0, 0 ,0, 1)
        );
    }

    public static Mat4f rotate(float angle, Vec3f rv) {

        float cos = (float) Math.cos(Math.toRadians(angle));
        float sin = (float) Math.sin(Math.toRadians(angle));

        // normalize
        if (rv.length() != 1.0f) {
            rv = rv.normalize();
        }

        return new Mat4f(
                new Vec4f(
                        rv.x() * rv.x() * (1.0f - cos) + cos ,
                        rv.x() * rv.y() * (1.0f - cos) - rv.z() * sin,
                        rv.x() * rv.z() * (1.0f - cos) + rv.y() * sin ,
                        0),
                new Vec4f(
                        rv.y() * rv.x() * (1.0f - cos) + rv.z() * sin,
                        rv.y() * rv.y() * (1.0f - cos) + cos,
                        rv.y() * rv.z() * (1.0f - cos) - rv.x() * sin,
                        0),
                new Vec4f(
                        rv.x() * rv.z() * (1.0f - cos) - rv.y() * sin,
                        rv.y() * rv.z() * (1.0f - cos) + rv.x() * sin,
                        rv.z() * rv.z() * (1.0f - cos) + cos,
                        0),
                new Vec4f(0, 0, 0, 1)
        );
    }

    public static Mat4f translate(Vec3f tv) {
        return new Mat4f(
                new Vec4f(1, 0, tv.x() ,0),
                new Vec4f(0, 1, tv.y(), 0),
                new Vec4f(0, 0 ,tv.z(), 0),
                new Vec4f(0, 0 ,0, 1)
        );
    }

    public void fillBuffer(FloatBuffer buffer) {
        buffer.put(col1.r()).put(col2.r()).put(col3.r()).put(col4.r());
        buffer.put(col1.g()).put(col2.g()).put(col3.g()).put(col4.g());
        buffer.put(col1.b()).put(col2.b()).put(col3.b()).put(col4.b());
        buffer.put(col1.a()).put(col2.a()).put(col3.a()).put(col4.a());
        buffer.flip();
    }

}
