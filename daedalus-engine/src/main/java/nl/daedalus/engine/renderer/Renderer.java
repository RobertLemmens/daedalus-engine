package nl.daedalus.engine.renderer;

import nl.daedalus.engine.Shader;
import nl.daedalus.engine.math.Mat4f;
import nl.daedalus.engine.math.Vec4f;

public final class Renderer {

    private static RendererBackend backend;

    public static void init() {
        backend = RendererBackend.create();
    }

    public static void setClearColor(Vec4f color){
        backend.setClearColor(color);
    }

    public static void clear() {
        backend.clear();
    }

    //temp
    private static Mat4f viewProjectionMatrix;

    public static void begin(OrthographicCamera orthographicCamera) {
        viewProjectionMatrix = orthographicCamera.getViewProjectionMatrix();
        // bind shader

        // bind the vertex array
    }

    public static void end() {
        // draw everything

    }

    public static void drawQuad() {
        // update the quads

    }

    public static void draw(VertexArray vertexArray, Shader shader, Mat4f transform) {
        shader.bind();
        shader.uploadUniformMat4("u_view_projection", viewProjectionMatrix);
        shader.uploadUniformMat4("u_transform", transform);

        vertexArray.bind();
        backend.drawIndexed(vertexArray);
    }

}
