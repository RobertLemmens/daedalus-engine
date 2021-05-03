package nl.codebulb.engine.math;

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

}
