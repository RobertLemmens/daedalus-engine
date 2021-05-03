package nl.codebulb.engine.renderer;

import nl.codebulb.engine.math.Vec4f;

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

    public static void begin() {
        // bind shader

        // bind the vertex array
    }

    public static void end() {
        // draw everything

    }

    public static void drawQuad() {
        // update the quads

    }

    public static void draw(VertexArray vertexArray) {
        vertexArray.bind();
        backend.drawIndexed(vertexArray);
    }

}
