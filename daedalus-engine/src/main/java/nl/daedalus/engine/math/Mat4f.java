package nl.daedalus.engine.math;


import java.nio.FloatBuffer;

public record Mat4f(Vec4f col1, Vec4f col2, Vec4f col3, Vec4f col4) {


    // We initialize matrices as identity
    public Mat4f() {
        this(
                new Vec4f(1, 0, 0 ,0),
                new Vec4f(0, 1, 0, 0),
                new Vec4f(0, 0 ,1, 0),
                new Vec4f(0, 0 ,0, 1)
        );
    }

    public Mat4f(float[] matrixFloats) {
        this(
                new Vec4f(matrixFloats[0], matrixFloats[1], matrixFloats[2], matrixFloats[3]),
                new Vec4f(matrixFloats[4], matrixFloats[5], matrixFloats[6], matrixFloats[7]),
                new Vec4f(matrixFloats[8], matrixFloats[9], matrixFloats[10], matrixFloats[11]),
                new Vec4f(matrixFloats[12], matrixFloats[13], matrixFloats[14], matrixFloats[15])
        );
    }

    public float[] asFloats() {
        return new float[] {
                col1.r(), col1.g(), col1.b(), col1.a(),
                col2.r(), col2.g(), col2.b(), col2.a(),
                col3.r(), col3.g(), col3.b(), col3.a(),
                col4.r(), col4.g(), col4.b(), col4.a()
        };
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

    public Mat4f negate() {
        return multiply(-1);
    }

    public Mat4f multiply(float scalar) {
        Vec4f res1 = new Vec4f(this.col1.r() * scalar, this.col1.g() * scalar, this.col1.b() * scalar, this.col1.a() * scalar);
        Vec4f res2 = new Vec4f(this.col2.r() * scalar, this.col2.g() * scalar, this.col2.b() * scalar, this.col2.a() * scalar);
        Vec4f res3 = new Vec4f(this.col3.r() * scalar, this.col3.g() * scalar, this.col3.b() * scalar, this.col3.a() * scalar);
        Vec4f res4 = new Vec4f(this.col4.r() * scalar, this.col4.g() * scalar, this.col4.b() * scalar, this.col4.a() * scalar);

        return new Mat4f(res1, res2, res3, res4);
    }

    public Vec4f multiply(Vec4f vector) {
        return new Vec4f(
                col1.r() * vector.r() + col1.g() * vector.g() + col1.b() * vector.b() + col1.a() * vector.a(),
                col2.r() * vector.r() + col2.g() * vector.g() + col2.b() * vector.b() + col2.a() * vector.a(),
                col3.r() * vector.r() + col3.g() * vector.g() + col3.b() * vector.b() + col3.a() * vector.a(),
                col4.r() * vector.r() + col4.g() * vector.g() + col4.b() * vector.b() + col4.a() * vector.a()
        );
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

    /**
     * @return the inverse of this matrix
     */
    public Mat4f invert() {
        float[] tmp = new float[12];
        float[] src = new float[16];
        float[] dst = new float[16];
        float[] newMatrix = new float[16];

        // Transpose matrix
        src = transpose().asFloats();

        // calculate pairs for first 8 elems (cofactors)
        tmp[0] = src[10] * src[15];
        tmp[1] = src[11] * src[14];
        tmp[2] = src[9]  * src[15];
        tmp[3] = src[11] * src[13];
        tmp[4] = src[9]  * src[14];
        tmp[5] = src[10] * src[13];
        tmp[6] = src[8]  * src[15];
        tmp[7] = src[11] * src[12];
        tmp[8] = src[8]  * src[14];
        tmp[9] = src[10] * src[12];
        tmp[10]= src[8]  * src[13];
        tmp[11]= src[9]  * src[12];

        // calculate first 8 elements (cofactors)
        dst[0]  = tmp[0]*src[5] + tmp[3]*src[6] + tmp[4]*src[7];
        dst[0] -= tmp[1]*src[5] + tmp[2]*src[6] + tmp[5]*src[7];
        dst[1]  = tmp[1]*src[4] + tmp[6]*src[6] + tmp[9]*src[7];
        dst[1] -= tmp[0]*src[4] + tmp[7]*src[6] + tmp[8]*src[7];
        dst[2]  = tmp[2]*src[4] + tmp[7]*src[5] + tmp[10]*src[7];
        dst[2] -= tmp[3]*src[4] + tmp[6]*src[5] + tmp[11]*src[7];
        dst[3]  = tmp[5]*src[4] + tmp[8]*src[5] + tmp[11]*src[6];
        dst[3] -= tmp[4]*src[4] + tmp[9]*src[5] + tmp[10]*src[6];
        dst[4]  = tmp[1]*src[1] + tmp[2]*src[2] + tmp[5]*src[3];
        dst[4] -= tmp[0]*src[1] + tmp[3]*src[2] + tmp[4]*src[3];
        dst[5]  = tmp[0]*src[0] + tmp[7]*src[2] + tmp[8]*src[3];
        dst[5] -= tmp[1]*src[0] + tmp[6]*src[2] + tmp[9]*src[3];
        dst[6]  = tmp[3]*src[0] + tmp[6]*src[1] + tmp[11]*src[3];
        dst[6] -= tmp[2]*src[0] + tmp[7]*src[1] + tmp[10]*src[3];
        dst[7]  = tmp[4]*src[0] + tmp[9]*src[1] + tmp[10]*src[2];
        dst[7] -= tmp[5]*src[0] + tmp[8]*src[1] + tmp[11]*src[2];

        // Calculate pairs for second 8 elements (cofactors)
        tmp[0]  = src[2]*src[7];
        tmp[1]  = src[3]*src[6];
        tmp[2]  = src[1]*src[7];
        tmp[3]  = src[3]*src[5];
        tmp[4]  = src[1]*src[6];
        tmp[5]  = src[2]*src[5];
        tmp[6]  = src[0]*src[7];
        tmp[7]  = src[3]*src[4];
        tmp[8]  = src[0]*src[6];
        tmp[9]  = src[2]*src[4];
        tmp[10] = src[0]*src[5];
        tmp[11] = src[1]*src[4];

        // Calculate second 8 elements (cofactors)
        dst[8]   = tmp[0] * src[13]  + tmp[3] * src[14]  + tmp[4] * src[15];
        dst[8]  -= tmp[1] * src[13]  + tmp[2] * src[14]  + tmp[5] * src[15];
        dst[9]   = tmp[1] * src[12]  + tmp[6] * src[14]  + tmp[9] * src[15];
        dst[9]  -= tmp[0] * src[12]  + tmp[7] * src[14]  + tmp[8] * src[15];
        dst[10]  = tmp[2] * src[12]  + tmp[7] * src[13]  + tmp[10]* src[15];
        dst[10] -= tmp[3] * src[12]  + tmp[6] * src[13]  + tmp[11]* src[15];
        dst[11]  = tmp[5] * src[12]  + tmp[8] * src[13]  + tmp[11]* src[14];
        dst[11] -= tmp[4] * src[12]  + tmp[9] * src[13]  + tmp[10]* src[14];
        dst[12]  = tmp[2] * src[10]  + tmp[5] * src[11]  + tmp[1] * src[9];
        dst[12] -= tmp[4] * src[11]  + tmp[0] * src[9]   + tmp[3] * src[10];
        dst[13]  = tmp[8] * src[11]  + tmp[0] * src[8]   + tmp[7] * src[10];
        dst[13] -= tmp[6] * src[10]  + tmp[9] * src[11]  + tmp[1] * src[8];
        dst[14]  = tmp[6] * src[9]   + tmp[11]* src[11]  + tmp[3] * src[8];
        dst[14] -= tmp[10]* src[11]  + tmp[2] * src[8]   + tmp[7] * src[9];
        dst[15]  = tmp[10]* src[10]  + tmp[4] * src[8]   + tmp[9] * src[9];
        dst[15] -= tmp[8] * src[9]   + tmp[11]* src[10]  + tmp[5] * src[8];

        // Calculate determinant
        float det = src[0]*dst[0] + src[1]*dst[1] + src[2]*dst[2] + src[3]*dst[3];

        // Calculate matrix inverse
        det = 1.0f / det;


        for (int i = 0; i < 16; i++)
            newMatrix[i] = dst[i] * det;

        return new Mat4f(newMatrix);
    }

    public Mat4f transpose() {
        Vec4f res1 = new Vec4f(
                this.col1.r(),this.col2.r(), this.col3.r(), this.col4.r()
        );
        Vec4f res2 = new Vec4f(
                this.col1.g(), this.col2.g(), this.col3.g(), this.col4.g()
        );
        Vec4f res3 = new Vec4f(
                this.col1.b(), this.col2.b(), this.col3.b(), this.col4.b()
        );
        Vec4f res4 = new Vec4f(
                this.col1.a(), this.col2.a(), this.col3.a(), this.col4.a()
        );
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

    /**
     * Translates with an identity matrix
     *
     * @param tv
     * @return
     */
    public static Mat4f translate(Vec3f tv) {
        return new Mat4f(
                new Vec4f(1, 0, 0, tv.x()),
                new Vec4f(0, 1, 0, tv.y()),
                new Vec4f(0, 0, 1, tv.z()),
                new Vec4f(0, 0, 0, 1)
        );
    }

    public static Mat4f scale(Vec3f sv) {
        return new Mat4f(
                new Vec4f(sv.x(), 0, 0 ,0),
                new Vec4f(0, sv.y(), 0, 0),
                new Vec4f(0, 0 ,sv.z(), 0),
                new Vec4f(0, 0 ,0, 1));
    }

    public void fillBuffer(FloatBuffer buffer) {
        buffer.put(col1.r()).put(col2.r()).put(col3.r()).put(col4.r());
        buffer.put(col1.g()).put(col2.g()).put(col3.g()).put(col4.g());
        buffer.put(col1.b()).put(col2.b()).put(col3.b()).put(col4.b());
        buffer.put(col1.a()).put(col2.a()).put(col3.a()).put(col4.a());
        buffer.flip();
    }

}
